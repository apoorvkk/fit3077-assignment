package edu.monash.it.fit3077.akql.controller;

import edu.monash.it.fit3077.akql.model.PatientMonitorModelInterface;
import edu.monash.it.fit3077.akql.view.PatientMonitorView;

/*
This class is responsible to respond to user events such as selecting health measurements to monitor for a particular patient.
It is also repsonsible to initialize the patient monitor view in the main dashboard.
 */
public class PatientMonitorController implements PatientMonitorControllerInterface {
    private final PatientMonitorModelInterface model;
    private final PatientMonitorView view;

    /**
     * Setup dependencies with model and view and then intialize the view.
     * @param pm: the patient monitor model which has business logic to manage the patient's health measurements.
     * @param view: the main view which represents the patient being monitored and their selected measurements shown (eg. cholesterol level).
     */
    public PatientMonitorController(PatientMonitorModelInterface pm, PatientMonitorView view) {
        this.view = view;
        this.model = pm;

        this.view.initialize();
    }

    /**
     * Used to select a measurement to see for a particular patient (eg. systolic blood pressure).
     * @param measurementType: the measurement of interest (eg. cholesterol).
     */
    public void trackMeasurement(String measurementType) {
        this.model.trackMeasurement(measurementType);
    }

    /**
     * Used to stop monitoring a measurement for a particular patient (eg. systolic blood pressure).
     * @param measurementType: the measurement of interest (eg. cholesterol).
     */
    public void removeHealthMeasurement(String measurementType) {
        this.model.removeHealthMeasurement(measurementType);
    }
}
