package edu.monash.it.fit3077.akql.model;

/**
 * This class models the data that will be sent to connected clients via web sockets.
 */
public class MonitorEventModel {
    private String patient;
    private String type;

    /**
     * Initializes the health measurement data to be sent via web sockets.
     * @param pm: the patient whose health measurement data is to be sent.
     */
    public MonitorEventModel(PatientMonitorModel pm) {
        this.patient = pm.getPatientId();
        this.type = pm.getMeasurementType();
    }

    /**
     * Gets the id of the patient being monitored.
     * @return the id of the patient being monitored.
     */
    public String getPatient() {
        return patient;
    }

    /**
     * Sets a new patient to be monitored using a new id.
     * @param patient: the id of the new patient to be monitored.
     */
    public void setPatient(String patient) {
        this.patient = patient;
    }

    /**
     * Gets the type of health measurement being monitored.
     * @return the type of health measurement being monitored.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets a different type of health measurement to be monitored.
     * @param type: the new type of health measurement to be monitored.
     */
    public void setType(String type) {
        this.type = type;
    }
}
