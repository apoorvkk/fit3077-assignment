package edu.monash.it.fit3077.vjak.api.hapi.HapiCholesterol;

import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationModel;
import edu.monash.it.fit3077.vjak.model.CholesterolModel.CholesterolObservationModelInterface;
import org.hl7.fhir.dstu3.model.Observation;

/**
 * Represents HAPI's version of the cholesterol measurement.
 */
public class HapiCholesterolObservationModel extends HapiObservationModel implements CholesterolObservationModelInterface {

    /**
     * Instantiates the cholesterol measurement presentation model.
     * @param resource: HAPI's cholesterol measurement.
     */
    HapiCholesterolObservationModel(Observation resource) {
        super(resource);
    }

    /**
     * Gets the unit of the cholesterol measurement from the HAPI cholesterol measurement.
     * @return the unit of the cholesterol measurement.
     */
    public String getUnit() {
        return this.observation.getValueQuantity().getUnit();
    }

    /**
     * Gets the value of the cholesterol measurement from the HAPI cholesterol measurement.
     * @return the value of the cholesterol measurement.
     */
    public String getValue() {
        return this.observation.getValueQuantity().getValue().toString();
    }
}
