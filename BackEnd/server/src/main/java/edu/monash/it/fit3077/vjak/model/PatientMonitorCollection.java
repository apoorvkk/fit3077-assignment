package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationLoader;

import java.util.ArrayList;

public class PatientMonitorCollection {
    private final ArrayList<PatientMonitor> patientMonitors;
    private final ObservationLoaderInterface observationLoader;

    public PatientMonitorCollection(ArrayList<PatientMonitor> patientMonitors) {
        this.patientMonitors = patientMonitors;
        this.observationLoader = new HapiObservationLoader();
    }

    private PatientMonitor getPatientMonitor(String patientId, String measurementType) {
        for (PatientMonitor pm: this.patientMonitors) {
            if (pm.matches(patientId, measurementType)){
                return pm;
            }
        }
        return null;
    }

    public void addMonitor(String patientId, String clientId, String measurementType){
        PatientMonitor existingMonitor = this.getPatientMonitor(patientId, measurementType);

        if(existingMonitor == null) {
            existingMonitor = PatientMonitorCreator.createMonitor(measurementType, patientId, clientId, observationLoader);
        }

        // notify client of the value + unit (observer pattern).
    }

    public void deleteMonitor(String patientId, String clientId, String measurementType) {
        PatientMonitor existingMonitor = this.getPatientMonitor(patientId, measurementType);
        if (existingMonitor != null) {
            existingMonitor.removeClient(clientId);
            if (existingMonitor.hasNoRegisteredClients()) {
                this.patientMonitors.remove(existingMonitor);
            }
        }
    }
}
