package edu.monash.it.fit3077.akql.api.hapi.HapiCholesterol;

import edu.monash.it.fit3077.akql.api.hapi.HapiObservationLoader;
import edu.monash.it.fit3077.akql.api.hapi.HapiObservationModel;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;

/**
 * Fetcher of the cholesterol measurement using HAPI API.
 */
public class HapiCholesterolObservationLoader extends HapiObservationLoader {

    /**
     * Interprets the response of a cholesterol measurement request from the HAPI API.
     * @param response: the response from the HAPI API.
     * @return the latest cholesterol measurement.
     */
    @Override
    protected HapiObservationModel getModel(Bundle response) {
        return new HapiCholesterolObservationModel((Observation) response.getEntry().get(0).getResource());
    }
}
