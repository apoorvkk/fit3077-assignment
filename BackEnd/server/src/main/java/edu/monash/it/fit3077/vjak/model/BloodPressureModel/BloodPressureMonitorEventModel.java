package edu.monash.it.fit3077.vjak.model.BloodPressureModel;

import edu.monash.it.fit3077.vjak.model.MonitorEventModel;

/**
 * This class models the blood pressure data that will be sent to connected clients via web sockets.
 */
public class BloodPressureMonitorEventModel extends MonitorEventModel {

    private String unit;
    private String value;

    /**
     * Initializes the blood pressure data to be sent via web sockets.
     * @param pm: the patient whose blood pressure data is to be sent.
     */
    BloodPressureMonitorEventModel(BloodPressurePatientMonitorModel pm) {
        super(pm);
        this.unit = pm.getMeasurementUnit();
        this.value = pm.getMeasurementValue();
    }

    /**
     * Gets the measurement unit of the blood pressure data.
     * @return the unit of the blood pressure data.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the measurement unit of the blood pressure data.
     * @param unit: the new measurement unit of the blood pressure data.
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Gets the measurement value of the blood pressure data.
     * @return the value of the blood pressure data.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the measurement value of the blood pressure data.
     * @param value: the new measurement value of the blood pressure data.
     */
    public void setValue(String value) {
        this.value = value;
    }

}
