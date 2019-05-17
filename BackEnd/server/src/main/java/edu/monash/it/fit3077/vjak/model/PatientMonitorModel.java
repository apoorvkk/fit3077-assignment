package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.observer.MonitorControllerObserver;
import edu.monash.it.fit3077.vjak.observer.PatientMonitorSubject;

import java.util.ArrayList;
import java.util.Dictionary;

/*
This class represents a generic patient monitor. Essentially, it will hold all clients (i.e frontend client) who
wish to monitor a selected patient on a particular measurement (eg. cholesterol). The class will poll and fetch the latest
observation every hour and then notify all observers about the new observation. If there are no clients watching this
monitor, then it will terminate and stop polling.
This class also caches the observation data so that if a new registered client joins, they can easily fetch this cached value
and leave polling to every hour rather than to every new client registered.
 */
public abstract class PatientMonitorModel extends PatientMonitorSubject {
    protected final ArrayList<String> clientIds;
    protected final String patientId;
    protected ObservationLoaderInterface observationLoader;
    private boolean shouldTerminateThread;

    PatientMonitorModel(String patientId, String firstClientId) {
        this.patientId = patientId;
        this.clientIds = new ArrayList<>();
        this.clientIds.add(firstClientId);
    }

    /*
    This inner class is used to start a new thread to poll every hour and not hog up the main thread.
     */
    class PollingRunnable implements Runnable {
        @Override
        public void run() {
            while (!PatientMonitorModel.this.shouldTerminateThread) {
                PatientMonitorModel.this.fetchData();
                try {
                    Thread.sleep(PatientMonitorModel.this.getPollingTime()); // Polls every hour.
                    System.out.println("Polled 10 seconds");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    abstract void fetchData();

    protected int getPollingTime() {
        return 3600000;
    }

    protected void poll() {
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

    String getPatientId() {
        return this.patientId;
    }

    // These methods are specific to actual patient monitors (eg. cholesterol patient monitor).
    protected abstract String getMeasurementCode();
    public abstract String getMeasurementType();

    public abstract MonitorEventModel serialize();
}
