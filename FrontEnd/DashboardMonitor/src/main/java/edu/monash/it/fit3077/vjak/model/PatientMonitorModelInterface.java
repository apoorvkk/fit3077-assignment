package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.observer.Observer;

public interface PatientMonitorModelInterface extends Observer {
    PatientModelInterface getPatient();
    void trackMeasurements();
    void removeMeasurements();
    boolean isBeingMonitored();
}
