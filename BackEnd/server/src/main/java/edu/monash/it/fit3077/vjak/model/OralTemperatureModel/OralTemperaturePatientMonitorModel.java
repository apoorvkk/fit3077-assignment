package edu.monash.it.fit3077.vjak.model.OralTemperatureModel;


import edu.monash.it.fit3077.vjak.api.hapi.HapiOralTemperature.HapiOralTemperatureObservationLoader;
import edu.monash.it.fit3077.vjak.model.MonitorEventModel;
import edu.monash.it.fit3077.vjak.model.PatientMonitorModel;

/**
 * This class represents the oral temperature patient monitor type. It is a subtype of PatientMonitorModel and specifies
 * the specifics of a oral temperature version of a patient monitor.
 */
public class OralTemperaturePatientMonitorModel extends PatientMonitorModel {
    private String measurementValue;
    private String measurementUnit;

    /**
     * Initializes the oral temperature patient monitor.
     * @param patientId: the id of the patient to be monitored.
     * @param firstClientId: the id of the first client to request monitoring of this patient.
     */
    public OralTemperaturePatientMonitorModel(String patientId, String firstClientId){

        super(patientId, firstClientId);

        this.observationLoader = new HapiOralTemperatureObservationLoader();
        this.poll();
    }

    /**
     * Gets the latest oral temperature data and stores it inside this model.
     */
    @Override
    protected void fetchData() {
        OralTemperatureObservationModelInterface latestObservation = this.observationLoader.getLatestObservation(this.patientId, OralTemperaturePatientMonitorModel.this.getMeasurementCode());

        if (latestObservation != null) {
            OralTemperaturePatientMonitorModel.this.measurementValue = latestObservation.getValue();
            OralTemperaturePatientMonitorModel.this.measurementUnit = latestObservation.getUnit();


            for (String clientId: OralTemperaturePatientMonitorModel.this.clientIds) {
                OralTemperaturePatientMonitorModel.this.notifyObservers(clientId);
            }
        }
    }

    /**
     * Gets the measurement unit of the oral temperature data of this monitor.
     * @return the measurement unit of the oral temperature data of this monitor.
     */
    String getMeasurementUnit() {
        return this.measurementUnit;
    }

    /**
     * Gets the measurement value of the oral temperature data of this monitor.
     * @return the measurement value of the oral temperature data of this monitor.
     */
    String getMeasurementValue() {
        return this.measurementValue;
    }

    /**
     * Gets the type of health measurement this monitor is monitoring.
     * @return the type of health measurement this monitor is monitoring.
     */
    public String getMeasurementType() {
        return "OralTemperature";
    }

    /**
     * Gets the code of the oral temperature measurement of the HAPI server.
     * @return the code of the oral temperature measurement of the HAPI server.
     */
    protected String getMeasurementCode() {
        return "8331-1";
    } // Used for FHIR and is also an ID of measurement type.

    /**
     * Serializes the oral temperature monitor object so that it can be sent via the web sockets.
     * @return the serialized oral temperature monitor event model.
     */
    @Override
    public MonitorEventModel serialize() {
        return new OralTemperatureMonitorEventModel(this);
    }
}
