package edu.monash.it.fit3077.vjak.model.health;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.health.BloodPressure.BloodPressureModel;
import edu.monash.it.fit3077.vjak.model.health.Cholesterol.CholesterolModel;
import edu.monash.it.fit3077.vjak.model.health.OralTemperature.OralTemperatureModel;
import edu.monash.it.fit3077.vjak.model.health.TobaccoUse.TobaccoUseModel;

/*
This class focuses on creating specific measurement models (eg. Cholesterol). It uses the factory pattern design.
 */
public class HealthMeasurementCreator {
    /**
     * This method is responsible for creating the necessary health measurement models.
     * @param patientId: the patient id.
     * @param measurementType: measurement type of interest (eg. SystolicBloodPressure).
     * @return a health measurement model.
     */
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
            case Constant.oralTemperature:
                hm = new OralTemperatureModel(patientId);
                break;
            default:
                return null;
        }
        return hm;
    }
}
