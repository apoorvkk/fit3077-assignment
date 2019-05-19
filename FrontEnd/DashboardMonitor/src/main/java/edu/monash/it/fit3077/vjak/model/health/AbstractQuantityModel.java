package edu.monash.it.fit3077.vjak.model.health;

import edu.monash.it.fit3077.vjak.backendmonitor.MeasurementEventModel;
import edu.monash.it.fit3077.vjak.backendmonitor.QuantityMeasurmentEventModel;

public abstract class AbstractQuantityModel extends AbstractHealthMeasurementModel implements QuantityModelInterface {
    private String value;
    private String unit;

    @Override
    public abstract String getDescription();

    @Override
    public void setHealthMeasurementValue(MeasurementEventModel me) {
        QuantityMeasurmentEventModel qme = (QuantityMeasurmentEventModel) me;

        String value = qme.getValue();
        String unit = qme.getUnit();

        if (!value.equals(this.value) || !unit.equals(this.unit)) {
            this.value = value;
            this.unit = unit;
            this.notifyObservers(); // Measurement update so make sure to notify all observers (should generally be the views).
        }
    }

    @Override
    public abstract String getMeasurementType();

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
