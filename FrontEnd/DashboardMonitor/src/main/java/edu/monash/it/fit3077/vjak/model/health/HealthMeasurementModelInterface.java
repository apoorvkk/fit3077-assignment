package edu.monash.it.fit3077.vjak.model.health;

import edu.monash.it.fit3077.vjak.backendmonitor.MeasurementEventModel;
import edu.monash.it.fit3077.vjak.observer.SubjectInterface;

public interface HealthMeasurementModelInterface extends SubjectInterface {
    String getHealthMeasurementResult();
    void setHealthMeasurementValue(MeasurementEventModel me);
    String getMeasurementType();
}
