package edu.monash.it.fit3077.vjak.view.health;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;

public class CholesterolView extends MonitorTextView {
    CholesterolView(AbstractHealthMeasurementModel hm) {
        super(hm);
    }

    @Override
    public String getMeasurementName() {
        return Constant.cholesterol;
    }
}
