package edu.monash.it.fit3077.vjak.model;

import java.util.ArrayList;

public class PatientMonitorCollection {
    private final ArrayList<PatientMonitor> patientMonitors;

    public PatientMonitorCollection() {
        this.patientMonitors = new ArrayList<>();
    }

    private PatientMonitor getPatientMonitor(String patientId, String measurementType) {
        for (PatientMonitor pm: this.patientMonitors) {
            if (pm.matches(patientId, measurementType)){
                return pm;
            }
        }
        return null;
    }

    public PatientMonitor addMonitor(RequestMonitorInfo requestMonitorInfo){
        PatientMonitor monitor = this.getPatientMonitor(requestMonitorInfo.getPatientId(), requestMonitorInfo.getMeasurementType());

        if (monitor == null) {
            monitor = PatientMonitorCreator.createMonitor(requestMonitorInfo);
            this.patientMonitors.add(monitor);
        } else {
            monitor.registerNewClient(requestMonitorInfo.getClientId());
        }

        return monitor;
    }

    public void deleteMonitor(RequestMonitorInfo requestMonitorInfo) {
        PatientMonitor existingMonitor = this.getPatientMonitor(requestMonitorInfo.getPatientId(), requestMonitorInfo.getMeasurementType());
        if (existingMonitor != null) {
            existingMonitor.removeClient(requestMonitorInfo.getClientId());
            if (existingMonitor.hasNoRegisteredClients()) {
                existingMonitor.cleanUp();
                this.patientMonitors.remove(existingMonitor);
            }
        }
    }
}
