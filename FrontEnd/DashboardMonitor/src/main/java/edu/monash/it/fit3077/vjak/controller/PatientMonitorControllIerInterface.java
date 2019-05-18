package edu.monash.it.fit3077.vjak.controller;

public interface PatientMonitorControllIerInterface {
    void trackMeasurement(String measurementType);
    void removeHealthMeasurement(String measurementType);
}
