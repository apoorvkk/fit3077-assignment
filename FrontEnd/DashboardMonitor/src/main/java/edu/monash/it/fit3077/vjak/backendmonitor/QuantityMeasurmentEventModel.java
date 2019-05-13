package edu.monash.it.fit3077.vjak.backendmonitor;

import java.util.LinkedHashMap;

public class QuantityMeasurmentEventModel extends MeasurementEventModel {
    QuantityMeasurmentEventModel(LinkedHashMap<String, String> le) {
        super(le);
    }

    public String getUnit() {
        return this.payload.get("unit");
    }

    public String getValue() {
        return this.payload.get("value");
    }
}
