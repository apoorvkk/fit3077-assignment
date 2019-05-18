package edu.monash.it.fit3077.vjak.model.OralTemperatureModel;

/**
 * The interface to interact with oral temperature data.
 *
 * This interface is used to form an API contract to represent blood pressure observations. This helps in decoupling raw
 * implementation and caller code which allows us to easily change to a different implementation without having to
 * change the application logic calling code a lot.
 */
public interface OralTemperatureObservationModelInterface {

    /**
     * Gets the measurement unit of the oral temperature data.
     * @return the unit of the oral temperature data.
     */
    String getUnit();

    /**
     * Gets the measurement value of the oral temperature data.
     * @return the value of the oral temperature data.
     */
    String getValue();
}
