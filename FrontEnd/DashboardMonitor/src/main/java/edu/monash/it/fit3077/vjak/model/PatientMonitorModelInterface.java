package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;
import edu.monash.it.fit3077.vjak.observer.Observer;
import edu.monash.it.fit3077.vjak.observer.SubjectInterface;

import java.util.ArrayList;
import java.util.HashMap;

/*
This interface is responsible for defining a contract that callers (eg. controllers) can use to manipulate the model.
This provides a nice separation of concerns between the controller and model through the dependency inversion principle.
 */
public interface PatientMonitorModelInterface extends Observer, SubjectInterface {
    PatientModelInterface getPatient();
    void startMonitoring();
    void trackMeasurement(String measurementType);
    void removeHealthMeasurement(String measurementType);
    void stopMonitoring();
    boolean isBeingMonitored();
    ArrayList<AbstractHealthMeasurementModel> getHealthMeasurements();
}
