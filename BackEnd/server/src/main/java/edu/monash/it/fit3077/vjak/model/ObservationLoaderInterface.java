package edu.monash.it.fit3077.vjak.model;

import java.util.ArrayList;

public interface ObservationLoaderInterface {
    ArrayList<ObservationModelInterface> loadNewObservations();
    ObservationModelInterface getLatestObservation(String patientId, String measurementType);
}
