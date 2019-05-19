package edu.monash.it.fit3077.akql.api;

/**
 * This interface is used to form an API contract to load observations. This helps in decoupling raw implementation and
 * caller code which allows us to easily change to a different implementation without having to change the application
 * logic calling code a lot.
 */
public interface ObservationLoaderInterface {

    /**
     * Gets the latest health measurement of a specified type of health measurement of a specified patient.
     * @param patientId: the id of the patient whose measurement we want to retrieve.
     * @param measurementType: the type of the health measurement of the HAPI server.
     * @return HAPI version of the health measurement model.
     */
    <ObservationModelInterface> ObservationModelInterface getLatestObservation(String patientId, String measurementType);
}
