package edu.monash.it.fit3077.vjak.controller;

import edu.monash.it.fit3077.vjak.model.AbstractPatientMonitorCollectionModel;
import edu.monash.it.fit3077.vjak.view.PatientListView;

public class PatientMonitorCollectionController implements PatientMonitorCollectionInterface {
    private AbstractPatientMonitorCollectionModel model;
    private PatientListView view;

    public PatientMonitorCollectionController(AbstractPatientMonitorCollectionModel model, PatientListView view) {
        this.model = model;
        this.view = view;
    }

    public void loadMorePatients() {
        this.view.setViewToFetchingState();
        this.model.loadMorePatients();
    }
}
