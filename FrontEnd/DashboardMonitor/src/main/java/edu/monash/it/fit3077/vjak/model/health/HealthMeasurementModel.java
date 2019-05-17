package edu.monash.it.fit3077.vjak.model.health;

import edu.monash.it.fit3077.vjak.backendmonitor.MeasurementEventModel;
import edu.monash.it.fit3077.vjak.model.health.tracker.MeasurementTracker;
import edu.monash.it.fit3077.vjak.observer.Subject;

/*
This class represents a generic health measurement model that can register/deregister to receive its values.
 */
public abstract class HealthMeasurementModel extends Subject implements HealthMeasurementModelInterface {
    private final MeasurementTracker measurementTracker;

    HealthMeasurementModel(String patientId) {
        this.measurementTracker = new MeasurementTracker(patientId, this.getMeasurementType());
        this.measurementTracker.register();
    }

    public void cleanUp() {
        measurementTracker.deregister();
    }

    abstract public void setHealthMeasurementValue(MeasurementEventModel me);
    abstract public String getMeasurementType();
}
