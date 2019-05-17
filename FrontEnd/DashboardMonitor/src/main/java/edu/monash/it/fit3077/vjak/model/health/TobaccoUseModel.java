package edu.monash.it.fit3077.vjak.model.health;

import edu.monash.it.fit3077.vjak.backendmonitor.MeasurementEventModel;
import edu.monash.it.fit3077.vjak.backendmonitor.QualityMeasurementEventModel;

public class TobaccoUseModel extends HealthMeasurementModel {
    private String status;

    public TobaccoUseModel(String patientId) { super(patientId); }

    @Override
    public String getHealthMeasurementResult() {
        return this.status;
    }

    @Override
    public void setHealthMeasurementValue(MeasurementEventModel me) {
        QualityMeasurementEventModel qme = (QualityMeasurementEventModel) me;

        String status = qme.getStatus();

        if (!status.equals(this.status)) {
            this.status = status;
            this.notifyObservers(); // Measurement update so make sure to notify all observers (should generally be the views).
        }
    }

    @Override
    public String getMeasurementType() {
        return "TobaccoUse";
    }
}
