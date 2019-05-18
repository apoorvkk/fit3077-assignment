package edu.monash.it.fit3077.vjak.api.hapi.HapiTobaccoUse;

import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationModel;
import edu.monash.it.fit3077.vjak.model.TobaccoUseModel.TobaccoUseObservationModelInterface;
import org.hl7.fhir.dstu3.model.Observation;

public class HapiTobaccoUseObservationModel extends HapiObservationModel implements TobaccoUseObservationModelInterface {

    HapiTobaccoUseObservationModel(Observation resource) {
        super(resource);
    }

    public String getStatus() {
        return this.observation.getValueCodeableConcept().getText();
    }
}
