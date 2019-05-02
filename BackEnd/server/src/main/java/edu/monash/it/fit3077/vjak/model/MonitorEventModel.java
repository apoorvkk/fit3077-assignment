package edu.monash.it.fit3077.vjak.model;

/*
This class models the data that will be sent to connected clients via web sockets.
 */
public class MonitorEventModel {
    private String patient;
    private String type;
    private String unit;
    private String value;

    public MonitorEventModel(PatientMonitor pm) {
        this.patient = pm.getPatientId();
        this.type = pm.getMeasurementType();
        this.unit = pm.getMeasurementUnit();
        this.value = pm.getMeasurementValue();
    }
    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
