package edu.monash.it.fit3077.vjak.model;

class PatientMonitorCreator {
    public static PatientMonitor createMonitor(RequestMonitorInfo requestMonitorInfo) {
        if (requestMonitorInfo.getMeasurementType().equals("Cholesterol")) {
            return new CholesterolPatientMonitor(requestMonitorInfo.getPatientId(), requestMonitorInfo.getClientId());
        }
        return null;
    }
}
