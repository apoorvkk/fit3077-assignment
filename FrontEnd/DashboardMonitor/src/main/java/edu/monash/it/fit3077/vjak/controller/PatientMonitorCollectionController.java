package edu.monash.it.fit3077.vjak.controller;

import edu.monash.it.fit3077.vjak.model.AbstractPatientMonitorCollectionModel;
import edu.monash.it.fit3077.vjak.model.PatientMonitorModelInterface;
import edu.monash.it.fit3077.vjak.view.PatientListView;

public class PatientMonitorCollectionController implements PatientMonitorCollectionInterface {
    private final AbstractPatientMonitorCollectionModel model;
    private final PatientListView view;

    public PatientMonitorCollectionController(AbstractPatientMonitorCollectionModel model, PatientListView view) {
        this.model = model;
        this.view = view;
    }

    public void loadMorePatients() {
        this.view.setViewToFetchingState();
        this.model.loadMorePatients();
    }

    public void startMonitoring(PatientMonitorModelInterface p) {
        this.model.startMonitoring(p);
    }

    public void stopMonitoring(PatientMonitorModelInterface p) {
        this.model.stopMonitoring(p);
    }
}
