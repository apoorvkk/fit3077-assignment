package edu.monash.it.fit3077.akql.backendmonitor;

import java.util.LinkedHashMap;

/*
This class is a type of measurement event model but specific to events that have quantitative values.
This is useful for monitors like cholesterol or blood pressure use.
 */
public class QuantityMeasurementEventModel extends MeasurementEventModel {
    /**
     * Stores the payload data.
     * @param le Raw payload wrapped in hash map.
     */
    QuantityMeasurementEventModel(LinkedHashMap<String, String> le) {
        super(le);
    }

    /**
     * Fetches the measurement unit.
     * @return the measurement unit.
     */
    public String getUnit() {
        return this.payload.get("unit");
    }

    /**
     * Fetches the measurement value.
     * @return the measurement value.
     */
    public String getValue() {
        return this.payload.get("value");
    }
}
