package edu.monash.it.fit3077.vjak.model;

public class PatientMonitorCreator {
    public static PatientMonitor createMonitor(String measurementType, String patientId, String firstClientId, ObservationLoaderInterface observationLoader) {
        if (measurementType.equals("Cholesterol")) {
            return new CholesterolPatientMonitor(patientId, firstClientId, observationLoader);
        }
        return null;
    }
}
