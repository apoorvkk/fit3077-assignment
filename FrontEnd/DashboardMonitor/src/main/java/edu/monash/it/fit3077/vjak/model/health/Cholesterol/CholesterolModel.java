package edu.monash.it.fit3077.vjak.model.health.Cholesterol;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.health.AbstractQuantityModel;

/*
This class models the total cholesterol model.
 */
public class CholesterolModel extends AbstractQuantityModel {
    /**
     * Initiailize the model.
     * This will also tell backend to start tracking.
     * @param patientId: the patient id.
     */
    public CholesterolModel(String patientId) {
        this.track(patientId);
    }

    /**
     * Return the official measurement type.
     * @return the measurement type.
     */
    @Override
    public String getMeasurementType() {
        return Constant.cholesterol;
    }

    /**
     * Returns a description of the model. Mainly used to display the model in presentational format.
     * @return the description of the model.
     */
    @Override
    public String getDescription() {
        return "Cholesterol";
    }
}
