package edu.monash.it.fit3077.vjak.api.hapi.HapiTobaccoUse;

import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationModel;
import edu.monash.it.fit3077.vjak.model.TobaccoUseModel.TobaccoUseObservationModelInterface;
import org.hl7.fhir.dstu3.model.Observation;

/**
 * Represents HAPI's version of the tobacco use measurement.
 */
public class HapiTobaccoUseObservationModel extends HapiObservationModel implements TobaccoUseObservationModelInterface {

    /**
     * Instantiates the tobacco use measurement presentation model.
     * @param resource: HAPI's tobacco use measurement.
     */
    HapiTobaccoUseObservationModel(Observation resource) {
        super(resource);
    }

    /**
     * Gets the status of the tobacco use measurement from the HAPI tobacco use measurement.
     * @return the status of the tobacco use measurement.
     */
    public String getStatus() {
        return this.observation.getValueCodeableConcept().getText();
    }
}
