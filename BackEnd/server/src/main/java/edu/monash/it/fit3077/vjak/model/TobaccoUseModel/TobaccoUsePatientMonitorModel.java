package edu.monash.it.fit3077.vjak.model.TobaccoUseModel;


import edu.monash.it.fit3077.vjak.api.hapi.HapiTobaccoUse.HapiTobaccoUseObservationLoader;
import edu.monash.it.fit3077.vjak.model.MonitorEventModel;
import edu.monash.it.fit3077.vjak.model.PatientMonitorModel;

/*
This class represents the cholesterol patient monitor type. It is a subtype of PatientMonitorModel and specifies
the specifics of a cholesterol version of a patient monitor.
 */
public class TobaccoUsePatientMonitorModel extends PatientMonitorModel {
    private String measurementStatus;

    public TobaccoUsePatientMonitorModel(String patientId, String firstClientId){

        super(patientId, firstClientId);

        this.observationLoader = new HapiTobaccoUseObservationLoader();
        this.poll();
    }

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

    String getMeasurementStatus() {
        return this.measurementStatus;
    }

    public String getMeasurementType() {
        return "TobaccoUse";
    }

    protected String getMeasurementCode() {
        return "72166-2";
    } // Used for FHIR and is also an ID of measurement type.

    @Override
    public MonitorEventModel serialize() {
        return new TobaccoUseMonitorEventModel(this);
    }
}
