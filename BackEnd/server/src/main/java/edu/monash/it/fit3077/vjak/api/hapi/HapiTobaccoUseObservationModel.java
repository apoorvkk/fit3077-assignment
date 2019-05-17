package edu.monash.it.fit3077.vjak.api.hapi;

import edu.monash.it.fit3077.vjak.model.TobaccoUseObservationModelInterface;
import org.hl7.fhir.dstu3.model.Observation;

public class HapiTobaccoUseObservationModel extends HapiObservationModel implements TobaccoUseObservationModelInterface {

    HapiTobaccoUseObservationModel(Observation resource) {
        super(resource);
    }

    public String getStatus() {
        return this.observation.getValueCodeableConcept().getText();
    }
}
