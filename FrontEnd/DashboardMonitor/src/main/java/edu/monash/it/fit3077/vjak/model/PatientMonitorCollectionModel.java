package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.backendmonitor.HealthMeasurementListener;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PatientMonitorCollectionModel extends AbstractPatientMonitorCollectionModel {
    private PatientLoaderInterface patientLoader;
    private ArrayList<AbstractPatientMonitorModel> patientMonitors;
    private HealthMeasurementListener healthMeasurementListener;

    public PatientMonitorCollectionModel(PatientLoaderInterface pl) {
        this.patientLoader = pl;
        this.patientMonitors = new ArrayList<>();
        this.healthMeasurementListener = new HealthMeasurementListener();

        // initialize collection with first set of patients.
        this.loadMorePatients();
    }

    public void loadMorePatients() {
        ArrayList<AbstractPatientMonitorModel> newPatientMonitors = this.patientLoader.loadPatients()
                                                            .stream()
                                                            .map(patient -> new PatientMonitorModel(patient, this.healthMeasurementListener))
                                                            .collect(Collectors.toCollection(ArrayList::new));
        this.patientMonitors.addAll(newPatientMonitors);
        this.notifyObservers();
    }

    public ArrayList<AbstractPatientMonitorModel> getPatientMonitors() {
        return this.patientMonitors;
    }
}
