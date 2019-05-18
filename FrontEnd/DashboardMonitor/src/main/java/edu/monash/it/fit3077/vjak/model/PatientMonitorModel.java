package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.backendmonitor.HealthMeasurementListener;
import edu.monash.it.fit3077.vjak.backendmonitor.MeasurementEventModel;
import edu.monash.it.fit3077.vjak.model.health.HealthMeasurementCreator;
import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;
import edu.monash.it.fit3077.vjak.observer.Subject;

import java.util.ArrayList;
import java.util.HashMap;

/*
This class is responsible for managing all tracked measurements on a patient. For a given patient, you can monitor
various measurements such as cholesterol. You can start and stop tracking these health measurements too.
 */
public class PatientMonitorModel extends Subject implements PatientMonitorModelInterface {
    private final PatientModelInterface patient;
    private final ArrayList<AbstractHealthMeasurementModel> healthMeasurements;
    private HealthMeasurementListener healthMeasurementListener;
    private Boolean isBeingMonitored;

    PatientMonitorModel(PatientModelInterface p, HealthMeasurementListener hl) {
        this.isBeingMonitored = false;
        this.healthMeasurementListener = hl;

        this.patient = p;
        this.healthMeasurements = new ArrayList<>();
    }

    private void cleanUp() {
        // Make sure to detach from the socket listener and remove all measurements.
        this.healthMeasurementListener.detach(this);
        this.healthMeasurements.forEach(AbstractHealthMeasurementModel::cleanUp);
        this.healthMeasurements.clear();
        this.isBeingMonitored = false;
    }

    private void registerHealthMeasurement(AbstractHealthMeasurementModel hm) {
        this.healthMeasurements.add(hm);
        this.notifyObservers();
    }

    private AbstractHealthMeasurementModel getByMeasurementType(String measurementType) {
        int i = 0;
        while (i < this.healthMeasurements.size()) {
            AbstractHealthMeasurementModel curr = this.healthMeasurements.get(i);
            if (curr.getMeasurementType().equals(measurementType)) {
                return curr;

            }
            i++;
        }
        return null;
    }

    public void removeHealthMeasurement(String measurementType) {
        AbstractHealthMeasurementModel hm = this.getByMeasurementType(measurementType);
        if (hm != null) {
            hm.cleanUp();
            this.healthMeasurements.remove(hm);
            this.notifyObservers();
        }
    }

    public void trackMeasurement(String measurementType) {
        AbstractHealthMeasurementModel hm = HealthMeasurementCreator.trackHealthMeasurement(this.patient.getId(), measurementType);
        this.registerHealthMeasurement(hm);
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

    public ArrayList<AbstractHealthMeasurementModel> getHealthMeasurements() {
        ArrayList<AbstractHealthMeasurementModel> orderedHealthMeasurements = new ArrayList<>();

        Constant.monitorOrder.forEach(type -> {
            AbstractHealthMeasurementModel hm = this.getByMeasurementType(type);
            if (hm != null) {
                orderedHealthMeasurements.add(hm);
            }
        });

        return orderedHealthMeasurements;
    }

    public void update() {
        // Listen into socket event and update the relevant measurement models.
        MeasurementEventModel me = this.healthMeasurementListener.getDataReceived();
        if (me.getPatient().equals(this.patient.getId())) {
            this.healthMeasurements
                .forEach(healthMeasurement -> {
                    String measurementType = me.getType();
                    if (measurementType.equals(healthMeasurement.getMeasurementType())) {
                        healthMeasurement.setHealthMeasurementValue(me);
                    }
                });
        }
    }
}
