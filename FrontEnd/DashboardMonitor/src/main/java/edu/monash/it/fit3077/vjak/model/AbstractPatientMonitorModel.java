package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.observer.Observer;

public abstract class AbstractPatientMonitorModel implements Observer {
    public abstract PatientModelInterface getPatient();
}
