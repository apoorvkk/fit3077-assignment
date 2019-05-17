package edu.monash.it.fit3077.vjak.model.health;

import edu.monash.it.fit3077.vjak.backendmonitor.MeasurementEventModel;
import edu.monash.it.fit3077.vjak.backendmonitor.QuantityMeasurmentEventModel;

import java.util.ArrayList;

public class BloodPressureModel extends HealthMeasurementModel {
    private String type;
    private ArrayList<String> units;
    private ArrayList<String> values;

    public BloodPressureModel(String patientId, String type) {
        super(patientId);
        this.type = type;
        this.units = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    @Override
    public String getHealthMeasurementResult() {
        return null;
    }

    @Override
    public void setHealthMeasurementValue(MeasurementEventModel me) {
        QuantityMeasurmentEventModel qme = (QuantityMeasurmentEventModel) me;

        this.units.add(qme.getUnit());
        this.values.add(qme.getValue());
        this.notifyObservers();
    }

    @Override
    public String getMeasurementType() {
        return this.type;
    }
}
