package edu.monash.it.fit3077.vjak.model.health.BloodPressure;

import edu.monash.it.fit3077.vjak.model.health.QuantityModelInterface;

public interface BloodPressureInterface extends QuantityModelInterface {
    boolean isInHypertensiveCrisis();
}
