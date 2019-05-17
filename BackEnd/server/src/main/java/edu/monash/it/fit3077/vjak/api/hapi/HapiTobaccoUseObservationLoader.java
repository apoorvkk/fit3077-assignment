package edu.monash.it.fit3077.vjak.api.hapi;

import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;

public class HapiTobaccoUseObservationLoader extends HapiObservationLoader {

    @Override
    HapiObservationModel getModel(Bundle response) {
        return new HapiTobaccoUseObservationModel((Observation) response.getEntry().get(0).getResource());
    }
}
