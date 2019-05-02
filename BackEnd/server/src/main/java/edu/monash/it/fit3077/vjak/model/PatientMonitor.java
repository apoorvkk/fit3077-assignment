package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationLoader;
import edu.monash.it.fit3077.vjak.observer.MonitorControllerObserver;
import edu.monash.it.fit3077.vjak.observer.PatientMonitorSubject;

import java.util.ArrayList;

/*
This class represents a generic patient monitor. Essentially, it will hold all clients (i.e frontend client) who
wish to monitor a selected patient on a particular measurement (eg. cholesterol). The class will poll and fetch the latest
observation every hour and then notify all observers about the new observation. If there are no clients watching this
monitor, then it will terminate and stop polling.
This class also caches the observation data so that if a new registered client joins, they can easily fetch this cached value
and leave polling to every hour rather than to every new client registered.
 */
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

    /*
    This inner class is used to start a new thread to poll every hour and not hog up the main thread.
     */
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

    void registerNewClient(String clientId) { // A new client would like to observe the monitor.
        if (!this.clientIds.contains(clientId)) {
            this.clientIds.add(clientId);
            /*
            Notify observers of new client registered and hence fetch the cached observation.
            This ensures that registered clients immediately receive the cached observation and not wait until the next polled
            observation.
             */
            this.notifyObservers(clientId);
        }
    }

    void cleanUp() {
        this.shouldTerminateThread = true;
    }

    boolean matches(String patientId, String measurementType){
        return this.patientId.equals(patientId) && this.getMeasurementType().equals(measurementType);
    }

    void removeClient(String clientId) {
        this.clientIds.remove(clientId);
    }

    boolean hasNoRegisteredClients() {
        return this.clientIds.size() == 0;
    }

    String getMeasurementUnit() {
        return this.measurementUnit;
    }

    String getMeasurementValue() {
        return this.measurementValue;
    }

    String getPatientId() {
        return this.patientId;
    }

    // These methods are specific to actual patient monitors (eg. cholesterol patient monitor).
    protected abstract String getMeasurementCode();
    public abstract String getMeasurementType();
}
