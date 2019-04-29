package edu.monash.it.fit3077.vjak.model;

import java.util.ArrayList;

public abstract class PatientMonitor {
    private final ArrayList<String> clientIds;
    private final String patientId;
    private final String measurementUnit;
    private final String measurementValue;

    public PatientMonitor(String patientId, String firstClientId, ObservationLoaderInterface observationLoader) {
        this.patientId = patientId;
        this.clientIds = new ArrayList<String>();
        this.clientIds.add(firstClientId);

        ObservationModelInterface latestObservation = observationLoader.getLatestObservation(patientId, this.getMeasurementCode());
        this.measurementValue = latestObservation.getValue();
        this.measurementUnit = latestObservation.getUnit();
    }

    public boolean matches(String patientId, String measurementType){
        return this.patientId.equals(patientId) && this.getMeasurementType().equals(measurementType);
    }

    public void removeClient(String clientId) {
        this.clientIds.remove(clientId);
    }

    public boolean hasNoRegisteredClients() {
        return this.clientIds.size() == 0;
    }

    public String getMeasurementValue() {
        return this.measurementValue;
    };

    public String getMeasurementUnit() {
        return this.measurementUnit;
    }

    protected abstract String getMeasurementCode();
    public abstract String getMeasurementType();
}
