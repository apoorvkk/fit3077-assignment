package edu.monash.it.fit3077.vjak.model;

/*
This class is models the request data received to register/deregister a client from watching a patient on a particular
health measurement (eg. cholesterol).
 */
public class RequestMonitorInfo {
    private String patientId;
    private String measurementType;
    private String clientId;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
