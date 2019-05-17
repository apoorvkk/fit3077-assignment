package edu.monash.it.fit3077.vjak.model;


import edu.monash.it.fit3077.vjak.api.hapi.HapiBloodPressureObservationLoader;

import java.util.Dictionary;

/*
This class represents the cholesterol patient monitor type. It is a subtype of PatientMonitorModel and specifies
the specifics of a cholesterol version of a patient monitor.
 */
public class BloodPressurePatientMonitorModel extends PatientMonitorModel {
    private String type;
    private String measurementValue;
    private String measurementUnit;

    BloodPressurePatientMonitorModel(String patientId, String firstClientId, String type){

        super(patientId, firstClientId);
        this.type = type;
        this.observationLoader = new HapiBloodPressureObservationLoader(type);
        this.poll();
    }

    @Override
    void fetchData() {
        BloodPressureObservationModelInterface latestObservation = this.observationLoader.getLatestObservation(this.patientId, BloodPressurePatientMonitorModel.this.getMeasurementCode());

        if (latestObservation != null) {
            BloodPressurePatientMonitorModel.this.measurementValue = latestObservation.getValue();
            BloodPressurePatientMonitorModel.this.measurementUnit = latestObservation.getUnit();


            for (String clientId: BloodPressurePatientMonitorModel.this.clientIds) {
                BloodPressurePatientMonitorModel.this.notifyObservers(clientId);
            }
        }
    }

    @Override
    protected int getPollingTime() {
        return 10000;
    }

    String getMeasurementUnit() {
        return this.measurementUnit;
    }

    String getMeasurementValue() {

        return this.measurementValue;
    }

    public String getMeasurementType() {
        return this.type;
    }

    protected String getMeasurementCode() {
        return "55284-4";
    } // Used for FHIR and is also an ID of measurement type.

    @Override
    public MonitorEventModel serialize() {
        return new BloodPressureMonitorEventModel(this);
    }
}
