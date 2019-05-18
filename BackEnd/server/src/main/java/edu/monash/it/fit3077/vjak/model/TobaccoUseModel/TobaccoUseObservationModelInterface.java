package edu.monash.it.fit3077.vjak.model.TobaccoUseModel;

/**
 * This interface is used to form an API contract to represent tobacco use observations. This helps in decoupling raw
 * implementation and caller code which allows us to easily change to a different implementation without having to
 * change the application logic calling code a lot.
 */
public interface TobaccoUseObservationModelInterface {

    /**
     * Gets the measurement status of the tobacco use data.
     * @return the status of the tobacco use data.
     */
    String getStatus();
}
