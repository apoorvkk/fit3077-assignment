package edu.monash.it.fit3077.akql.observer;

import java.util.ArrayList;

/*
Apart of the observer design pattern.
 */
public abstract class Subject implements SubjectInterface {
    private final ArrayList<Observer> observers;

    /**
     * Initialize the observers.
     */
    protected Subject() {
        this.observers = new ArrayList<>();
    }

    /**
     * Adds a new observer to the current list of observers.
     * @param o: the new observer that wants to monitor this subject.
     */
    public void attach(Observer o) {
        if (!this.observers.contains(o)) {
            this.observers.add(o);
        }
    }

    /**
     * Removes an observer that does not want to observer this subject.
     * @param o: the observer.
     */
    public void detach(Observer o) {
        this.observers.remove(o);
    }

    /**
     * Notifies all registered observers that something has changed in subject.
     */
    public void notifyObservers() {
        for (Observer o: this.observers) {
            o.update();
        }
    }
}
