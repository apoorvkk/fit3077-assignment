package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.backendmonitor.HealthMeasurementListener;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MonitorablePatientCollection {
    private PatientLoader patientLoader;
    private ArrayList<MonitorPatient> monitorPatients;
    private HealthMeasurementListener healthMeasurementListener;

    public MonitorablePatientCollection(PatientLoader pl) {
        this.patientLoader = pl;
        this.monitorPatients = new ArrayList<>();
        this.healthMeasurementListener = new HealthMeasurementListener();

        // initialize collection with first set of patients.
        this.loadMorePatientMonitors();
    }

    public void loadMorePatientMonitors() {
        ArrayList<MonitorPatient> newMonitorPatients = this.patientLoader.loadPatients()
                                                            .stream()
                                                            .map(patient -> new MonitorPatient(patient, this.healthMeasurementListener))
                                                            .collect(Collectors.toCollection(ArrayList::new));
        this.monitorPatients.addAll(newMonitorPatients);
    }
}
