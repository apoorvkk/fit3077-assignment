package edu.monash.it.fit3077.vjak.model.health.OralTemperature;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.health.AbstractQuantityModel;

public class OralTemperatureModel extends AbstractQuantityModel {
    public OralTemperatureModel(String patientId) {
        this.track(patientId);
    }

    @Override
    public String getDescription() {
        return "Oral Temperature";
    }

    @Override
    public String getMeasurementType() {
        return Constant.oralTemperature;
    }
}
