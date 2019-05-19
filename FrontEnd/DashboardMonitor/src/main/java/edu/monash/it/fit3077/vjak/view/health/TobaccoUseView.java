package edu.monash.it.fit3077.vjak.view.health;

import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;
import edu.monash.it.fit3077.vjak.model.health.TobaccoUse.TobaccoUseModelInterface;

public class TobaccoUseView extends MonitorTextView {
    TobaccoUseView(AbstractHealthMeasurementModel hm) {
        super(hm);
    }

    @Override
    protected String renderResult() {
        TobaccoUseModelInterface cm = (TobaccoUseModelInterface) this.model;
        if (cm.getStatus() != null) {
            return "Tobacco Use: " + cm.getStatus();
        }
        return "Tobacco Use: " + "N/A";
    }

    @Override
    public String getMeasurementName() {
        return "Tobacco Use";
    }
}


