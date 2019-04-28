package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.backendmonitor.HealthMeasurementListener;
import edu.monash.it.fit3077.vjak.backendmonitor.MeasurementEventModel;
import edu.monash.it.fit3077.vjak.model.health.HealthMeasurementModel;

import java.util.ArrayList;

public class PatientMonitorModel implements PatientMonitorModelInterface {
    private PatientModelInterface patient;
    private ArrayList<HealthMeasurementModel> healthMeasurements;
    private HealthMeasurementCreator healtMeasurementCreator;
    private HealthMeasurementListener healthMeasurementListener;

    public PatientMonitorModel(PatientModelInterface p, HealthMeasurementListener hl) {
        this.observeHealthMeasurements(hl);

        this.patient = p;
        this.healthMeasurements = new ArrayList<>();
        this.healtMeasurementCreator = new HealthMeasurementCreator();
    }

    private void observeHealthMeasurements(HealthMeasurementListener hl) {
        this.healthMeasurementListener = hl;
    }

    private void trackCholesterol() {
        HealthMeasurementModel hm = this.healtMeasurementCreator.trackCholesterol(this.patient.getId());
        this.healthMeasurements.add(hm);
    }

    private void cleanUp() {
        this.healthMeasurementListener.detach(this);
        this.healthMeasurements.forEach(HealthMeasurementModel::cleanUp);
        this.healthMeasurements.clear();
    }

    public void trackMeasurements() {
        this.healthMeasurementListener.attach(this);

        this.trackCholesterol();
    }

    public void removeMeasurements() {
        this.cleanUp();
    }

    public boolean isBeingMonitored() {
        return this.healthMeasurements.size() > 0;
    }

    public PatientModelInterface getPatient() {
        return this.patient;
    }

    public ArrayList<HealthMeasurementModel> getHealthMeasurements() {
        return this.healthMeasurements;
    }

    public void update() {
        MeasurementEventModel me = this.healthMeasurementListener.getDataReceived();

        if (me.getPatient().equals(this.patient.getId())) {
            this.healthMeasurements
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
}
