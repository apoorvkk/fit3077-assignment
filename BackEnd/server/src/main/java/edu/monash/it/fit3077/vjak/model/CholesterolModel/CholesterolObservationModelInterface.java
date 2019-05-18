package edu.monash.it.fit3077.vjak.model.CholesterolModel;

/*
This interface is used to form an API contract to represent observations. This helps in decoupling raw implementation and
caller code which allows us to easily change to a different implementation without having to change the application
logic calling code a lot.
 */
public interface CholesterolObservationModelInterface {
    String getUnit();
    String getValue();
}
