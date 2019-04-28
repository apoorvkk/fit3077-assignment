package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.backendmonitor.HealthMeasurementListener;
import edu.monash.it.fit3077.vjak.backendmonitor.MeasurementEvent;
import edu.monash.it.fit3077.vjak.model.health.HealthMeasurement;
import edu.monash.it.fit3077.vjak.observer.Observer;

import java.util.ArrayList;

public class MonitorPatient extends Observer {
    private Patient patient;
    private ArrayList<HealthMeasurement> healthMeasurements;
    private HealthMeasurementCreator healtMeasurementCreator;
    private HealthMeasurementListener healthMeasurementListener;

    public MonitorPatient(Patient p, HealthMeasurementListener hl) {
        this.observeHealthMeasurements(hl);

        this.patient = p;
        this.healthMeasurements = new ArrayList<>();
        this.healtMeasurementCreator = new HealthMeasurementCreator();
    }

    private void observeHealthMeasurements(HealthMeasurementListener hl) {
        this.healthMeasurementListener = hl;
        this.healthMeasurementListener.attach(this);
    }

    public void cleanUp() {
        this.healthMeasurementListener.detach(this);
        this.healthMeasurements.forEach(healthMeasurement -> healthMeasurement.cleanUp());
    }

    public void update() {
        MeasurementEvent me = this.healthMeasurementListener.getDataReceived();

        if (me.getPatient().equals(this.patient.getId())) {
            this.healthMeasurements
                    .stream()
                    .forEach(healthMeasurement -> {
                        String measurementType = me.getType();
                        if (measurementType.equals(healthMeasurement.toString())) {
                            String value = me.getValue();
                            String unit = me.getUnit();
                            healthMeasurement.setHealthMeasurementValue(value, unit);
                        }
                    });
        }
    }

    public void trackCholesterol() {
        HealthMeasurement hm = this.healtMeasurementCreator.trackCholesterol(this.patient.getId());
        this.healthMeasurements.add(hm);
    }

}
