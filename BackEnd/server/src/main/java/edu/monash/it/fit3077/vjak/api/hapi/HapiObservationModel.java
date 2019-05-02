package edu.monash.it.fit3077.vjak.api.hapi;

import edu.monash.it.fit3077.vjak.model.ObservationModelInterface;
import org.hl7.fhir.dstu3.model.Observation;

/*
This class is responsible for representing an Observation model. It's a specific concrete implementation
of representing observations and hence, tightly coupled with FHIR's description but does implement a common
interface so callers can use a common interface and not depend directly on this implementation.

This class uses the Adapter Pattern where it encapsulates Hapi's specific resource and implements a common interface
that callers will user. We only need to get the value (eg. 194.3343) and unit (eg. mg/dl) of an observation and hence,
this class will only get those properties from the internal Hapi's Observation object.
*/
public class HapiObservationModel implements ObservationModelInterface {
    private final Observation observation;

    HapiObservationModel(Observation resource) {
        this.observation = resource;
    }

    public String getUnit() {
        return this.observation.getValueQuantity().getUnit();
    }

    public String getValue() {
        return this.observation.getValueQuantity().getValue().toString();
    }

}
