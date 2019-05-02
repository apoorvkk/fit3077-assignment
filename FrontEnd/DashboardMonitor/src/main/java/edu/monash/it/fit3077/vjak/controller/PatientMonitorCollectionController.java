package edu.monash.it.fit3077.vjak.controller;

import edu.monash.it.fit3077.vjak.model.AbstractPatientMonitorCollectionModel;
import edu.monash.it.fit3077.vjak.model.PatientMonitorModelInterface;
import edu.monash.it.fit3077.vjak.view.PatientListView;

/*
This class is responsible for applying changes to the model based on user input events. It will load more patients and
start/stop monitoring a particular patient. This class forms the controller part of the MVC design pattern.
 */
public class PatientMonitorCollectionController implements PatientMonitorCollectionControllerInterface {
    private final AbstractPatientMonitorCollectionModel model;
    private final PatientListView view;

    public PatientMonitorCollectionController(AbstractPatientMonitorCollectionModel model, PatientListView view) {
        // Setup dependencies with model and view and then intialize the view and load first set of patients into the model.
        this.model = model;
        this.view = view;
        this.view.initializePatientList();
        this.view.initializeLoadMoreButton();
        this.loadMorePatients();
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
