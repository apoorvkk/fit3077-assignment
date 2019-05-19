package edu.monash.it.fit3077.akql.model.health.OralTemperature;

import edu.monash.it.fit3077.akql.Constant;
import edu.monash.it.fit3077.akql.model.health.AbstractQuantityModel;

/*
This class models the oral temperature model.
 */
public class OralTemperatureModel extends AbstractQuantityModel {
    /**
     * Initiailize the model.
     * This will also tell backend to start tracking.
     * @param patientId: the patient id.
     */
    public OralTemperatureModel(String patientId) {
        this.track(patientId);
    }

    /**
     * Returns a description of the model. Mainly used to display the model in presentational format.
     * @return the description of the model.
     */
    @Override
    public String getDescription() {
        return "Oral Temperature";
    }

    /**
     * Return the official measurement type.
     * @return the measurement type.
     */
    @Override
    public String getMeasurementType() {
        return Constant.oralTemperature;
    }
}
