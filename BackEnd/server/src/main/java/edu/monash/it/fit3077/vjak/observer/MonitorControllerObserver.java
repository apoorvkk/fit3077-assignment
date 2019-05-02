package edu.monash.it.fit3077.vjak.observer;

import edu.monash.it.fit3077.vjak.model.PatientMonitorModel;

/*
Apart of the observer design pattern. Classes that implement this interface, will be notified about a monitor and
client id upon the subject's discretion.
 */
public interface MonitorControllerObserver {
    void update(PatientMonitorModel monitor, String clientId);
}
