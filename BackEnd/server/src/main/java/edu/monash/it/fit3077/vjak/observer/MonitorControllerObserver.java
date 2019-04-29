package edu.monash.it.fit3077.vjak.observer;

import edu.monash.it.fit3077.vjak.model.PatientMonitor;

public interface MonitorControllerObserver {
    void update(PatientMonitor monitor, String clientId);
}
