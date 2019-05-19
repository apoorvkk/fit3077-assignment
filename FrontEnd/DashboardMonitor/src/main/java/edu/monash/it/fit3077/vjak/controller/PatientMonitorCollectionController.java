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

    /**
     * Setup dependencies with model and view and then intialize the view and load first set of patients into the model.
     * @param model: the patient monitor collection model which has business logic to manage the patient monitors.
     * @param view: the sidebar view which hold all available patients.
     */
    public PatientMonitorCollectionController(AbstractPatientMonitorCollectionModel model, PatientListView view) {
        this.model = model;
        this.view = view;
        this.view.initializePatientList();
        this.view.initializeLoadMoreButton();
        this.loadMorePatients();
    }

    /**
     * Sets the view in fetching state and tells the model to load more patients.
     */
    public void loadMorePatients() {
        this.view.setViewToFetchingState();
        this.model.loadMorePatients();
    }

    /**
     * Tell model to select a patient monitor as the end-user has selected it.
     * @param p: the patient monitor.
     */
    public void startMonitoring(PatientMonitorModelInterface p) {
        this.model.startMonitoring(p);
    }

    /**
     * Tell model to stop monitoring patient completely as the end-user does not want to see this patient's health measurement data.
     * @param p the patient monitor.
     */
    public void stopMonitoring(PatientMonitorModelInterface p) {
        this.model.stopMonitoring(p);
    }
}
