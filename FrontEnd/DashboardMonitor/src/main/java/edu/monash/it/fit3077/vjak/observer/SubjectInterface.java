package edu.monash.it.fit3077.vjak.observer;

/*
Simple interface used for separation of concerns.
 */
public interface SubjectInterface {
    /**
     * Adds a new observer to the current list of observers.
     * @param o: the new observer that wants to monitor this subject.
     */
    void attach(Observer o);

    /**
     * Removes an observer that does not want to observer this subject.
     * @param o: the observer.
     */
    void detach(Observer o);
}
