package edu.monash.it.fit3077.vjak.backendmonitor;

import java.util.LinkedHashMap;

public class QualityMeasurementEventModel extends MeasurementEventModel {

    QualityMeasurementEventModel(LinkedHashMap<String, String> le) {
        super(le);
    }

    public String getStatus() {
        return this.payload.get("status");
    }
}
