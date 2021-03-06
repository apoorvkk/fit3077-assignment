package edu.monash.it.fit3077.akql.api.hapi;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import edu.monash.it.fit3077.akql.api.ObservationLoaderInterface;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;

/**
 * This class is responsible for loading observations from FHIR. It's a specific concrete implementation
 * of loading observations and hence, tightly coupled with Hapi's Java SDK but does implement a common interface so callers
 * can use a common interface and not depend directly on this implementation.
 */
public abstract class HapiObservationLoader implements ObservationLoaderInterface {
    private final IGenericClient client;

    /**
     * Initialize the loader
     */
    public HapiObservationLoader() {
        /* Initialize fhir client */
        FhirContext ctx = FhirContext.forDstu3();
        String serverBaseUrl = "http://hapi-fhir.erc.monash.edu:8080/baseDstu3";
        ctx.getRestfulClientFactory().setConnectTimeout(120 * 1000);
        ctx.getRestfulClientFactory().setSocketTimeout(120 * 1000);
        this.client = ctx.newRestfulGenericClient(serverBaseUrl);
    }

    /**
     * Gets the latest health measurement of a specified health measurement of a specified patient.
     * @param patientId: the id of the patient whose measurement we want to retrieve.
     * @param measurementCode: the code of the health measurement of the HAPI server.
     * @return HAPI version of the health measurement model.
     */
    public HapiObservationModel getLatestObservation(String patientId, String measurementCode) {
        /*
            Search observations on patient id and the measurement code (eg. Cholesterol's code is 2093-3).
            The code also sorts by date in descending order so it can retrieve latest observation (most up to date
            measurement value).
        */
        Bundle response = this.client.search()
            .forResource(Observation.class)
            .where(Observation.PATIENT.hasId(patientId))
            .and(Observation.CODE.exactly().code(measurementCode))
            .sort().descending(Observation.DATE)
            .returnBundle(Bundle.class)
            .execute();

        // Just get the first observation because it's the latest observation.
        try {
            return this.getModel(response);
        } catch (IndexOutOfBoundsException err) {
            return null;
        }
    }

    /**
     * Interprets the response of a health measurement request from the HAPI API.
     * @param response: the response from the HAPI API.
     * @return the latest health measurement.
     */
    protected abstract HapiObservationModel getModel(Bundle response);
}
