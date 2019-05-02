package edu.monash.it.fit3077.vjak.api.hapi;

import edu.monash.it.fit3077.vjak.model.ObservationModelInterface;
import org.hl7.fhir.dstu3.model.Observation;

public class HapiObservationModel implements ObservationModelInterface {
    private final Observation observation;

    HapiObservationModel(Observation resource) {
        this.observation = resource;
    }

    public String getUnit() {
        return this.observation.getValueQuantity().getUnit();
    }

    public String getValue() {
        return this.observation.getValueQuantity().getValue().toString();
    }

}
