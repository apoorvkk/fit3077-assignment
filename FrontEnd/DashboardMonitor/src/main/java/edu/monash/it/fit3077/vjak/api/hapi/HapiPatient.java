package edu.monash.it.fit3077.vjak.api.hapi;

import edu.monash.it.fit3077.vjak.model.Patient;

public class HapiPatient implements Patient {
    private org.hl7.fhir.dstu3.model.Patient patient;

    public HapiPatient(org.hl7.fhir.dstu3.model.Patient p) {
    }

    public void constructor(org.hl7.fhir.dstu3.model.Patient p) {
        this.patient = p;
    }

    public String getId() {
        return this.patient.getId();
    }
}
