package edu.monash.it.fit3077.vjak.model.health;

/*
This class models the cholesterol model.
 */
public class CholesterolModel extends HealthMeasurementModel {
    public CholesterolModel(String patientId) {
        super(patientId);
    }

    @Override
    public String toString() {
        return "Cholesterol";
    }
}
