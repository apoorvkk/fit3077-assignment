package edu.monash.it.fit3077.vjak.model.health;

import edu.monash.it.fit3077.vjak.model.health.tracker.MeasurementTracker;
import edu.monash.it.fit3077.vjak.observer.Subject;

public abstract class HealthMeasurementModel extends Subject {
    private final MeasurementTracker measurementTracker;
    private String value;
    private String unit;

    HealthMeasurementModel(String patientId) {
        this.measurementTracker = new MeasurementTracker(patientId, this.toString());
        this.measurementTracker.register();
    }

    public void cleanUp() {
        measurementTracker.deregister();
    }

    public String getHealthMeasurementResult() {
        if (this.value != null && this.unit != null){
            return this.value + " " + this.unit;
        }
        return "N/A";
    }

    public void setHealthMeasurementValue(String value, String unit) {
        if (!value.equals(this.value) || !unit.equals(this.unit)) {
            this.value = value;
            this.unit = unit;
            this.notifyObservers();
        }
    }
}
