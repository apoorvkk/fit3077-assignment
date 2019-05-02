package edu.monash.it.fit3077.vjak.model;

/*
This interface is responsible for defining a contract that callers (eg. controllers) can use to manipulate the model.
This provides a nice separation of concerns between the controller and model through the dependency inversion principle.
 */
public interface PatientModelInterface {
    String getId();
    String getName();
}
