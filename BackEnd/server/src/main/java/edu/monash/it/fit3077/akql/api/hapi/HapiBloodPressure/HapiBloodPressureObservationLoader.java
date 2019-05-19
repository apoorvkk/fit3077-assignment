package edu.monash.it.fit3077.akql.api.hapi.HapiBloodPressure;

import edu.monash.it.fit3077.akql.api.hapi.HapiObservationLoader;
import edu.monash.it.fit3077.akql.api.hapi.HapiObservationModel;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;

/**
 * Fetcher of the blood pressure measurement using HAPI API.
 */
public class HapiBloodPressureObservationLoader extends HapiObservationLoader {

    private String type;

    /**
     * Instantiates the blood pressure loader.
     * @param type: the type of blood pressure to fetch (systolic or diastolic)
     */
    public HapiBloodPressureObservationLoader(String type) {
        super();
        this.type = type;
    }

    /**
     * Interprets the response of a blood pressure measurement request from the HAPI API.
     * @param response: the response from the HAPI API.
     * @return the latest blood pressure measurement.
     */
    @Override
    protected HapiObservationModel getModel(Bundle response) {
        return new HapiBloodPressureObservationModel((Observation) response.getEntry().get(0).getResource(), this.type);
    }
}
