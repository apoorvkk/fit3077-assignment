package edu.monash.it.fit3077.vjak.model;

/*
This interface is responsible for defining a contract that callers (eg. controllers) can use to manipulate the patient model.
This provides a nice separation of concerns between the controller and model through the dependency inversion principle.
 */
public interface PatientModelInterface {
    /**
     * Return patient id.
     * @return patient id.
     */
    String getId();

    /**
     * Return patient name.
     * @return patient naem.
     */
    String getName();
}
