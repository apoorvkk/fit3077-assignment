package edu.monash.it.fit3077.vjak.observer;

import edu.monash.it.fit3077.vjak.model.PatientMonitorModel;

/**
 * Apart of the observer design pattern. Classes that implement this interface, will be notified about a monitor and
 * client id upon the subject's discretion.
 */
public interface MonitorControllerObserver {

    /**
     * Perform some operation on the new monitor data.
     * @param monitor: the health measurement monitor that is going to have its data sent.
     * @param clientId: the id of the client which this health measurement data is going to be sent to.
     */
    void update(PatientMonitorModel monitor, String clientId);
}
