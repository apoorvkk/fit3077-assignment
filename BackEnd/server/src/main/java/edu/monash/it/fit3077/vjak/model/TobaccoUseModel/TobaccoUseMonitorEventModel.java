package edu.monash.it.fit3077.vjak.model.TobaccoUseModel;

import edu.monash.it.fit3077.vjak.model.MonitorEventModel;

/**
 * This class models the tobacco use data that will be sent to connected clients via web sockets.
 */
public class TobaccoUseMonitorEventModel extends MonitorEventModel {

    private String status;

    /**
     * Initializes the tobacco use data to be sent via web sockets.
     * @param pm: the patient whose tobacco use data is to be sent.
     */
    TobaccoUseMonitorEventModel(TobaccoUsePatientMonitorModel pm) {
        super(pm);
        this.status = pm.getMeasurementStatus();
    }

    /**
     * Gets the measurement status of the tobacco use data.
     * @return the status of the tobacco use data.
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets the measurement status of the tobacco use data.
     * @param status: the new measurement status of the tobacco use data.
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
