package edu.monash.it.fit3077.vjak.model.health.BloodPressure;

import edu.monash.it.fit3077.vjak.model.health.HealthMeasurementModelInterface;

import java.util.ArrayList;

public interface BloodPressureInterface extends HealthMeasurementModelInterface {
    ArrayList<String> getUnits();
    ArrayList<String> getValues();
}
