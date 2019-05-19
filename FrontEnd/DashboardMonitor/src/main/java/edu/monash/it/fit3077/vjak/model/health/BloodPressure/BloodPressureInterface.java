package edu.monash.it.fit3077.vjak.model.health.BloodPressure;

import edu.monash.it.fit3077.vjak.model.health.QuantityModelInterface;

/*
This interface is mainly used to separate views from BloodPressureModel and restrict access to that model by strictly
defining the contract here.
 */
public interface BloodPressureInterface extends QuantityModelInterface {
    /**
     * This method is responsible to determine if the patient is in hypertensive crisis.
     * @return true is they are in crisis mode or false if they are not.
     */
    boolean isInHypertensiveCrisis();
}
