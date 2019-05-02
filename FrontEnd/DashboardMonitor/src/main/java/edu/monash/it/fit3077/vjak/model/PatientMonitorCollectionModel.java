package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.backendmonitor.HealthMeasurementListener;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PatientMonitorCollectionModel extends AbstractPatientMonitorCollectionModel {
    private final PatientLoaderInterface patientLoader;
    private final ArrayList<PatientMonitorModelInterface> patientMonitors;
    private final HealthMeasurementListener healthMeasurementListener;

    public PatientMonitorCollectionModel(PatientLoaderInterface pl) {
        this.patientLoader = pl;
        this.patientMonitors = new ArrayList<>();
        this.healthMeasurementListener = new HealthMeasurementListener();
    }

    class PatientLoaderRunnable implements Runnable {

        @Override
        public void run() {
            ArrayList<PatientMonitorModelInterface> newPatientMonitors = PatientMonitorCollectionModel.this.patientLoader.loadPatients()
                    .stream()
                    .map(patient -> new PatientMonitorModel(patient, PatientMonitorCollectionModel.this.healthMeasurementListener))
                    .collect(Collectors.toCollection(ArrayList::new));
            PatientMonitorCollectionModel.this.patientMonitors.addAll(newPatientMonitors);
            PatientMonitorCollectionModel.this.notifyObservers();
        }
    }

    public void loadMorePatients() {
        Thread patientLoaderThread = new Thread(new PatientLoaderRunnable());
        patientLoaderThread.start();
    }

    public ArrayList<PatientMonitorModelInterface> getPatientMonitors() {
        return this.patientMonitors;
    }

    public void startMonitoring(PatientMonitorModelInterface p) {
        if (this.patientMonitors.contains(p)) {
            p.trackMeasurements();
            this.notifyObservers();
        }
    }

    public void stopMonitoring(PatientMonitorModelInterface p) {
        p.removeMeasurements();
        this.notifyObservers();
    }

    public ArrayList<PatientMonitorModelInterface> getSelectedPatientMonitors() {
        ArrayList<PatientMonitorModelInterface> selectedPatientMonitors = new ArrayList<>();
        this.patientMonitors.forEach(p -> {
            if (p.isBeingMonitored()) {
                selectedPatientMonitors.add(p);
            }
        });
        return selectedPatientMonitors;
    }
}
