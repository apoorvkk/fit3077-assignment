package edu.monash.it.fit3077.vjak.model;

import java.util.ArrayList;

public class PatientMonitorCollection {
    private final ArrayList<PatientMonitor> patientMonitors;

    public PatientMonitorCollection(ArrayList<PatientMonitor> patientMonitors) {
        this.patientMonitors = patientMonitors;
    }

    private PatientMonitor getPatientMonitor(String patientId, String measurementType) {
        for (PatientMonitor pm: this.patientMonitors) {
            if (pm.matches(patientId, measurementType)){
                return pm;
            }
        }
        return null;
    }

    public PatientMonitor addMonitor(String patientId, String clientId, String measurementType){
        PatientMonitor monitor = this.getPatientMonitor(patientId, measurementType);
        monitor.registerNewClient(clientId);

        if (monitor == null) {
            monitor = PatientMonitorCreator.createMonitor(measurementType, patientId, clientId);
            this.patientMonitors.add(monitor);
        }

        return monitor;
    }

    public void deleteMonitor(String patientId, String clientId, String measurementType) {
        PatientMonitor existingMonitor = this.getPatientMonitor(patientId, measurementType);
        if (existingMonitor != null) {
            existingMonitor.removeClient(clientId);
            if (existingMonitor.hasNoRegisteredClients()) {
                existingMonitor.cleanUp();
                this.patientMonitors.remove(existingMonitor);
            }
        }
    }
}
