package edu.monash.it.fit3077.vjak.api.hapi;

import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;

public class HapiBloodPressureObservationLoader extends HapiObservationLoader {

    private String type;

    public HapiBloodPressureObservationLoader(String type) {
        super();
        this.type = type;
    }

    @Override
    HapiObservationModel getModel(Bundle response) {
        return new HapiBloodPressureObservationModel((Observation) response.getEntry().get(0).getResource(), this.type);
    }
}
