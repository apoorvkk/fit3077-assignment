package edu.monash.it.fit3077.vjak.model;


import edu.monash.it.fit3077.vjak.api.hapi.HapiCholesterolObservationLoader;
/*
This class represents the cholesterol patient monitor type. It is a subtype of PatientMonitorModel and specifies
the specifics of a cholesterol version of a patient monitor.
 */
public class CholesterolPatientMonitorModel extends PatientMonitorModel {
    private String measurementValue;
    private String measurementUnit;

    CholesterolPatientMonitorModel(String patientId, String firstClientId){

        super(patientId, firstClientId);

        this.observationLoader = new HapiCholesterolObservationLoader();
    }

    @Override
    void fetchData() {
        CholesterolObservationModelInterface latestObservation = this.observationLoader.getLatestObservation(this.patientId, CholesterolPatientMonitorModel.this.getMeasurementCode());

        if (latestObservation != null) {
            CholesterolPatientMonitorModel.this.measurementValue = latestObservation.getValue();
            CholesterolPatientMonitorModel.this.measurementUnit = latestObservation.getUnit();


            for (String clientId: CholesterolPatientMonitorModel.this.clientIds) {
                CholesterolPatientMonitorModel.this.notifyObservers(clientId);
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
        return "Cholesterol";
    }

    protected String getMeasurementCode() {
        return "2093-3";
    } // Used for FHIR and is also an ID of measurement type.

    @Override
    public MonitorEventModel serialize() {
        return new CholesterolMonitorEventModel(this);
    }
}
