package edu.monash.it.fit3077.vjak.api.hapi.HapiOralTemperature;

import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationModel;
import edu.monash.it.fit3077.vjak.model.OralTemperatureModel.OralTemperatureObservationModelInterface;
import org.hl7.fhir.dstu3.model.Observation;

/**
 * Represents HAPI's version of the oral temperature measurement.
 */
public class HapiOralTemperatureObservationModel extends HapiObservationModel implements OralTemperatureObservationModelInterface {

    /**
     * Instantiates the oral temperature measurement presentation model.
     * @param resource: HAPI's oral temperature measurement.
     */
    HapiOralTemperatureObservationModel(Observation resource) {
        super(resource);
    }

    /**
     * Gets the unit of the oral temperature measurement from the HAPI oral temperature measurement.
     * @return the unit of the oral temperature measurement.
     */
    public String getUnit() {
        return this.observation.getValueQuantity().getUnit();
    }

    /**
     * Gets the value of the oral temperature measurement from the HAPI oral temperature measurement.
     * @return the value of the oral temperature measurement.
     */
    public String getValue() {
        return this.observation.getValueQuantity().getValue().toString();
    }
}
