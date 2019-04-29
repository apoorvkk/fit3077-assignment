package edu.monash.it.fit3077.vjak.model;

public class CholesterolPatientMonitor extends PatientMonitor {
    public CholesterolPatientMonitor(String patientId, String firstClientId, ObservationLoaderInterface observationLoader){
        super(patientId, firstClientId, observationLoader);
    }

    public String getMeasurementType() {
        return "Cholesterol";
    }

    protected String getMeasurementCode() {
        return "2093-3";
    }
}
