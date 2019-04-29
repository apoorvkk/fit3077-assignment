package edu.monash.it.fit3077.vjak.api.hapi;

import edu.monash.it.fit3077.vjak.model.ObservationModelInterface;
import org.hl7.fhir.dstu3.model.Observation;

import java.util.Date;

public class HapiObservationModel implements ObservationModelInterface {
    private Observation observation;

    public HapiObservationModel(Observation resource) {
        this.observation = resource;
    }
}
