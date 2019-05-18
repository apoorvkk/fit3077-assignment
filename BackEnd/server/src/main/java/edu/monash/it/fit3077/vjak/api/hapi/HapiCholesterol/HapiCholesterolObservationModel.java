package edu.monash.it.fit3077.vjak.api.hapi.HapiCholesterol;

import edu.monash.it.fit3077.vjak.api.hapi.HapiObservationModel;
import edu.monash.it.fit3077.vjak.model.CholesterolModel.CholesterolObservationModelInterface;
import org.hl7.fhir.dstu3.model.Observation;

public class HapiCholesterolObservationModel extends HapiObservationModel implements CholesterolObservationModelInterface {

    HapiCholesterolObservationModel(Observation resource) {
        super(resource);
    }

    public String getUnit() {
        return this.observation.getValueQuantity().getUnit();
    }

    public String getValue() {
        return this.observation.getValueQuantity().getValue().toString();
    }
}
