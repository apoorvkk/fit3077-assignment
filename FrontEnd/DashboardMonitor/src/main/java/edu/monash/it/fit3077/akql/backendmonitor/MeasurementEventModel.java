package edu.monash.it.fit3077.akql.backendmonitor;

import java.util.LinkedHashMap;

/*
This class models the data that will be received from the server via web sockets.
This classes uses the adaptor pattern where application code (callers) will not directly access raw event payload data
but use this class as an interface to interact with the event.
 */
public class MeasurementEventModel {
    final LinkedHashMap<String, String> payload;

    /**
     * Stores the payload data.
     * @param le Raw payload wrapped in hash map.
     */
    MeasurementEventModel(LinkedHashMap<String, String> le) {
        this.payload = le;
    }

    /**
     * Fetches the patient id.
     * @return the patient id
     */
    public String getPatient() {
        return this.payload.get("patient");
    }

    /**
     * Fetches the measurement type.
     * @return the measurement type.
     */
    public String getType() {
        return this.payload.get("type");
    }
}