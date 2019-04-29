package edu.monash.it.fit3077.vjak.api.hapi;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import edu.monash.it.fit3077.vjak.model.ObservationLoaderInterface;
import edu.monash.it.fit3077.vjak.model.ObservationModelInterface;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class HapiObservationLoader implements ObservationLoaderInterface {
    private IGenericClient client;
    private Bundle currentObservationPage;

    public HapiObservationLoader() {
        FhirContext ctx = FhirContext.forDstu3();
        String serverBaseUrl = "http://hapi-fhir.erc.monash.edu:8080/baseDstu3";
        ctx.getRestfulClientFactory().setConnectTimeout(120 * 1000);
        ctx.getRestfulClientFactory().setSocketTimeout(120 * 1000);
        this.client = ctx.newRestfulGenericClient(serverBaseUrl);
    }

    public ArrayList<ObservationModelInterface> loadNewObservations() {
        if (this.currentObservationPage == null) {
            this.currentObservationPage = this.client.search()
                .forResource(Observation.class)
                .sort().descending(Observation.DATE)
                .returnBundle(Bundle.class)
                .execute();
        } else {
            this.currentObservationPage = this.client.loadPage().next(this.currentObservationPage).execute();
        }

        return this.currentObservationPage.getEntry()
            .stream()
            .map(entry -> new HapiObservationModel((Observation) entry.getResource()))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public ObservationModelInterface getLatestObservation(String patientId, String measurementCode) {
        Bundle response = this.client.search()
            .forResource(Observation.class)
            .where(Observation.PATIENT.hasId(patientId))
            .and(Observation.CODE.exactly().code(measurementCode))
            .sort().descending(Observation.DATE)
            .returnBundle(Bundle.class)
            .execute();

        return new HapiObservationModel((Observation) response.getEntry().get(0).getResource());
    }
}
