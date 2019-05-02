package edu.monash.it.fit3077.vjak.model;

/*
This class represents the cholesterol patient monitor type. It is a subtype of PatientMonitor and specifies
the specifics of a cholesterol version of a patient monitor.
 */
public class CholesterolPatientMonitor extends PatientMonitor {
    CholesterolPatientMonitor(String patientId, String firstClientId){
        super(patientId, firstClientId);
    }

    public String getMeasurementType() {
        return "Cholesterol";
    }

    protected String getMeasurementCode() {
        return "2093-3";
    } // Used for FHIR and is also an ID of measurement type.
}
