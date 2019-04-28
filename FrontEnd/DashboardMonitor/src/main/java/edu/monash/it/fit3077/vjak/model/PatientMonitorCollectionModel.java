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
    }

    public class PatientLoaderRunnable implements Runnable {

        @Override
        public void run() {
            ArrayList<AbstractPatientMonitorModel> newPatientMonitors = PatientMonitorCollectionModel.this.patientLoader.loadPatients()
                    .stream()
                    .map(patient -> new PatientMonitorModel(patient, PatientMonitorCollectionModel.this.healthMeasurementListener))
                    .collect(Collectors.toCollection(ArrayList::new));
            PatientMonitorCollectionModel.this.patientMonitors.addAll(newPatientMonitors);
            System.out.println(Thread.currentThread().getName());
            PatientMonitorCollectionModel.this.notifyObservers();
        }
    }
    public void loadMorePatients() {
        Thread patientLoaderThread = new Thread(new PatientLoaderRunnable());
        patientLoaderThread.start();
    }

    public ArrayList<AbstractPatientMonitorModel> getPatientMonitors() {
        return this.patientMonitors;
    }
}
