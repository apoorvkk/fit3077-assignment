package edu.monash.it.fit3077.vjak.api.hapi;

import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;

public class HapiOralTemperatureObservationLoader extends HapiObservationLoader {

    @Override
    HapiObservationModel getModel(Bundle response) {
        return new HapiOralTemperatureObservationModel((Observation) response.getEntry().get(0).getResource());
    }
}
