package edu.monash.it.fit3077.vjak.model;

import java.util.ArrayList;

/*
This interface is used to define a contract that the application calling code can use to load patients. It decouples the
loading patients implementation from the application through the dependency inversion principle. We program to interfaces
not implementations.
 */
public interface PatientLoaderInterface {
    /**
     * Fetch new patients.
     * @return List of new patients.
     */
    ArrayList<PatientModelInterface> loadPatients();
}
