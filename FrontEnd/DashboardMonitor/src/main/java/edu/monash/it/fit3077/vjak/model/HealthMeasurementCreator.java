package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.model.health.BloodPressure.BloodPressureModel;
import edu.monash.it.fit3077.vjak.model.health.Cholesterol.CholesterolModel;
import edu.monash.it.fit3077.vjak.model.health.HealthMeasurementModel;
import edu.monash.it.fit3077.vjak.model.health.TobaccoUse.TobaccoUseModel;

/*
This class focuses on creating specific measurement models (eg. Cholesterol). This class can be extended to
many other measurements in future.
 */
class HealthMeasurementCreator {
    public HealthMeasurementModel trackCholesterol(String patientId) {
        return new CholesterolModel(patientId);
    }

    public HealthMeasurementModel trackSystolicBloodPressure(String patientId) {
        return new BloodPressureModel(patientId, "SystolicBloodPressure");
    }

    public HealthMeasurementModel trackDiastolicBloodPressure(String patientId) {
        return new BloodPressureModel(patientId, "DiastolicBloodPressure");
    }

    public HealthMeasurementModel trackTobaccoUse(String patientId) {
        return new TobaccoUseModel(patientId);
    }
}
