package edu.monash.it.fit3077.akql.model;

import java.util.ArrayList;

/**
 * This class is responsible for maintaining a collection of all patient monitors and adding/removing them.
 */
public class PatientMonitorCollectionModel {
    private final ArrayList<PatientMonitorModel> patientMonitors;

    /**
     * Initializes the patient monitor collection.
     */
    public PatientMonitorCollectionModel() {
        this.patientMonitors = new ArrayList<>();
    }

    /**
     * Gets a patient monitor from the list of patient monitors.
     * @param patientId: the id of the patient being monitored by the monitor.
     * @param measurementType: the type of measurement being monitored by the monitor.
     * @return the matching patient monitor.
     */
    private PatientMonitorModel getPatientMonitor(String patientId, String measurementType) {
        for (PatientMonitorModel pm: this.patientMonitors) {
            if (pm.matches(patientId, measurementType)){
                return pm;
            }
        }
        return null;
    }

    /**
     * Adds a new monitor to the list of patient monitors.
     * @param requestMonitorInfoModel: the information of the monitor requested to be added.
     * @return the patient monitor corresponding to the request.
     */
    public PatientMonitorModel addMonitor(RequestMonitorInfoModel requestMonitorInfoModel){
        PatientMonitorModel monitor = this.getPatientMonitor(requestMonitorInfoModel.getPatientId(), requestMonitorInfoModel.getMeasurementType());

        if (monitor == null) { // No other clients are observing this patient and measurement type. Hence, create the monitor.
            monitor = PatientMonitorCreator.createMonitor(requestMonitorInfoModel);
            this.patientMonitors.add(monitor);
        } else { // There is already one or more clients observing this patient and measurement type. Hence, add to existing monitor.
            monitor.registerNewClient(requestMonitorInfoModel.getClientId());
        }

        return monitor;
    }

    /**
     * Removes a monitor from the list of patient monitors.
     * @param requestMonitorInfoModel: the information of the monitor requested to be removed.
     */
    public void deleteMonitor(RequestMonitorInfoModel requestMonitorInfoModel) {
        PatientMonitorModel existingMonitor = this.getPatientMonitor(requestMonitorInfoModel.getPatientId(), requestMonitorInfoModel.getMeasurementType());
        if (existingMonitor != null) {
            existingMonitor.removeClient(requestMonitorInfoModel.getClientId());
            if (existingMonitor.hasNoRegisteredClients()) { // If there are no more registered clients, terminate the monitor.
                existingMonitor.cleanUp();
                this.patientMonitors.remove(existingMonitor);
            }
        }
    }
}
