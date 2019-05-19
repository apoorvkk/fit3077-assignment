package edu.monash.it.fit3077.akql.model.CholesterolModel;


import edu.monash.it.fit3077.akql.api.hapi.HapiCholesterol.HapiCholesterolObservationLoader;
import edu.monash.it.fit3077.akql.model.MonitorEventModel;
import edu.monash.it.fit3077.akql.model.PatientMonitorModel;

/**
 * This class represents the cholesterol patient monitor type. It is a subtype of PatientMonitorModel and specifies
 * the specifics of a cholesterol version of a patient monitor.
 */
public class CholesterolPatientMonitorModel extends PatientMonitorModel {
    private String measurementValue;
    private String measurementUnit;

    /**
     * Initializes the cholesterol patient monitor.
     * @param patientId: the id of the patient to be monitored.
     * @param firstClientId: the id of the first client to request monitoring of this patient.
     */
    public CholesterolPatientMonitorModel(String patientId, String firstClientId){

        super(patientId, firstClientId);

        this.observationLoader = new HapiCholesterolObservationLoader();
        this.poll();
    }

    /**
     * Gets the latest cholesterol data and stores it inside this model.
     */
    @Override
    protected void fetchData() {
        CholesterolObservationModelInterface latestObservation = this.observationLoader.getLatestObservation(this.patientId, CholesterolPatientMonitorModel.this.getMeasurementCode());

        if (latestObservation != null) {
            CholesterolPatientMonitorModel.this.measurementValue = latestObservation.getValue();
            CholesterolPatientMonitorModel.this.measurementUnit = latestObservation.getUnit();


            for (String clientId: CholesterolPatientMonitorModel.this.clientIds) {
                CholesterolPatientMonitorModel.this.notifyObservers(clientId);
            }
        }
    }

    /**
     * Gets the measurement unit of the cholesterol data of this monitor.
     * @return the measurement unit of the cholesterol data of this monitor.
     */
    String getMeasurementUnit() {
        return this.measurementUnit;
    }

    /**
     * Gets the measurement value of the cholesterol data of this monitor.
     * @return the measurement value of the cholesterol data of this monitor.
     */
    String getMeasurementValue() {
        return this.measurementValue;
    }

    /**
     * Gets the type of health measurement this monitor is monitoring.
     * @return the type of health measurement this monitor is monitoring.
     */
    public String getMeasurementType() {
        return "Cholesterol";
    }

    /**
     * Gets the code of the cholesterol measurement of the HAPI server.
     * @return the code of the cholesterol measurement of the HAPI server.
     */
    protected String getMeasurementCode() {
        return "2093-3";
    } // Used for FHIR and is also an ID of measurement type.

    /**
     * Serializes the cholesterol monitor object so that it can be sent via the web sockets.
     * @return the serialized cholesterol monitor event model.
     */
    @Override
    public MonitorEventModel serialize() {
        return new CholesterolMonitorEventModel(this);
    }
}
