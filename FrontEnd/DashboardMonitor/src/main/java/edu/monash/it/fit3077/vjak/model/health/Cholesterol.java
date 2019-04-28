package edu.monash.it.fit3077.vjak.model.health;

public class Cholesterol extends HealthMeasurement {
    public Cholesterol(String patientId) {
        super(patientId);
    }

    @Override
    public String toString() {
        return "Cholesterol";
    }
}
