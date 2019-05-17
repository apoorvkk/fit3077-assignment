package edu.monash.it.fit3077.vjak.model;

/*
This interface is used to form an API contract to load observations. This helps in decoupling raw implementation and
caller code which allows us to easily change to a different implementation without having to change the application
logic calling code a lot.
 */
public interface ObservationLoaderInterface {
    <ObservationModelInterface> ObservationModelInterface getLatestObservation(String patientId, String measurementType);
}
