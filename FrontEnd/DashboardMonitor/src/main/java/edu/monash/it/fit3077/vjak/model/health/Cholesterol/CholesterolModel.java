package edu.monash.it.fit3077.vjak.model.health.Cholesterol;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.health.AbstractQuantityModel;

/*
This class models the cholesterol model.
 */
public class CholesterolModel extends AbstractQuantityModel {
    public CholesterolModel(String patientId) {
        this.track(patientId);
    }

    @Override
    public String getMeasurementType() {
        return Constant.cholesterol;
    }

    @Override
    public String getDescription() {
        return "Cholesterol";
    }
}
