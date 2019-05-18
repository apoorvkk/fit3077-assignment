package edu.monash.it.fit3077.vjak.api.hapi.HapiTobaccoUse;

import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationLoader;
import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationModel;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;

/**
 * Fetcher of the tobacco use measurement using HAPI API.
 */
public class HapiTobaccoUseObservationLoader extends HapiObservationLoader {

    /**
     * Interprets the response of a tobacco use measurement request from the HAPI API.
     * @param response: the response from the HAPI API.
     * @return the latest tobacco use measurement.
     */
    @Override
    protected HapiObservationModel getModel(Bundle response) {
        return new HapiTobaccoUseObservationModel((Observation) response.getEntry().get(0).getResource());
    }
}
