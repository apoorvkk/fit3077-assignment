package edu.monash.it.fit3077.vjak.api.hapi;

import edu.monash.it.fit3077.vjak.model.ObservationModelInterface;
import org.hl7.fhir.dstu3.model.Observation;

import java.util.Date;

public class HapiObservationModel implements ObservationModelInterface {
    private Observation observation;

    public HapiObservationModel(Observation resource) {
        this.observation = resource;
    }

    public String getUnit() {
        return this.observation.getValueQuantity().getUnit();
    }

    public String getValue() {
        return this.observation.getValueQuantity().getValue().toString();
    }

    public String getPatientId() {
        return this.observation.getSubject().getId();
    }

    public String getMeasurementCode() {
        return this.observation.getCode().getText();
    }
}
