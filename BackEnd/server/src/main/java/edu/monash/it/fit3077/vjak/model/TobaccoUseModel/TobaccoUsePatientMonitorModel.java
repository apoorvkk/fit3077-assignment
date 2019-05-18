package edu.monash.it.fit3077.vjak.model.TobaccoUseModel;


import edu.monash.it.fit3077.vjak.api.hapi.HapiTobaccoUse.HapiTobaccoUseObservationLoader;
import edu.monash.it.fit3077.vjak.model.MonitorEventModel;
import edu.monash.it.fit3077.vjak.model.PatientMonitorModel;

/**
 * This class represents the tobacco use patient monitor type. It is a subtype of PatientMonitorModel and specifies
 * the specifics of a tobacco use version of a patient monitor.
 */
public class TobaccoUsePatientMonitorModel extends PatientMonitorModel {
    private String measurementStatus;

    /**
     * Initializes the tobacco use patient monitor.
     * @param patientId: the id of the patient to be monitored.
     * @param firstClientId: the id of the first client to request monitoring of this patient.
     */
    public TobaccoUsePatientMonitorModel(String patientId, String firstClientId){

        super(patientId, firstClientId);

        this.observationLoader = new HapiTobaccoUseObservationLoader();
        this.poll();
    }

    /**
     * Gets the latest tobacco use data and stores it inside this model.
     */
    @Override
    protected void fetchData() {
        TobaccoUseObservationModelInterface latestObservation = this.observationLoader.getLatestObservation(this.patientId, TobaccoUsePatientMonitorModel.this.getMeasurementCode());

        if (latestObservation != null) {
            TobaccoUsePatientMonitorModel.this.measurementStatus = latestObservation.getStatus();


            for (String clientId: TobaccoUsePatientMonitorModel.this.clientIds) {
                TobaccoUsePatientMonitorModel.this.notifyObservers(clientId);
            }
        }
    }

    /**
     * Gets the measurement status of the tobacco use data of this monitor.
     * @return the measurement status of the tobacco use data of this monitor.
     */
    String getMeasurementStatus() {
        return this.measurementStatus;
    }

    /**
     * Gets the type of health measurement this monitor is monitoring.
     * @return the type of health measurement this monitor is monitoring.
     */
    public String getMeasurementType() {
        return "TobaccoUse";
    }

    /**
     * Gets the code of the tobacco use measurement of the HAPI server.
     * @return the code of the tobacco use measurement of the HAPI server.
     */
    protected String getMeasurementCode() {
        return "72166-2";
    } // Used for FHIR and is also an ID of measurement type.

    /**
     * Serializes the tobacco use monitor object so that it can be sent via the web sockets.
     * @return the serialized tobacco use monitor event model.
     */
    @Override
    public MonitorEventModel serialize() {
        return new TobaccoUseMonitorEventModel(this);
    }
}
