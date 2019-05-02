package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.model.health.CholesterolModel;
import edu.monash.it.fit3077.vjak.model.health.HealthMeasurementModel;

/*
This class focuses on creating specific measurement models (eg. Cholesterol). This class can be extended to
many other measurements in future.
 */
class HealthMeasurementCreator {
    public HealthMeasurementModel trackCholesterol(String patientId) {
        return new CholesterolModel(patientId);
    }
}
