package edu.monash.it.fit3077.vjak.model.health;

import java.util.ArrayList;

public interface BloodPressureInterface extends HealthMeasurementModelInterface {
    ArrayList<String> getUnits();
    ArrayList<String> getValues();
}
