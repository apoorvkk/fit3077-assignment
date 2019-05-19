package edu.monash.it.fit3077.vjak.view.health;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;

public class HealthMeasurementViewCreator {
    public static HealthMeasurementView create(AbstractHealthMeasurementModel hm) {
        HealthMeasurementView hmv;

        switch (hm.getMeasurementType()) {
            case Constant.cholesterol:
                hmv = new CholesterolView(hm);
                break;
            case Constant.tobaccoUse:
                hmv = new TobaccoUseView(hm);
                break;
            case Constant.diastolicBloodPressure:
            case Constant.systolicBloodPressure:
                hmv = new BloodPressureView(hm);
                break;
            case Constant.oralTemperature:
                hmv = new OralTemperatureView(hm);
                break;
            default:
                return null;
        }
        return hmv;
    }
}
