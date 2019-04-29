package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationLoader;
import edu.monash.it.fit3077.vjak.observer.PatientMonitorSubject;

import java.util.ArrayList;

public abstract class PatientMonitor extends PatientMonitorSubject {
    private final ArrayList<String> clientIds;
    private final String patientId;
    private final ObservationLoaderInterface observationLoader;
    private String measurementUnit;
    private String measurementValue;
    private boolean shouldTerminateThread;

    public PatientMonitor(String patientId, String firstClientId) {
        this.patientId = patientId;
        this.clientIds = new ArrayList<String>();
        this.clientIds.add(firstClientId);
        this.observationLoader = new HapiObservationLoader();

        this.poll();
    }

    public class PollingRunnable implements Runnable {
        @Override
        public void run() {
            while (!PatientMonitor.this.shouldTerminateThread) {
                ObservationModelInterface latestObservation = PatientMonitor.this.observationLoader.getLatestObservation(patientId, PatientMonitor.this.getMeasurementCode());
                PatientMonitor.this.measurementValue = latestObservation.getValue();
                PatientMonitor.this.measurementUnit = latestObservation.getUnit();


                for (String clientId: PatientMonitor.this.clientIds) {
                    PatientMonitor.this.notifyObservers(clientId);
                }

                try {
                    Thread.sleep(2000); // change to 1hr.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void poll() {
        this.shouldTerminateThread = false;
        Thread pollThread = new Thread(new PollingRunnable());
        pollThread.start();
    }

    public void registerNewClient(String clientId) {
        if (!this.clientIds.contains(clientId)) {
            this.clientIds.add(clientId);
            this.notifyObservers(clientId);
        }
    }

    public void cleanUp() {
        this.shouldTerminateThread = true;
    }
    public boolean matches(String patientId, String measurementType){
        return this.patientId.equals(patientId) && this.getMeasurementType().equals(measurementType);
    }

    public void removeClient(String clientId) {
        this.clientIds.remove(clientId);
    }

    public boolean hasNoRegisteredClients() {
        return this.clientIds.size() == 0;
    }

    protected abstract String getMeasurementCode();
    public abstract String getMeasurementType();
}
