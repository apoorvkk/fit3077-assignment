package edu.monash.it.fit3077.vjak.model.BloodPressureModel;

/**
 * The interface to interact with blood pressure data.
 *
 * This interface is used to form an API contract to represent blood pressure observations. This helps in decoupling raw
 * implementation and caller code which allows us to easily change to a different implementation without having to
 * change the application logic calling code a lot.
 */
public interface BloodPressureObservationModelInterface {

    /**
     * Gets the measurement unit of the blood pressure data.
     * @return the unit of the blood pressure data.
     */
    String getUnit();

    /**
     * Gets the measurement value of the blood pressure data.
     * @return the value of the blood pressure data.
     */
    String getValue();
}
