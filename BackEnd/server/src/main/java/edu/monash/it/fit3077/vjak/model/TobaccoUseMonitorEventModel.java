package edu.monash.it.fit3077.vjak.model;

public class TobaccoUseMonitorEventModel extends MonitorEventModel {

    private String status;

    TobaccoUseMonitorEventModel(TobaccoUsePatientMonitorModel pm) {
        super(pm);
        this.status = pm.getMeasurementStatus();
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
