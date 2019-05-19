package edu.monash.it.fit3077.vjak.model.health.TobaccoUse;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.backendmonitor.MeasurementEventModel;
import edu.monash.it.fit3077.vjak.backendmonitor.QualityMeasurementEventModel;
import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;

public class TobaccoUseModel extends AbstractHealthMeasurementModel implements TobaccoUseModelInterface{
    private String status;

    /**
     * Initiailize the model.
     * This will also tell backend to start tracking.
     * @param patientId: the patient id.
     */
    public TobaccoUseModel(String patientId) {
        this.track(patientId);
    }

    /**
     * Returns a description of the model. Mainly used to display the model in presentational format.
     * @return the description of the model.
     */
    @Override
    public String getDescription() {
        return "Tobacco Use";
    }

    /**
     * This is used to take the measurement event that came from web sockets and store event payload data into
     * the tobacco use class appropriately.
     * @param me: The raw payload wrapped in a business adaptor model.
     */
    @Override
    public void setHealthMeasurementValue(MeasurementEventModel me) {
        QualityMeasurementEventModel qme = (QualityMeasurementEventModel) me;

        String status = qme.getStatus();

        if (!status.equals(this.status)) {
            this.status = status;
            this.notifyObservers(); // Measurement update so make sure to notify all observers (should generally be the views).
        }
    }

    /**
     * Return the official measurement type.
     * @return the measurement type.
     */
    @Override
    public String getMeasurementType() {
        return Constant.tobaccoUse;
    }

    /**
     * Return the textual status recorded for tobacco use.
     * @return the status.
     */
    @Override
    public String getStatus() {
        return this.status;
    }
}
