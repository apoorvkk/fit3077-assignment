package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.model.health.HealthMeasurementModel;
import edu.monash.it.fit3077.vjak.observer.Observer;

import java.util.ArrayList;

/*
This interface is responsible for defining a contract that callers (eg. controllers) can use to manipulate the model.
This provides a nice separation of concerns between the controller and model through the dependency inversion principle.
 */
public interface PatientMonitorModelInterface extends Observer {
    PatientModelInterface getPatient();
    void startMonitoring();
    void trackCholesterol();
    void trackSystolicBloodPressure();
    void trackDiastolicBloodPressure();
    void trackTobaccoUse();
    void stopMonitoring();
    boolean isBeingMonitored();
    ArrayList<HealthMeasurementModel> getHealthMeasurements();
}
