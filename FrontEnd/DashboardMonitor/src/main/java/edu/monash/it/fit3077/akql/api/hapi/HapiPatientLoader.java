package edu.monash.it.fit3077.akql.api.hapi;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import edu.monash.it.fit3077.akql.model.PatientLoaderInterface;
import edu.monash.it.fit3077.akql.model.PatientModelInterface;
import org.hl7.fhir.dstu3.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

/*
This class is responsible for loading the patients for Hapi FHIR. It implements an interface so that the application logic code
is not coupled with the Hapi FHIR's implemantion of patients and loading of patients. This class is a concrete implementation
and hence, it is coupled with Hapi's Java SDK.
 */
public class HapiPatientLoader implements PatientLoaderInterface {
    private IGenericClient client;
    private Bundle currentEncounterPage;
    private final HashSet<String> patientIdsCache;
    private final String practionerId;

    /**
     * Initializes the loader and setups the necessary variables needed.
     * @param practitionerId: Used to get the practitioner's patients only.
     */
    public HapiPatientLoader(String practitionerId) {
        this.practionerId = practitionerId;
        this.patientIdsCache = new HashSet<>();
        this.initializeHapiClient();
    }

    /**
     * Set up the FHIR sdk code to make requests.
     */
    private void initializeHapiClient() {
        FhirContext ctx = FhirContext.forDstu3();
        String serverBaseUrl = "http://hapi-fhir.erc.monash.edu:8080/baseDstu3";
        ctx.getRestfulClientFactory().setConnectTimeout(120 * 1000);
        ctx.getRestfulClientFactory().setSocketTimeout(120 * 1000);
        this.client = ctx.newRestfulGenericClient(serverBaseUrl);
    }

    /**
     * This method will fetch a new batch of patient ids. It will get up to a 15 patient ids or until it has exhausted its search.
     * The search happens on the Encounter resource where we search for encounters related to the given practitioner.
     * We also track the current page so that we can easily iterate to the next page when needing more patient ids.
     * @return the next list of unique new patient ids.
     */
    private ArrayList<String> fetchNewPatientIds() {
        ArrayList<String> patientIds = new ArrayList<>();

        while (patientIds.size() < 15 && (this.currentEncounterPage == null || this.currentEncounterPage.getLink(Bundle.LINK_NEXT) != null)) {
            if (this.currentEncounterPage == null) { // Never made a search so start now.
                this.currentEncounterPage = client.search()
                        .forResource(org.hl7.fhir.dstu3.model.Encounter.class)
                        .where(Encounter.PRACTITIONER.hasId(this.practionerId))
                        .returnBundle(Bundle.class)
                        .execute();
            } else { // Moving onto the next page.
                this.currentEncounterPage = client.loadPage().next(this.currentEncounterPage).execute();
            }

            this.currentEncounterPage.getEntry()
                .forEach(entry -> {
                    Encounter e = (Encounter) entry.getResource();
                    Reference r = e.getSubject();

                    String patientId = r.getReference().replaceAll("\\D+","");

                    // Check if new patient id (note: new patient ids are cached once the patient resource has been downloaded.)
                    if (!this.patientIdsCache.contains(patientId) && !patientIds.contains(patientId)) {
                        patientIds.add(patientId);
                    }
                });
        }
        return patientIds;
    }

    /**
     * This method is responsible for taking a list of patient ids and download their full resources.
     * @param patientIds
     * @return the next list of unique FHIR patients.
     */
    private ArrayList<org.hl7.fhir.dstu3.model.Patient> downloadNewPatients(ArrayList<String> patientIds) {
        if (patientIds.size() == 0) return new ArrayList<>();

        ArrayList<org.hl7.fhir.dstu3.model.Patient> patients = new ArrayList<>();
        Bundle response = null;

        // The patient resources might span across multiple pages so make sure to iterate through all pages.
        while (response == null || response.getLink(Bundle.LINK_NEXT) != null) {
            if (response == null) { // On the first page.
                response = client.search()
                        .forResource(org.hl7.fhir.dstu3.model.Patient.class)
                        .where(Patient.RES_ID.exactly().systemAndValues("", patientIds))
                        .returnBundle(Bundle.class)
                        .execute();
            } else { // Move to the next page.
                response = client.loadPage().next(response).execute();
            }

            // Store the patients.
            ArrayList<org.hl7.fhir.dstu3.model.Patient> patientsBatch = response.getEntry()
                .stream()
                .map(entry -> (Patient) entry.getResource())
                .collect(Collectors.toCollection(ArrayList::new));
            patients.addAll(patientsBatch);
        }

        return patients;
    }

    /**
     * Helper function easily cache patient ids into an instance variable.
     * @param patientIds A list of unique patient ids.
     */
    private void cachePatientIds(ArrayList<String> patientIds) {
        this.patientIdsCache.addAll(patientIds);
    }

    /**
     * First fetch next batch of new patient ids. Then download all patients resources that belong to that batch.
     * Finally, cache the batch of patient ids so that we do not load the same patients again in any future patient load requests.
     * @return the latest batch of patient resources.
     */
    public ArrayList<PatientModelInterface> loadPatients() {
        ArrayList<String> patientIds = this.fetchNewPatientIds();
        ArrayList<org.hl7.fhir.dstu3.model.Patient> rawHapiPatients = this.downloadNewPatients(patientIds);
        this.cachePatientIds(patientIds);

        return rawHapiPatients.stream()
            .map(HapiPatientModel::new)
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
