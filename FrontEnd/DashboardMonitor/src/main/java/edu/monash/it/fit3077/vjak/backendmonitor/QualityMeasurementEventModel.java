package edu.monash.it.fit3077.vjak.backendmonitor;

import java.util.LinkedHashMap;

/*
This class is a type of measurement event model but specific to events that have textual descriptions.
This is useful for monitors like tobacco use which have results in descriptive formats.
 */
public class QualityMeasurementEventModel extends MeasurementEventModel {

    /**
     * Stores the payload data.
     * @param le Raw payload wrapped in hash map.
     */
    QualityMeasurementEventModel(LinkedHashMap<String, String> le) {
        super(le);
    }

    /**
     * Fetches the measurement status.
     * @return the measurement status.
     */
    public String getStatus() {
        return this.payload.get("status");
    }
}
