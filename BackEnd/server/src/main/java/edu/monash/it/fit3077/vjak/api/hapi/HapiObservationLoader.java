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
            this.currentObservationPage = client.search()
                .forResource(org.hl7.fhir.dstu3.model.Observation.class)
                .sort().descending(Observation.DATE)
                .returnBundle(Bundle.class)
                .execute();
        } else {
            this.currentObservationPage = client.loadPage().next(this.currentObservationPage).execute();
        }

        return this.currentObservationPage.getEntry()
            .stream()
            .map(entry -> new HapiObservationModel((org.hl7.fhir.dstu3.model.Observation) entry.getResource()))
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
