package edu.monash.it.fit3077.vjak.api.hapi.HapiTobaccoUse;

import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationLoader;
import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationModel;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;

public class HapiTobaccoUseObservationLoader extends HapiObservationLoader {

    @Override
    protected HapiObservationModel getModel(Bundle response) {
        return new HapiTobaccoUseObservationModel((Observation) response.getEntry().get(0).getResource());
    }
}
