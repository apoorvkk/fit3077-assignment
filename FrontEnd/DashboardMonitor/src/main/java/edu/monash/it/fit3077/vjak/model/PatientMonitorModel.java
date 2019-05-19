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

    /**
     * Initializes the necessary instance variables.
     * @param p: the patient.
     * @param hl: the health measurement listener that will be observed for new events.
     */
    PatientMonitorModel(PatientModelInterface p, HealthMeasurementListener hl) {
        this.isBeingMonitored = false;
        this.healthMeasurementListener = hl;

        this.patient = p;
        this.healthMeasurements = new ArrayList<>();
    }

    /**
     * This method makes sure to detach from the socket listener and remove all measurements.
     */
    private void cleanUp() {
        this.healthMeasurementListener.detach(this);
        this.healthMeasurements.forEach(AbstractHealthMeasurementModel::cleanUp);
        this.healthMeasurements.clear();
        this.isBeingMonitored = false;
    }

    /**
     * This method will register a health measurement and notify all observers (usually views)
     * that a health measurement has been added.
     * @param hm: the health measurement model.
     */
    private void registerHealthMeasurement(AbstractHealthMeasurementModel hm) {
        this.healthMeasurements.add(hm);
        this.notifyObservers();
    }

    /**
     * Find the tracked health measurment model of interest.
     * @param measurementType: measurement of interest (eg. Cholesterol).
     * @return the health measurement model of interest.
     */
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

    /**
     * Stop tracking the health measurement and make sure to destroy it properly. Also notify observers health measurement
     * has been removed.
     * @param measurementType: measurement of interest.
     */
    public void removeHealthMeasurement(String measurementType) {
        AbstractHealthMeasurementModel hm = this.getByMeasurementType(measurementType);
        if (hm != null) {
            hm.cleanUp();
            this.healthMeasurements.remove(hm);
            this.notifyObservers();
        }
    }

    /**
     * Start tracking a health measurement for this patient monitor.
     * @param measurementType: measurement of interest
     */
    public void trackMeasurement(String measurementType) {
        AbstractHealthMeasurementModel hm = HealthMeasurementCreator.trackHealthMeasurement(this.patient.getId(), measurementType);
        this.registerHealthMeasurement(hm);
    }

    /**
     * Select this patient monitor and listen into events. This is where someone is interested in viewing this monitor.
     */
    public void startMonitoring() {
        this.healthMeasurementListener.attach(this);
        this.isBeingMonitored = true;
    }

    /**
    Completely destroy this monitor as it's no longer being use.
     */
    public void stopMonitoring() {
        this.cleanUp();
    }

    /**
     * Check if someone is interested in this patient monitor.
     * @return boolean value.
     */
    public boolean isBeingMonitored() {
        return this.isBeingMonitored;
    }

    /**
     * Return the patient.
     * @return the patient.
     */
    public PatientModelInterface getPatient() {
        return this.patient;
    }

    /**
     * This method returns list of tracking health measurements under a particular order.
     * @return a list of tracking health measurements.
     */
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

    /**
     * This method is called whenever the socket listener updates. It will fetch the latest event and
     * then update the necessary health measurements.
     */
    public void update() {
        MeasurementEventModel me = this.healthMeasurementListener.getDataReceived();
        if (me.getPatient().equals(this.patient.getId())) {
            this.healthMeasurements
                .forEach(healthMeasurement -> {
                    String measurementType = me.getType();
                    if (measurementType.equals(healthMeasurement.getMeasurementType())) { // found a match so update health measurement.
                        healthMeasurement.setHealthMeasurementValue(me);
                    }
                });
        }
    }
}
