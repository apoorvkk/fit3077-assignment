package edu.monash.it.fit3077.akql.model.health;

import edu.monash.it.fit3077.akql.backendmonitor.MeasurementEventModel;
import edu.monash.it.fit3077.akql.observer.Subject;

/*
This class represents a generic health measurement model that can register/deregister to receive its values.
 */
public abstract class AbstractHealthMeasurementModel extends Subject {
    private MeasurementTracker measurementTracker;

    /**
     * This method initiate tracking for a particular health measurement by telling the backend service to register it.
     * @param patientId: the patient id.
     */
    protected void track(String patientId) {
        this.measurementTracker = new MeasurementTracker(patientId, this.getMeasurementType());
        this.measurementTracker.register();
    }

    /**
     * Tell the backend service to stop sending events about this measurement.
     */
    public void cleanUp() {
        measurementTracker.deregister();
    }

    /**
     * Returns a description of the model. Mainly used to display the model in presentational format.
     * @return the description of the model.
     */
    abstract public String getDescription();

    /**
     * This is used to take the measurement event that came from web sockets and store event payload data into
     * the the health measurement class appropriately. Subclasses will override this method to determine how to store the values
     * based on their needs (eg. blood pressure store history of values while tobacco use store latest text).
     * @param me: The raw payload wrapped in a business adaptor model.
     */
    abstract public void setHealthMeasurementValue(MeasurementEventModel me);

    /**
     * Return the official measurement type.
     * @return the measurement type.
     */
    abstract public String getMeasurementType();
}
