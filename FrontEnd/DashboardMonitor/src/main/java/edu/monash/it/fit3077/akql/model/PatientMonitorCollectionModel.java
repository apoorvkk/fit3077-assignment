package edu.monash.it.fit3077.akql.model;

import edu.monash.it.fit3077.akql.backendmonitor.HealthMeasurementListener;

import java.util.ArrayList;
import java.util.stream.Collectors;

/*
This class is responsible for managing all patient monitors. This includes loading patients and start/stop patient monitors.
 */
public class PatientMonitorCollectionModel extends AbstractPatientMonitorCollectionModel {
    private final PatientLoaderInterface patientLoader;
    private final ArrayList<PatientMonitorModelInterface> patientMonitors;
    private final HealthMeasurementListener healthMeasurementListener;

    /**
     * Initialize the patient loader, patientMonitors and the healthMeasurementListeners.
     * @param pl: the patient loader that is used to fetch patients.
     */
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

    /**
     * This method starts up a new thread and loads new patients.
     */
    public void loadMorePatients() {
        Thread patientLoaderThread = new Thread(new PatientLoaderRunnable());
        patientLoaderThread.start();
    }

    /**
     * This method returns the current list of of patient monitors.
     * @return the current list of patient monitors.
     */
    public ArrayList<PatientMonitorModelInterface> getPatientMonitors() {
        return this.patientMonitors;
    }

    /**
     * This method starts monitoring a particular patient monitor and notify observers.
     * @param p: the patient monitor.
     */
    public void startMonitoring(PatientMonitorModelInterface p) {
        if (this.patientMonitors.contains(p)) {
            p.startMonitoring();
            this.notifyObservers();
        }
    }

    /**
     * This method stops monitoring a particular patient monitor and notify observers.
     * @param p: the patient monitor.
     */
    public void stopMonitoring(PatientMonitorModelInterface p) {
        p.stopMonitoring();
        this.notifyObservers();
    }

    /**
     * This method returns all selected patients that are being monitored on one or more measurements.
     * @return list of selected patient monitors.
     */
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
