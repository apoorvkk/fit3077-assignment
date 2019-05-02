package edu.monash.it.fit3077.vjak.model;

/*
This class focuses on creating specific monitors (eg. Cholesterol). This class can be extended to
many other monitors in future.
 */
class PatientMonitorCreator {
    public static PatientMonitorModel createMonitor(RequestMonitorInfoModel requestMonitorInfoModel) {
        if (requestMonitorInfoModel.getMeasurementType().equals("Cholesterol")) {
            return new CholesterolPatientMonitorModel(requestMonitorInfoModel.getPatientId(), requestMonitorInfoModel.getClientId());
        }
        return null;
    }
}
