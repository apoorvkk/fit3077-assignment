package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.model.health.Cholesterol;
import edu.monash.it.fit3077.vjak.model.health.HealthMeasurement;

public class HealthMeasurementCreator {
    public HealthMeasurement trackCholesterol(String patientId) {
        return new Cholesterol(patientId);
    }
}
