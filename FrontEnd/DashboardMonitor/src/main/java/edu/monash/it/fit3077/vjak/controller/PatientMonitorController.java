package edu.monash.it.fit3077.vjak.controller;

import edu.monash.it.fit3077.vjak.model.PatientMonitorModelInterface;
import edu.monash.it.fit3077.vjak.view.PatientMonitorView;

public class PatientMonitorController implements PatientMonitorControllIerInterface {
    private final PatientMonitorModelInterface model;
    private final PatientMonitorView view;

    public PatientMonitorController(PatientMonitorModelInterface pm, PatientMonitorView view) {
        this.view = view;
        this.model = pm;

        this.view.initialize();
    }

    public void trackMeasurement(String measurementType) {
        this.model.trackMeasurement(measurementType);
    }

    public void removeHealthMeasurement(String measurementType) {
        this.model.removeHealthMeasurement(measurementType);
    }
}
