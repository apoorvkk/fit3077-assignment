package edu.monash.it.fit3077.vjak.api.hapi.HapiOralTemperature;

import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationLoader;
import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationModel;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;

public class HapiOralTemperatureObservationLoader extends HapiObservationLoader {

    @Override
    protected HapiObservationModel getModel(Bundle response) {
        return new HapiOralTemperatureObservationModel((Observation) response.getEntry().get(0).getResource());
    }
}
