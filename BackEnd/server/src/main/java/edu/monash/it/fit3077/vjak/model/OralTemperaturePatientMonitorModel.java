package edu.monash.it.fit3077.vjak.model;


import edu.monash.it.fit3077.vjak.api.hapi.HapiOralTemperatureObservationLoader;
/*
This class represents the cholesterol patient monitor type. It is a subtype of PatientMonitorModel and specifies
the specifics of a cholesterol version of a patient monitor.
 */
public class OralTemperaturePatientMonitorModel extends PatientMonitorModel {
    private String measurementValue;
    private String measurementUnit;

    OralTemperaturePatientMonitorModel(String patientId, String firstClientId){

        super(patientId, firstClientId);

        this.observationLoader = new HapiOralTemperatureObservationLoader();
        this.poll();
    }

    @Override
    void fetchData() {
        OralTemperatureObservationModelInterface latestObservation = this.observationLoader.getLatestObservation(this.patientId, OralTemperaturePatientMonitorModel.this.getMeasurementCode());

        if (latestObservation != null) {
            OralTemperaturePatientMonitorModel.this.measurementValue = latestObservation.getValue();
            OralTemperaturePatientMonitorModel.this.measurementUnit = latestObservation.getUnit();


            for (String clientId: OralTemperaturePatientMonitorModel.this.clientIds) {
                OralTemperaturePatientMonitorModel.this.notifyObservers(clientId);
            }
        }

    }

    String getMeasurementUnit() {
        return this.measurementUnit;
    }

    String getMeasurementValue() {
        return this.measurementValue;
    }

    public String getMeasurementType() {
        return "Oral Temperature";
    }

    protected String getMeasurementCode() {
        return "8331-1";
    } // Used for FHIR and is also an ID of measurement type.

    @Override
    public MonitorEventModel serialize() {
        return new OralTemperatureMonitorEventModel(this);
    }
}
