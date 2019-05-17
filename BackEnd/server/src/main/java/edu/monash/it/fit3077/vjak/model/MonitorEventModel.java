package edu.monash.it.fit3077.vjak.model;

/*
This class models the data that will be sent to connected clients via web sockets.
 */
public class MonitorEventModel {
    private String patient;
    private String type;

    public MonitorEventModel(PatientMonitorModel pm) {
        this.patient = pm.getPatientId();
        this.type = pm.getMeasurementType();
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
}
