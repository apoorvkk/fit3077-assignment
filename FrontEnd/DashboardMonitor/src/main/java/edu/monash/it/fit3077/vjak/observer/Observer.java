package edu.monash.it.fit3077.vjak.observer;

/*
Apart of the observer design pattern.
 */
public interface Observer {
    /**
     * Whenever this method is called, observers know that it's time to fetch new data from the model and update accordingly.
     */
    void update();
}
