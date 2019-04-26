package edu.monash.it.fit3077.vjak.model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MonitorablePatientCollection {
    private PatientLoader patientLoader;
    private ArrayList<MonitorPatient> monitorPatients;

    public MonitorablePatientCollection(PatientLoader pl) {
        this.patientLoader = pl;
        this.monitorPatients = new ArrayList<MonitorPatient>();

        // initialize collection with first set of patients.
        this.loadMorePatientMonitors();
    }

    public void loadMorePatientMonitors() {
        ArrayList<MonitorPatient> newMonitorPatients = this.patientLoader.loadPatients()
                                                            .stream()
                                                            .map(patient -> new MonitorPatient(patient))
                                                            .collect(Collectors.toCollection(ArrayList::new));
        this.monitorPatients.addAll(newMonitorPatients);
    }
}
