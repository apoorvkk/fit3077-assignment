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
    /**
     * Return the patient.
     * @return the patient.
     */
    PatientModelInterface getPatient();

    /**
     * Select this patient monitor and listen into events. This is where someone is interested in viewing this monitor.
     */
    void startMonitoring();

    /**
     * Start tracking a health measurement for this patient monitor.
     * @param measurementType: measurement of interest
     */
    void trackMeasurement(String measurementType);

    /**
     * Stop tracking the health measurement and make sure to destroy it properly. Also notify observers health measurement
     * has been removed.
     * @param measurementType: measurement of interest.
     */
    void removeHealthMeasurement(String measurementType);

    /**
     Completely destroy this monitor as it's no longer being use.
     */
    void stopMonitoring();

    /**
     * Check if someone is interested in this patient monitor.
     * @return boolean value.
     */
    boolean isBeingMonitored();

    /**
     * This method returns list of tracking health measurements under a particular order.
     * @return a list of tracking health measurements.
     */
    ArrayList<AbstractHealthMeasurementModel> getHealthMeasurements();
}
