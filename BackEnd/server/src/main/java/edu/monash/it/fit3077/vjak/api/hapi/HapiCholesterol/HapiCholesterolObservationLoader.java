package edu.monash.it.fit3077.vjak.api.hapi.HapiCholesterol;

import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationLoader;
import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationModel;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;

public class HapiCholesterolObservationLoader extends HapiObservationLoader {

    @Override
    protected HapiObservationModel getModel(Bundle response) {
        return new HapiCholesterolObservationModel((Observation) response.getEntry().get(0).getResource());
    }
}
