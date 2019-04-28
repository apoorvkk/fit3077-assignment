package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.model.health.HealthMeasurementModel;
import edu.monash.it.fit3077.vjak.observer.Observer;

import java.util.ArrayList;

public interface PatientMonitorModelInterface extends Observer {
    PatientModelInterface getPatient();
    void trackMeasurements();
    void removeMeasurements();
    boolean isBeingMonitored();
    ArrayList<HealthMeasurementModel> getHealthMeasurements();
}
