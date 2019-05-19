package edu.monash.it.fit3077.akql.model.health.BloodPressure;

import edu.monash.it.fit3077.akql.Constant;
import edu.monash.it.fit3077.akql.model.health.AbstractQuantityModel;

import java.util.ArrayList;
import java.util.HashMap;

/*
This class is responsible to manage and store the blood pressure values of a particular patient. It will be of type
systolic or diastolic. It is also responsible to determine if the patient is in hypertensive crisis mode.
 */
public class BloodPressureModel extends AbstractQuantityModel implements BloodPressureInterface {
    private String bloodType;
    private String unit;
    private ArrayList<String> values;
    private final HashMap<String, Integer> crisisThresholds = new HashMap<>();

    /**
     * Setup the necessary variables to track blood pressure values and define the thresholds for a patient to be in hypertensive crisis mode.
     * This will also tell backend to start tracking.
     * @param patientId: the patient id.
     * @param bloodType: the blood type (eg. systolic or diastolic).
     */
    public BloodPressureModel(String patientId, String bloodType) {
        this.bloodType = bloodType;
        this.values = new ArrayList<>();
        this.track(patientId);
        this.crisisThresholds.put(Constant.systolic, 180);
        this.crisisThresholds.put(Constant.diastolic, 120);
    }

    /**
     * Returns a description of the model. Mainly used to display the model in presentational format.
     * @return the description of the model.
     */
    @Override
    public String getDescription() {
        return this.bloodType + " Blood Pressure";
    }

    @Override
    protected void setValue(String value) {
        this.values.add(value);
    }

    /**
     * Return the official measurement type.
     * @return the measurement type.
     */
    @Override
    public String getMeasurementType() {
        return this.bloodType + "BloodPressure";
    }

    /**
     * Return the latest value tracked for blood pressure.
     * @return the latest value tracked for blood pressure.
     */
    @Override
    public Double getValue() {
        if (this.values.size() == 0) return null;

        return Double.parseDouble(this.values.get(this.values.size() - 1));
    }

    /**
     * This method is responsible to determine if the patient is in hypertensive crisis model by simply checking
     * if their latest blood pressure value is above the defined threshold.
     * @return true is they are in crisis mode or false if they are not.
     */
    @Override
    public boolean isInHypertensiveCrisis() {
        return this.getValue() > this.crisisThresholds.get(this.bloodType);
    }
}
