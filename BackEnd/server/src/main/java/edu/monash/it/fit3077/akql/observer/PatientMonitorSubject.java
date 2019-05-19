package edu.monash.it.fit3077.akql.observer;

import java.util.ArrayList;

/**
 * This class is responsible for tracking all observers (MonitorControllerObserver) and notifying them whenever needed.
 * Apart of the observer design pattern.
 */
public abstract class PatientMonitorSubject {
    protected final ArrayList<MonitorControllerObserver> observers;

    /**
     * Initialize the patient's health measurement to be monitored.
     */
    protected PatientMonitorSubject() {
        this.observers = new ArrayList<>();
    }

    /**
     * Adds a new observer that wants to monitor this patient's health measurement to the current list of observers.
     * @param o: the new observer that wants to monitor this patient's health measurement.
     */
    public void attach(MonitorControllerObserver o) {
        if (!this.observers.contains(o)) {
            this.observers.add(o);
        }
    }

    /**
     * Removes an observer that does not want to monitor this patient's health measurement from the list of observers.
     * @param o: the observer that does not want to monitor this patient's health measurement anymore.
     */
    public void detach(MonitorControllerObserver o) {
        this.observers.remove(o);
    }

    /**
     * Notifies a specified observer that a change has happened to this patient's health measurement.
     * @param clientId: the id of the observer which the change is going to be notified to.
     */
    public abstract void notifyObservers(String clientId);
}

