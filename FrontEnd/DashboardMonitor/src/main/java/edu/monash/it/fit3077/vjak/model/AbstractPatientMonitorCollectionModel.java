package edu.monash.it.fit3077.vjak.model;

import edu.monash.it.fit3077.vjak.observer.Subject;

import java.util.ArrayList;

public abstract class AbstractPatientMonitorCollectionModel extends Subject {
    public abstract void loadMorePatients();

    public abstract ArrayList<AbstractPatientMonitorModel> getPatientMonitors();
}
