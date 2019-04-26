package edu.monash.it.fit3077.vjak;

import edu.monash.it.fit3077.vjak.api.hapi.HapiPatientLoader;
import edu.monash.it.fit3077.vjak.model.MonitorablePatientCollection;

public class Application {

    public static void main(String[] args) {
        HapiPatientLoader h = new HapiPatientLoader("9310");
        MonitorablePatientCollection mps = new MonitorablePatientCollection(h);
    }
}