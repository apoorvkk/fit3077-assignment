package edu.monash.it.fit3077.vjak.controller;

/*
This interface is responsible for defining a contract that callers (eg. views) can use to manipulate the necessary models.
This provides a nice separation of concerns between the controller and view through the dependency inversion principle.
Here, the view will depend on this controller interface rather than internal controller.
 */
public interface PatientMonitorControllerInterface {
    /**
     * Used to select a measurement to see for a particular patient (eg. systolic blood pressure).
     * @param measurementType: the measurement of interest (eg. cholesterol).
     */
    void trackMeasurement(String measurementType);

    /**
     * Used to stop monitoring a measurement for a particular patient (eg. systolic blood pressure).
     * @param measurementType: the measurement of interest (eg. cholesterol).
     */
    void removeHealthMeasurement(String measurementType);
}
