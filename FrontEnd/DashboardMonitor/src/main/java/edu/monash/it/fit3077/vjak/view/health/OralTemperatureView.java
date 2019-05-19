package edu.monash.it.fit3077.vjak.view.health;

import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;

public class OralTemperatureView extends MonitorTextView {
    OralTemperatureView(AbstractHealthMeasurementModel hm) {
        super(hm);
    }

    @Override
    public String getMeasurementName() {
        return "Oral Temperature";
    }
}
