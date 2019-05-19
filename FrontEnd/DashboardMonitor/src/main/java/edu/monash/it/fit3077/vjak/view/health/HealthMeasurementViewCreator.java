package edu.monash.it.fit3077.vjak.view.health;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;

/*
This class is responsible for creating the necessary views based on caller's preference (eg. render cholesterol view
if cholesterol selected). This uses a factory pattern design.
 */
public class HealthMeasurementViewCreator {
    /**
     * Used to create views.
     * @param hm: the health measurement model to provide when creating the specific view.
     * @return the health measurement model.
     */
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
