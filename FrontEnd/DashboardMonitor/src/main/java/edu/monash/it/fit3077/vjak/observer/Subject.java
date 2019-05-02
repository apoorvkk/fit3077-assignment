package edu.monash.it.fit3077.vjak.observer;

import java.util.ArrayList;

public abstract class Subject {
    private final ArrayList<Observer> observers;

    protected Subject() {
        this.observers = new ArrayList<>();
    }

    public void attach(Observer o) {
        if (!this.observers.contains(o)) {
            this.observers.add(o);
        }
    }

    public void detach(Observer o) {
        this.observers.remove(o);
    }

    public void notifyObservers() {
        for (Observer o: this.observers) {
            o.update();
        }
    }
}
