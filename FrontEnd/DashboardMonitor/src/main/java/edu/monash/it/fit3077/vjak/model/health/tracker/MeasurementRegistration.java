package edu.monash.it.fit3077.vjak.model.health.tracker;

import edu.monash.it.fit3077.vjak.Constant;

public class MeasurementRegistration {
    private String patientId;
    private String clientId;
    private String measurementType;
    private String registrationId;

    public MeasurementRegistration(String patientId, String measurementType) {
        this.patientId = patientId;
        this.clientId = Constant.clientId;
        this.measurementType = measurementType;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
}
