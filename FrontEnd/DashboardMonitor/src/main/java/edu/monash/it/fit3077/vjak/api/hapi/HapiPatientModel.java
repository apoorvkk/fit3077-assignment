package edu.monash.it.fit3077.vjak.api.hapi;

import edu.monash.it.fit3077.vjak.model.PatientModelInterface;

public class HapiPatientModel implements PatientModelInterface {
    private org.hl7.fhir.dstu3.model.Patient patient;

    public HapiPatientModel(org.hl7.fhir.dstu3.model.Patient p) {
        this.patient = p;
    }

    public String getId() {
        return this.patient.getId();
    }

    public String getName() {
        return this.patient.getName().get(0).getFamily();
    }
}
