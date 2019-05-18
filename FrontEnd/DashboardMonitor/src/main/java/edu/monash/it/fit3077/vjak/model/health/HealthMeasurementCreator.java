package edu.monash.it.fit3077.vjak.model.health;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.health.BloodPressure.BloodPressureModel;
import edu.monash.it.fit3077.vjak.model.health.Cholesterol.CholesterolModel;
import edu.monash.it.fit3077.vjak.model.health.TobaccoUse.TobaccoUseModel;

/*
This class focuses on creating specific measurement models (eg. Cholesterol). This class can be extended to
many other measurements in future.
 */
public class HealthMeasurementCreator {
    public static AbstractHealthMeasurementModel trackHealthMeasurement(String patientId, String measurementType) {
        AbstractHealthMeasurementModel hm;
        switch (measurementType) {
            case Constant.cholesterol:
                hm = new CholesterolModel(patientId);
                break;
            case Constant.tobaccoUse:
                hm = new TobaccoUseModel(patientId);
                break;
            case Constant.diastolicBloodPressure:
                hm = new BloodPressureModel(patientId, Constant.diastolic);
                break;
            case Constant.systolicBloodPressure:
                hm = new BloodPressureModel(patientId, Constant.systolic);
                break;
            default:
                return null;
        }
        return hm;
    }
}
