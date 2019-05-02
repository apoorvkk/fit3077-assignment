package edu.monash.it.fit3077.vjak.model;

import java.util.ArrayList;

/*
This class is responsible for maintaining a collection of all patient monitors and adding/removing them.
 */
public class PatientMonitorCollectionModel {
    private final ArrayList<PatientMonitorModel> patientMonitors;

    public PatientMonitorCollectionModel() {
        this.patientMonitors = new ArrayList<>();
    }

    private PatientMonitorModel getPatientMonitor(String patientId, String measurementType) {
        for (PatientMonitorModel pm: this.patientMonitors) {
            if (pm.matches(patientId, measurementType)){
                return pm;
            }
        }
        return null;
    }

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
