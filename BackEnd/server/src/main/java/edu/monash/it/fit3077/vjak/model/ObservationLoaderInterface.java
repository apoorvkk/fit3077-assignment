package edu.monash.it.fit3077.vjak.model;

public interface ObservationLoaderInterface {
    ObservationModelInterface getLatestObservation(String patientId, String measurementType);
}
