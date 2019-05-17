package edu.monash.it.fit3077.vjak.api.hapi;

import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;

public class HapiCholesterolObservationLoader extends HapiObservationLoader {

    @Override
    HapiObservationModel getModel(Bundle response) {
        return new HapiCholesterolObservationModel((Observation) response.getEntry().get(0).getResource());
    }
}
