package edu.monash.it.fit3077.vjak.model.health;

import edu.monash.it.fit3077.vjak.backendmonitor.MeasurementEventModel;
import edu.monash.it.fit3077.vjak.backendmonitor.QuantityMeasurmentEventModel;

/*
This class models the cholesterol model.
 */
public class CholesterolModel extends HealthMeasurementModel {
    private String value;
    private String unit;
    public CholesterolModel(String patientId) {
        super(patientId);
    }

    @Override
    public String getHealthMeasurementResult() {
        if (this.value != null && this.unit != null){
            return this.value + " " + this.unit;
        }
        return "N/A";
    }

    @Override
    public void setHealthMeasurementValue(MeasurementEventModel me) {
        QuantityMeasurmentEventModel qme = (QuantityMeasurmentEventModel) me;

        String value = qme.getValue();
        String unit = qme.getUnit();
        System.out.println("value: " + value);
        System.out.println("unit: " + unit);

        if (!value.equals(this.value) || !unit.equals(this.unit)) {
            this.value = value;
            this.unit = unit;
            this.notifyObservers(); // Measurement update so make sure to notify all observers (should generally be the views).
        }
    }

    @Override
    public String toString() {
        return "Cholesterol";
    }
}
