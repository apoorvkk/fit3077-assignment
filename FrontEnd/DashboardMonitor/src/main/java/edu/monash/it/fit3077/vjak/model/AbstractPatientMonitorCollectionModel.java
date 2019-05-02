package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.observer.Subject;

import java.util.ArrayList;

/*
This class is responsible for defining a contract that callers (eg. controllers) can use to manipulate the model.
This provides a nice separation of concerns between the controller and model through the dependency inversion principle.
 */
public abstract class AbstractPatientMonitorCollectionModel extends Subject {
    public abstract void loadMorePatients();
    public abstract ArrayList<PatientMonitorModelInterface> getPatientMonitors();
    public abstract void startMonitoring(PatientMonitorModelInterface p);
    public abstract void stopMonitoring(PatientMonitorModelInterface p);
    public abstract ArrayList<PatientMonitorModelInterface> getSelectedPatientMonitors();
}
