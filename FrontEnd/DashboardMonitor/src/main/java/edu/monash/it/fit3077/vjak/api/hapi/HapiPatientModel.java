package edu.monash.it.fit3077.vjak.api.hapi;

import edu.monash.it.fit3077.vjak.model.PatientModelInterface;

/*
This class is responsible for representing a Patient model. It's a specific concrete implementation
of representing observations and hence, tightly coupled with FHIR's description but does implement a common
interface so callers can use a common interface and not depend directly on this implementation.

This class uses the Adapter Pattern where it encapsulates Hapi's specific resource and implements a common interface
that callers will user. We only need to get the id (eg. abebd2132) and name (eg. mg/dl) of a patient and hence,
this class will only get those properties from the internal Hapi's Patient object.
*/
public class HapiPatientModel implements PatientModelInterface {
    private final org.hl7.fhir.dstu3.model.Patient patient;

    /**
     * Constructor to store the FHIR Patient.
     * @param p: FHIR Patient.
     */
    HapiPatientModel(org.hl7.fhir.dstu3.model.Patient p) {
        this.patient = p;
    }

    /**
     * Accesses the FHIR patient to get its id and return back to caller code.
     * @return the patient id.
     */
    public String getId() {
        return this.patient.getIdElement().getIdPart();
    }

    /**
     * Accesses the FHIR patient to get its name and return back to caller code.
     * @return the patient name.
     */
    public String getName() {
        return this.patient.getName().get(0).getFamily();
    }
}
