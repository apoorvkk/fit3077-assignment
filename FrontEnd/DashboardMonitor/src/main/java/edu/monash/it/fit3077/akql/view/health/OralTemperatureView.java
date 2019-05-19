package edu.monash.it.fit3077.akql.view.health;

import edu.monash.it.fit3077.akql.model.health.AbstractHealthMeasurementModel;
import edu.monash.it.fit3077.akql.model.health.QuantityModelInterface;

/*
This class is responsible to show the oral temperature status of a patient.
 */
public class OralTemperatureView extends MonitorTextView {
    /**
     * Initialization.
     * @param hm the health measurement model that holds the status value.
     */
    OralTemperatureView(AbstractHealthMeasurementModel hm) {
        super(hm);
    }

    /**
     * Used to get the result from the model.
     * @return the result string.
     */
    @Override
    protected String renderResult() {
        {
            QuantityModelInterface cm = (QuantityModelInterface) this.model;
            if (cm.getValue() != null) {
                return "Oral Temperature" + ": " + cm.getValue() + " " + cm.getUnit();
            }
            return "Oral Temperature" + ": " + "N/A";
        }
    }
}
