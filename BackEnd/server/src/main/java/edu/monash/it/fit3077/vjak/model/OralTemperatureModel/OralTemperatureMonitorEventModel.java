package edu.monash.it.fit3077.vjak.model.OralTemperatureModel;

import edu.monash.it.fit3077.vjak.model.MonitorEventModel;

/**
 * This class models the oral temperature data that will be sent to connected clients via web sockets.
 */
public class OralTemperatureMonitorEventModel extends MonitorEventModel {

    private String unit;
    private String value;

    /**
     * Initializes the oral temperature data to be sent via web sockets.
     * @param pm: the patient whose oral temperature data is to be sent.
     */
    OralTemperatureMonitorEventModel(OralTemperaturePatientMonitorModel pm) {
        super(pm);
        this.unit = pm.getMeasurementUnit();
        this.value = pm.getMeasurementValue();
    }

    /**
     * Gets the measurement unit of the oral temperature data.
     * @return the unit of the oral temperature data.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the measurement unit of the oral temperature data.
     * @param unit: the new measurement unit of the oral temperature data.
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Gets the measurement value of the oral temperature data.
     * @return the value of the oral temperature data.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the measurement value of the oral temperature data.
     * @param value: the new measurement value of the oral temperature data.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
