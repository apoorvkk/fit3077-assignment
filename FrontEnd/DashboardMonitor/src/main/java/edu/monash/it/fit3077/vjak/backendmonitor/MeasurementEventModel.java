package edu.monash.it.fit3077.vjak.backendmonitor;

import java.util.LinkedHashMap;

/*
This class models the data that will be received from the server via web sockets.
 */
public class MeasurementEventModel {
    protected final LinkedHashMap<String, String> payload;

    MeasurementEventModel(LinkedHashMap<String, String> le) {
        this.payload = le;
    }

    public String getPatient() {
        return this.payload.get("patient");
    }

    public String getType() {
        return this.payload.get("type");
    }
}