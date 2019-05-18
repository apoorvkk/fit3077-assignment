package edu.monash.it.fit3077.vjak.model;

/**
 * This class is models the request data received to register/deregister a client from watching a patient on a particular
 * health measurement (eg. cholesterol).
 */
public class RequestMonitorInfoModel {
    private String patientId;
    private String measurementType;
    private String clientId;

    /**
     * Gets the id of the patient being requested.
     * @return the id of the patient being requested.
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Sets the id of the patient being requested.
     * @param patientId: the id of the new patient to be requested.
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * Gets the type of health measurement being requested.
     * @return the type of health measurement being requested.
     */
    public String getMeasurementType() {
        return measurementType;
    }

    /**
     * Sets the type of health measurement being requested.
     * @param measurementType: the type of the new health measurement to be requested.
     */
    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    /**
     * Gets the id of the client requesting the monitoring.
     * @return the id of the client requesting the monitoring.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the id of the client requesting the monitoring.
     * @param clientId: the id of the new client requesting the monitoring.
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
