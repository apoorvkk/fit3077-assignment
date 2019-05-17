package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.backendmonitor.HealthMeasurementListener;
import edu.monash.it.fit3077.vjak.backendmonitor.MeasurementEventModel;
import edu.monash.it.fit3077.vjak.model.health.HealthMeasurementModel;

import java.util.ArrayList;

/*
This class is responsible for managing all tracked measurements on a patient. For a given patient, you can monitor
various measurements such as cholesterol. You can start and stop tracking these health measurements too.
 */
public class PatientMonitorModel implements PatientMonitorModelInterface {
    private final PatientModelInterface patient;
    private final ArrayList<HealthMeasurementModel> healthMeasurements;
    private final HealthMeasurementCreator healtMeasurementCreator;
    private HealthMeasurementListener healthMeasurementListener;
    private Boolean isBeingMonitored;

    PatientMonitorModel(PatientModelInterface p, HealthMeasurementListener hl) {
        this.isBeingMonitored = false;
        this.observeHealthMeasurements(hl);

        this.patient = p;
        this.healthMeasurements = new ArrayList<>();
        this.healtMeasurementCreator = new HealthMeasurementCreator();
    }

    private void observeHealthMeasurements(HealthMeasurementListener hl) {
        this.healthMeasurementListener = hl;
    }

    private void cleanUp() {
        // Make sure to detach from the socket listener and remove all measurements.
        this.healthMeasurementListener.detach(this);
        this.healthMeasurements.forEach(HealthMeasurementModel::cleanUp);
        this.healthMeasurements.clear();
        this.isBeingMonitored = false;
    }

    public void trackCholesterol() {
        HealthMeasurementModel hm = this.healtMeasurementCreator.trackCholesterol(this.patient.getId());
        this.healthMeasurements.add(hm);
    }

    public void trackSystolicBloodPressure() {
        HealthMeasurementModel hm = this.healtMeasurementCreator.trackSystolicBloodPressure(this.patient.getId());
        this.healthMeasurements.add(hm);
    }

    public void trackDiastolicBloodPressure() {
        HealthMeasurementModel hm = this.healtMeasurementCreator.trackDiastolicBloodPressure(this.patient.getId());
        this.healthMeasurements.add(hm);
    }

    public void trackTobaccoUse() {
        HealthMeasurementModel hm = this.healtMeasurementCreator.trackTobaccoUse(this.patient.getId());
        this.healthMeasurements.add(hm);
    }

    public void startMonitoring() {
        this.healthMeasurementListener.attach(this);
        this.isBeingMonitored = true;
    }

    public void stopMonitoring() {
        this.cleanUp();
    }

    public boolean isBeingMonitored() {
        return this.isBeingMonitored;
    }

    public PatientModelInterface getPatient() {
        return this.patient;
    }

    public ArrayList<HealthMeasurementModel> getHealthMeasurements() {
        return this.healthMeasurements;
    }

    public void update() {
        // Listen into socket event and update the relevant measurement models.
        MeasurementEventModel me = this.healthMeasurementListener.getDataReceived();

        if (me.getPatient().equals(this.patient.getId())) {
            this.healthMeasurements
                .forEach(healthMeasurement -> {
                    String measurementType = me.getType();
                    if (measurementType.equals(healthMeasurement.toString())) {
                        healthMeasurement.setHealthMeasurementValue(me);
                    }
                });
        }
    }
}
