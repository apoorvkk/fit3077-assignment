package edu.monash.it.fit3077.vjak.model.health.TobaccoUse;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.backendmonitor.MeasurementEventModel;
import edu.monash.it.fit3077.vjak.backendmonitor.QualityMeasurementEventModel;
import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;

public class TobaccoUseModel extends AbstractHealthMeasurementModel implements TobaccoUseModelInterface{
    private String status;

    public TobaccoUseModel(String patientId) {
        this.track(patientId);
    }

    @Override
    public String getDescription() {
        return "Tobacco Use";
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
        return Constant.tobaccoUse;
    }

    @Override
    public String getStatus() {
        return this.status;
    }
}
