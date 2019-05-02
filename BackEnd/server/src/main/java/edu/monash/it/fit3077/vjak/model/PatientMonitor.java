package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationLoader;
import edu.monash.it.fit3077.vjak.observer.MonitorControllerObserver;
import edu.monash.it.fit3077.vjak.observer.PatientMonitorSubject;

import java.util.ArrayList;

public abstract class PatientMonitor extends PatientMonitorSubject {
    private final ArrayList<String> clientIds;
    private final String patientId;
    private final ObservationLoaderInterface observationLoader;
    private String measurementUnit;
    private String measurementValue;
    private boolean shouldTerminateThread;

    PatientMonitor(String patientId, String firstClientId) {
        this.patientId = patientId;
        this.clientIds = new ArrayList<>();
        this.clientIds.add(firstClientId);
        this.observationLoader = new HapiObservationLoader();
        this.poll();
    }

    class PollingRunnable implements Runnable {
        @Override
        public void run() {
            while (!PatientMonitor.this.shouldTerminateThread) {

                ObservationModelInterface latestObservation = PatientMonitor.this.observationLoader.getLatestObservation(PatientMonitor.this.patientId, PatientMonitor.this.getMeasurementCode());

                if (latestObservation != null) {
                    PatientMonitor.this.measurementValue = latestObservation.getValue();
                    PatientMonitor.this.measurementUnit = latestObservation.getUnit();


                    for (String clientId: PatientMonitor.this.clientIds) {
                        PatientMonitor.this.notifyObservers(clientId);
                    }
                }


                try {
                    Thread.sleep(3600000); // Polls every hour.
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

    public void notifyObservers(String clientId) {
        for (MonitorControllerObserver o: this.observers) {
            o.update(this, clientId);
        }
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

    public String getMeasurementUnit() {
        return this.measurementUnit;
    }

    public String getMeasurementValue() {
        return this.measurementValue;
    }

    public String getPatientId() {
        return this.patientId;
    }

    protected abstract String getMeasurementCode();
    public abstract String getMeasurementType();
}
