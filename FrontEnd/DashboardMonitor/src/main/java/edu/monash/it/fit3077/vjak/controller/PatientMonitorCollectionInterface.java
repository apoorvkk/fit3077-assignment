package edu.monash.it.fit3077.vjak.controller;

import edu.monash.it.fit3077.vjak.model.PatientMonitorModelInterface;

public interface PatientMonitorCollectionInterface {
    void loadMorePatients();
    void startMonitoring(PatientMonitorModelInterface p);
    void stopMonitoring(PatientMonitorModelInterface p);
}
