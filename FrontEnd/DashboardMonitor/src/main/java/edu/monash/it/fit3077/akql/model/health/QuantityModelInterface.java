package edu.monash.it.fit3077.akql.model.health;

/*
This interface is mainly used to separate views from health measurement models and restrict access to that model by strictly
defining the contract here.
 */
public interface QuantityModelInterface {
    /**
     * Return the value.
     * @return the value.
     */
    Double getValue();
    /**
     * Return the unit.
     * @return the unit.
     */
    String getUnit();
}
