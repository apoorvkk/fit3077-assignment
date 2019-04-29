package edu.monash.it.fit3077.vjak.model;

import org.hl7.fhir.dstu3.model.Patient;

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
