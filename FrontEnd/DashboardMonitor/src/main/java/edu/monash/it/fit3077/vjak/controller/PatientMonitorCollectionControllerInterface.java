package edu.monash.it.fit3077.vjak.controller;

import edu.monash.it.fit3077.vjak.model.PatientMonitorModelInterface;

/*
This interface is responsible for defining a contract that callers (eg. views) can use to manipulate the necessary models.
This provides a nice separation of concerns between the controller and view through the dependency inversion principle.
 */
public interface PatientMonitorCollectionControllerInterface {
    void loadMorePatients();
    void startMonitoring(PatientMonitorModelInterface p);
    void stopMonitoring(PatientMonitorModelInterface p);
}
