package edu.monash.it.fit3077.akql.model.CholesterolModel;

/**
 * This interface is used to form an API contract to represent cholesterol observations. This helps in decoupling raw
 * implementation and caller code which allows us to easily change to a different implementation without having to
 * change the application logic calling code a lot.
 */
public interface CholesterolObservationModelInterface {

    /**
     * Gets the measurement unit of the cholesterol data.
     * @return the unit of the cholesterol data.
     */
    String getUnit();

    /**
     * Gets the measurement value of the cholesterol data.
     * @return the value of the cholesterol data.
     */
    String getValue();
}
