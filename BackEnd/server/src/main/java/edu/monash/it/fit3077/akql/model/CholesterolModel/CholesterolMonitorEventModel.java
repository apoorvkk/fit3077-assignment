package edu.monash.it.fit3077.akql.model.CholesterolModel;

import edu.monash.it.fit3077.akql.model.MonitorEventModel;

/**
 * This class models the cholesterol data that will be sent to connected clients via web sockets.
 */
public class CholesterolMonitorEventModel extends MonitorEventModel {

    private String unit;
    private String value;

    /**
     * Initializes the cholesterol data to be sent via web sockets.
     * @param pm: the patient whose cholesterol data is to be sent.
     */
    CholesterolMonitorEventModel(CholesterolPatientMonitorModel pm) {
        super(pm);
        this.unit = pm.getMeasurementUnit();
        this.value = pm.getMeasurementValue();
    }

    /**
     * Gets the measurement unit of the cholesterol data.
     * @return the unit of the cholesterol data.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the measurement unit of the cholesterol data.
     * @param unit: the new measurement unit of the cholesterol data.
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Gets the measurement value of the cholesterol data.
     * @return the value of the cholesterol data.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the measurement value of the cholesterol data.
     * @param value: the new measurement value of the cholesterol data.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
