package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.api.ObservationLoaderInterface;
import edu.monash.it.fit3077.vjak.observer.MonitorControllerObserver;
import edu.monash.it.fit3077.vjak.observer.PatientMonitorSubject;

import java.util.ArrayList;

/**
 * This class represents a generic patient monitor. Essentially, it will hold all clients (i.e frontend client) who
 * wish to monitor a selected patient on a particular measurement (eg. cholesterol). The class will poll and fetch the latest
 * observation every hour and then notify all observers about the new observation. If there are no clients watching this
 * monitor, then it will terminate and stop polling.
 * This class also caches the observation data so that if a new registered client joins, they can easily fetch this cached value
 * and leave polling to every hour rather than to every new client registered.
 */
public abstract class PatientMonitorModel extends PatientMonitorSubject {
    protected final ArrayList<String> clientIds;
    protected final String patientId;
    protected ObservationLoaderInterface observationLoader;
    private boolean shouldTerminateThread;

    /**
     * Initializes the health measurement patient monitor.
     * @param patientId: the id of the patient to be monitored.
     * @param firstClientId: the id of the first client to request monitoring of this patient.
     */
    public PatientMonitorModel(String patientId, String firstClientId) {
        this.patientId = patientId;
        this.clientIds = new ArrayList<>();
        this.clientIds.add(firstClientId);
    }

    /**
     * This inner class is used to start a new thread to poll every hour and not hog up the main thread.
     */
    class PollingRunnable implements Runnable {
        @Override
        public void run() {
            while (!PatientMonitorModel.this.shouldTerminateThread) {
                PatientMonitorModel.this.fetchData();
                try {
                    Thread.sleep(PatientMonitorModel.this.getPollingTime()); // Polls every hour.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Gets the latest health measurement data and stores it inside this model.
     */
    protected abstract void fetchData();

    /**
     * Gets the polling time (how frequent this monitor polls the system for new health measurement data).
     * @return the polling time of this health measurement monitor.
     */
    protected int getPollingTime() {
        return 3600000;
    }

    /**
     * Polls the server for new health measurement data.
     */
    protected void poll() {
        this.shouldTerminateThread = false;
        Thread pollThread = new Thread(new PollingRunnable());
        pollThread.start();
    }

    /**
     * Notifies a specified observer that a change has happened to this patient's health measurement.
     * @param clientId: the id of the observer which the change is going to be notified to.
     */
    public void notifyObservers(String clientId) {
        for (MonitorControllerObserver o: this.observers) {
            o.update(this, clientId);
        }
    }

    /**
     * Registers a new client who wants to listen to changes to this patient's health measurement data.
     * @param clientId: the id of the client to be registered.
     */
    void registerNewClient(String clientId) { // A new client would like to observe the monitor.
        if (!this.clientIds.contains(clientId)) {
            this.clientIds.add(clientId);
            /*
            Notify observers of new client registered and hence fetch the cached observation.
            This ensures that registered clients immediately receive the cached observation and not wait until the next
            polled observation.
             */
            this.notifyObservers(clientId);
        }
    }

    /**
     * Terminate the thread.
     */
    void cleanUp() {
        this.shouldTerminateThread = true;
    }

    /**
     * Checks if this patient monitor's monitored patient id and health measurement type is equal to specified patient
     * id and monitored health measurement type.
     * @param patientId: the id of the patient.
     * @param measurementType: the type of health measurement type.
     * @return whether this patient monitor's monitored patient id and health measurement type are both equal to the
     * specified patient id and monitored health measurement type.
     */
    boolean matches(String patientId, String measurementType){
        return this.patientId.equals(patientId) && this.getMeasurementType().equals(measurementType);
    }

    /**
     * Removes a client listening to this patient monitor.
     * @param clientId: the id of the client to be removed.
     */
    void removeClient(String clientId) {
        this.clientIds.remove(clientId);
    }

    /**
     * Checks if this patient monitor has any monitoring clients.
     * @return whether this patient monitor has any monitoring clients.
     */
    boolean hasNoRegisteredClients() {
        return this.clientIds.size() == 0;
    }

    /**
     * Gets the id of the patient being monitored by this patient monitor.
     * @return the id of the patient being monitored by this patient monitor.
     */
    String getPatientId() {
        return this.patientId;
    }

    /**
     * Gets the code of the health measurement of the HAPI server.
     * @return the code of the health measurement of the HAPI server.
     */
    protected abstract String getMeasurementCode();

    /**
     * Gets the type of health measurement this monitor is monitoring.
     * @return the type of health measurement this monitor is monitoring.
     */
    public abstract String getMeasurementType();

    /**
     * Serializes the health measurement monitor object so that it can be sent via the web sockets.
     * @return the serialized health measurement monitor event model.
     */
    public abstract MonitorEventModel serialize();
}
