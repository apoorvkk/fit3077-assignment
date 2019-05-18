package edu.monash.it.fit3077.vjak.api.hapi.HapiOralTemperature;

import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationModel;
import edu.monash.it.fit3077.vjak.model.OralTemperatureModel.OralTemperatureObservationModelInterface;
import org.hl7.fhir.dstu3.model.Observation;

public class HapiOralTemperatureObservationModel extends HapiObservationModel implements OralTemperatureObservationModelInterface {

    HapiOralTemperatureObservationModel(Observation resource) {
        super(resource);
    }

    public String getUnit() {
        return this.observation.getValueQuantity().getUnit();
    }

    public String getValue() {
        return this.observation.getValueQuantity().getValue().toString();
    }
}
