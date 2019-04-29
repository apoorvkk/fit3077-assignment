package edu.monash.it.fit3077.vjak.observer;

import java.util.ArrayList;

public class PatientMonitorSubject {
    private ArrayList<MonitorControllerObserver> observers;

    public PatientMonitorSubject() {
        this.observers = new ArrayList<>();
    }

    public void attach(MonitorControllerObserver o) {
        if (!this.observers.contains(o)) {
            this.observers.add(o);
        }
    };

    public void detach(MonitorControllerObserver o) {
        if (this.observers.contains(o)) {
            this.observers.remove(o);
        }
    }

    public void notifyObservers(String clientId) {
        for (MonitorControllerObserver o: this.observers) {
            o.update(clientId);
        }
    }
}

