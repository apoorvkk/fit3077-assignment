package edu.monash.it.fit3077.vjak.model.health.BloodPressure;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.backendmonitor.MeasurementEventModel;
import edu.monash.it.fit3077.vjak.backendmonitor.QuantityMeasurmentEventModel;
import edu.monash.it.fit3077.vjak.model.health.AbstractQuantityModel;

import java.util.ArrayList;
import java.util.HashMap;

public class BloodPressureModel extends AbstractQuantityModel implements BloodPressureInterface {
    private String bloodType;
    private String unit;
    private ArrayList<String> values;
    private final HashMap<String, Integer> crisisThresholds = new HashMap<>();

    public BloodPressureModel(String patientId, String bloodType) {
        this.bloodType = bloodType;
        this.values = new ArrayList<>();
        this.track(patientId);
        this.crisisThresholds.put(Constant.systolic, 180);
        this.crisisThresholds.put(Constant.diastolic, 120);
    }

    @Override
    public String getDescription() {
        return this.bloodType + " Blood Pressure";
    }

    @Override
    public void setHealthMeasurementValue(MeasurementEventModel me) {
        QuantityMeasurmentEventModel qme = (QuantityMeasurmentEventModel) me;

        this.unit = qme.getUnit();
        this.values.add(qme.getValue());
        this.notifyObservers();
    }

    @Override
    public String getMeasurementType() {
        return this.bloodType + "BloodPressure";
    }

    @Override
    public Double getValue() {
        if (this.values.size() == 0) return null;

        return Double.parseDouble(this.values.get(this.values.size() - 1));
    }

    @Override
    public boolean isInHypertensiveCrisis() {
        return this.getValue() > this.crisisThresholds.get(this.bloodType);
    }
}
