package edu.monash.it.fit3077.akql.view.health;

import edu.monash.it.fit3077.akql.model.health.AbstractHealthMeasurementModel;
import edu.monash.it.fit3077.akql.model.health.TobaccoUse.TobaccoUseModelInterface;

/*
This class is responsible to show the tobacco use status of a patient.
 */
public class TobaccoUseView extends MonitorTextView {
    /**
     * Initialization.
     * @param hm the health measurement model that holds the status value.
     */
    TobaccoUseView(AbstractHealthMeasurementModel hm) {
        super(hm);
    }

    /**
     * Used to get the result from the model.
     * @return the result string.
     */
    @Override
    protected String renderResult() {
        TobaccoUseModelInterface cm = (TobaccoUseModelInterface) this.model;
        if (cm.getStatus() != null) {
            return "Tobacco Use: " + cm.getStatus();
        }
        return "Tobacco Use: " + "N/A";
    }
}


