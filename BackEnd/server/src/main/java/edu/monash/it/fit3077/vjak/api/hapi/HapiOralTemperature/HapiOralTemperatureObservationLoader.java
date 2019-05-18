package edu.monash.it.fit3077.vjak.api.hapi.HapiOralTemperature;

import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationLoader;
import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationModel;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;

/**
 * Fetcher of the oral temperature measurement using HAPI API.
 */
public class HapiOralTemperatureObservationLoader extends HapiObservationLoader {

    /**
     * Interprets the response of an oral temperature measurement request from the HAPI API.
     * @param response: the response from the HAPI API.
     * @return the latest oral temperature measurement.
     */
    @Override
    protected HapiObservationModel getModel(Bundle response) {
        return new HapiOralTemperatureObservationModel((Observation) response.getEntry().get(0).getResource());
    }
}
