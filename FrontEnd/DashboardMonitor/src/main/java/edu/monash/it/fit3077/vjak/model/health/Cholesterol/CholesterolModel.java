package edu.monash.it.fit3077.vjak.model.health.Cholesterol;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.backendmonitor.MeasurementEventModel;
import edu.monash.it.fit3077.vjak.backendmonitor.QuantityMeasurmentEventModel;
import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;
import edu.monash.it.fit3077.vjak.model.health.QuantityModelInterface;

/*
This class models the cholesterol model.
 */
public class CholesterolModel extends AbstractHealthMeasurementModel implements QuantityModelInterface {
    private String value;
    private String unit;
    public CholesterolModel(String patientId) {
        this.track(patientId);
    }

    @Override
    public String getDescription() {
        return "Cholesterol";
    }

    @Override
    public void setHealthMeasurementValue(MeasurementEventModel me) {
        QuantityMeasurmentEventModel qme = (QuantityMeasurmentEventModel) me;

        String value = qme.getValue();
        String unit = qme.getUnit();
        System.out.println(unit + " " + value);
        if (!value.equals(this.value) || !unit.equals(this.unit)) {
            this.value = value;
            this.unit = unit;
            this.notifyObservers(); // Measurement update so make sure to notify all observers (should generally be the views).
        }
    }

    @Override
    public String getMeasurementType() {
        return Constant.cholesterol;
    }

    @Override
    public Double getValue() {
        if (this.value == null) return null;
        return Double.parseDouble(this.value);
    }

    @Override
    public String getUnit() {
        return this.unit;
    }
}
