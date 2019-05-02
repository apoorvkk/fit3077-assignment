package edu.monash.it.fit3077.vjak.model;

/*
This class focuses on creating specific monitors (eg. Cholesterol). This class can be extended to
many other monitors in future.
 */
class PatientMonitorCreator {
    public static PatientMonitor createMonitor(RequestMonitorInfo requestMonitorInfo) {
        if (requestMonitorInfo.getMeasurementType().equals("Cholesterol")) {
            return new CholesterolPatientMonitor(requestMonitorInfo.getPatientId(), requestMonitorInfo.getClientId());
        }
        return null;
    }
}
