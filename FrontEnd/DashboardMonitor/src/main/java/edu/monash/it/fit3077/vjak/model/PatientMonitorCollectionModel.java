package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.backendmonitor.HealthMeasurementListener;

import java.util.ArrayList;
import java.util.stream.Collectors;

/*
This class is responsible for managing all patient monitors. This includes loading patients and start/stop patient monitors.
 */
public class PatientMonitorCollectionModel extends AbstractPatientMonitorCollectionModel {
    private final PatientLoaderInterface patientLoader;
    private final ArrayList<PatientMonitorModelInterface> patientMonitors;
    private final HealthMeasurementListener healthMeasurementListener;

    public PatientMonitorCollectionModel(PatientLoaderInterface pl) {
        this.patientLoader = pl;
        this.patientMonitors = new ArrayList<>();
        this.healthMeasurementListener = new HealthMeasurementListener();
    }

    /*
    This class is responsible for fetching patients asynchronously via threads and not hog up the main thread which
    would freeze the GUI application.
     */
    class PatientLoaderRunnable implements Runnable {

        @Override
        public void run() {
            ArrayList<PatientMonitorModelInterface> newPatientMonitors = PatientMonitorCollectionModel.this.patientLoader.loadPatients()
                    .stream()
                    .map(patient -> new PatientMonitorModel(patient, PatientMonitorCollectionModel.this.healthMeasurementListener))
                    .collect(Collectors.toCollection(ArrayList::new));
            PatientMonitorCollectionModel.this.patientMonitors.addAll(newPatientMonitors);
            PatientMonitorCollectionModel.this.notifyObservers(); // Make sure to notify observers that new patients monitors have been added.
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
            p.startMonitoring();
            this.notifyObservers();
        }
    }

    public void stopMonitoring(PatientMonitorModelInterface p) {
        p.stopMonitoring();
        this.notifyObservers();
    }

    public ArrayList<PatientMonitorModelInterface> getSelectedPatientMonitors() {
        // This method returns all selected patients that are being monitored on one or more measurements.
        ArrayList<PatientMonitorModelInterface> selectedPatientMonitors = new ArrayList<>();
        this.patientMonitors.forEach(p -> {
            if (p.isBeingMonitored()) {
                selectedPatientMonitors.add(p);
            }
        });
        return selectedPatientMonitors;
    }
}
