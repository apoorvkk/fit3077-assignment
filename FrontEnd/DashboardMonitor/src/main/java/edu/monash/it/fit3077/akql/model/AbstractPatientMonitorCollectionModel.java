package edu.monash.it.fit3077.akql.model;

import edu.monash.it.fit3077.akql.observer.Subject;

import java.util.ArrayList;

/*
This class is responsible for defining a contract that callers (eg. controllers) can use to manipulate the model.
This provides a nice separation of concerns between the controller and model through the dependency inversion principle.
 */
public abstract class AbstractPatientMonitorCollectionModel extends Subject {
    /**
     * This method starts up a new thread and loads new patients.
     */
    public abstract void loadMorePatients();

    /**
     * This method returns the current list of of patient monitors.
     * @return the current list of patient monitors.
     */
    public abstract ArrayList<PatientMonitorModelInterface> getPatientMonitors();

    /**
     * This method starts monitoring a particular patient monitor and notify observers.
     * @param p: the patient monitor.
     */
    public abstract void startMonitoring(PatientMonitorModelInterface p);

    /**
     * This method stops monitoring a particular patient monitor and notify observers.
     * @param p: the patient monitor.
     */
    public abstract void stopMonitoring(PatientMonitorModelInterface p);

    /**
     * This method returns all selected patients that are being monitored on one or more measurements.
     * @return list of selected patient monitors.
     */
    public abstract ArrayList<PatientMonitorModelInterface> getSelectedPatientMonitors();
}
