package edu.monash.it.fit3077.vjak.view.health;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;
import edu.monash.it.fit3077.vjak.model.health.QuantityModelInterface;

/*
This class is responsible to show the cholesterol status of a patient.
 */
public class CholesterolView extends MonitorTextView {
    /**
     * Initialization.
     * @param hm the health measurement model that holds the status value.
     */
    CholesterolView(AbstractHealthMeasurementModel hm) {
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
                return Constant.cholesterol + ": " + cm.getValue() + " " + cm.getUnit();
            }
            return Constant.cholesterol + ": " + "N/A";
        }
    }
}
