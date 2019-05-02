package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.model.health.CholesterolModel;
import edu.monash.it.fit3077.vjak.model.health.HealthMeasurementModel;

class HealthMeasurementCreator {
    public HealthMeasurementModel trackCholesterol(String patientId) {
        return new CholesterolModel(patientId);
    }
}
