package edu.monash.it.fit3077.akql.model.health;

import edu.monash.it.fit3077.akql.backendmonitor.MeasurementEventModel;
import edu.monash.it.fit3077.akql.backendmonitor.QuantityMeasurementEventModel;

/*
This class is responsible for health measurements that need quantity values.
 */
public abstract class AbstractQuantityModel extends AbstractHealthMeasurementModel implements QuantityModelInterface {
    private String value;
    private String unit;

    /**
     * This is used to take the measurement event that came from web sockets and store event payload data into
     * the class appropriately. Here, we store data into quantitative properties like value.
     * @param me: The raw payload wrapped in a business adaptor model.
     */
    @Override
    public void setHealthMeasurementValue(MeasurementEventModel me) {
        QuantityMeasurementEventModel qme = (QuantityMeasurementEventModel) me; // Cast event to Quantity version so we can access methods related to quantity properties.

        String value = qme.getValue();
        String unit = qme.getUnit();

        if (!value.equals(this.value) || !unit.equals(this.unit)) {
            this.setValue(value);
            this.unit = unit;
            this.notifyObservers(); // Measurement update so make sure to notify all observers (should generally be the views).
        }
    }

    /**
     * Stores the value. Subclasses should override this method if they have different implementations.
     * @param value: value form the event
     */
    protected void setValue(String value) {
        this.value = value;
    }

    /**
     * Return the value of the event.
     * @return the value of the event.
     */
    @Override
    public Double getValue() {
        if (this.value == null) return null;
        return Double.parseDouble(this.value);
    }

    /**
     * Return the unit of the event.
     * @return the unit of the event.
     */
    @Override
    public String getUnit() {
        return this.unit;
    }
}
