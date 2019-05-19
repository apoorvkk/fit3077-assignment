package edu.monash.it.fit3077.akql.model.BloodPressureModel;


import edu.monash.it.fit3077.akql.api.hapi.HapiBloodPressure.HapiBloodPressureObservationLoader;
import edu.monash.it.fit3077.akql.model.MonitorEventModel;
import edu.monash.it.fit3077.akql.model.PatientMonitorModel;

/**
 * This class represents the blood pressure patient monitor type. It is a subtype of PatientMonitorModel and specifies
 * the specifics of a blood pressure version of a patient monitor.
 */
public class BloodPressurePatientMonitorModel extends PatientMonitorModel {
    private String type;
    private String measurementValue;
    private String measurementUnit;

    /**
     * Initializes the blood pressure patient monitor.
     * @param patientId: the id of the patient to be monitored.
     * @param firstClientId: the id of the first client to request monitoring of this patient.
     * @param type: the type of the blood pressure to be monitored for this patient (systolic or diastolic).
     */
    public BloodPressurePatientMonitorModel(String patientId, String firstClientId, String type){

        super(patientId, firstClientId);
        this.type = type;
        this.observationLoader = new HapiBloodPressureObservationLoader(type);
        this.poll();
    }

    /**
     * Gets the latest blood pressure data and stores it inside this model.
     */
    @Override
    protected void fetchData() {
        BloodPressureObservationModelInterface latestObservation = this.observationLoader.getLatestObservation(this.patientId, BloodPressurePatientMonitorModel.this.getMeasurementCode());

        if (latestObservation != null) {
            BloodPressurePatientMonitorModel.this.measurementValue = latestObservation.getValue();
            BloodPressurePatientMonitorModel.this.measurementUnit = latestObservation.getUnit();


            for (String clientId: BloodPressurePatientMonitorModel.this.clientIds) {
                BloodPressurePatientMonitorModel.this.notifyObservers(clientId);
            }
        }
    }

    /**
     * Gets the polling time (how frequent this monitor polls the system for new blood pressure data).
     * @return the polling time of this blood pressure monitor.
     */
    @Override
    protected int getPollingTime() {
        return 10000;
    }

    /**
     * Gets the measurement unit of the blood pressure data of this monitor.
     * @return the measurement unit of the blood pressure data of this monitor.
     */
    String getMeasurementUnit() {
        return this.measurementUnit;
    }

    /**
     * Gets the measurement value of the blood pressure data of this monitor.
     * @return the measurement value of the blood pressure data of this monitor.
     */
    String getMeasurementValue() {

        return this.measurementValue;
    }

    /**
     * Gets the type of health measurement this monitor is monitoring.
     * @return the type of health measurement this monitor is monitoring.
     */
    public String getMeasurementType() {
        return this.type;
    }

    /**
     * Gets the code of the blood pressure measurement of the HAPI server.
     * @return the code of the blood pressure measurement of the HAPI server.
     */
    protected String getMeasurementCode() {
        return "55284-4";
    } // Used for FHIR and is also an ID of measurement type.

    /**
     * Serializes the blood pressure monitor object so that it can be sent via the web sockets.
     * @return the serialized blood pressure monitor event model.
     */
    @Override
    public MonitorEventModel serialize() {
        return new BloodPressureMonitorEventModel(this);
    }
}
