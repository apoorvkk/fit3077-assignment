package edu.monash.it.fit3077.vjak.observer;

public interface SubjectInterface {
    void attach(Observer o);
    void detach(Observer o);
}
