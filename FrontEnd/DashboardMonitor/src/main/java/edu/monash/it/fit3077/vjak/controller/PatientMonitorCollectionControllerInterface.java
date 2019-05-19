package edu.monash.it.fit3077.vjak.controller;

import edu.monash.it.fit3077.vjak.model.PatientMonitorModelInterface;

/*
This interface is responsible for defining a contract that callers (eg. views) can use to manipulate the necessary models.
This provides a nice separation of concerns between the controller and view through the dependency inversion principle.
Here, the view will depend on this controller interface rather than internal controller.
 */
public interface PatientMonitorCollectionControllerInterface {
    /**
     * Used to load more patients into the system.
     */
    void loadMorePatients();

    /**
     * Initiate monitoring of a patient.
     * @param p: patient monitor.
     */
    void startMonitoring(PatientMonitorModelInterface p);
    /**
     * Stop monitoring a patient.
     * @param p: patient monitor.
     */
    void stopMonitoring(PatientMonitorModelInterface p);
}
