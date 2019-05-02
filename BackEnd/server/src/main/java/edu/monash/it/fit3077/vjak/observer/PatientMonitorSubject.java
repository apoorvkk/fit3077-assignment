package edu.monash.it.fit3077.vjak.observer;

import java.util.ArrayList;

/*
This class is responsible for tracking all observers (MonitorControllerObserver) and notifying them whenever needed.
Apart of the observer design pattern.
 */
public abstract class PatientMonitorSubject {
    protected final ArrayList<MonitorControllerObserver> observers;

    protected PatientMonitorSubject() {
        this.observers = new ArrayList<>();
    }

    public void attach(MonitorControllerObserver o) {
        if (!this.observers.contains(o)) {
            this.observers.add(o);
        }
    }

    public void detach(MonitorControllerObserver o) {
        this.observers.remove(o);
    }

    public abstract void notifyObservers(String clientId);
}

