package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.model.health.HealthMeasurement;

import java.util.ArrayList;

public class MonitorPatient {
    private Patient patient;
    private ArrayList<HealthMeasurement> healthMeasurements;

    public MonitorPatient(Patient p) {
        this.patient = p;
        this.healthMeasurements = new ArrayList<HealthMeasurement>();
    }
}
