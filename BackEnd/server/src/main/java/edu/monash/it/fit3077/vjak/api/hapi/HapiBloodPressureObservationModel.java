package edu.monash.it.fit3077.vjak.api.hapi;

import edu.monash.it.fit3077.vjak.model.BloodPressureObservationModelInterface;
import org.hl7.fhir.dstu3.model.Observation;

import java.util.List;
import java.util.function.Function;

public class HapiBloodPressureObservationModel extends HapiObservationModel implements BloodPressureObservationModelInterface {

    private String type;

    HapiBloodPressureObservationModel(Observation resource, String type) {

        super(resource);
        this.type = type;
    }

    public String getType() { return this.type; }

    private String selectBloodPressure(Function<Observation.ObservationComponentComponent, String> getDesiredProperty) {

        List<Observation.ObservationComponentComponent> bloodPressureObservations = this.observation.getComponent();

        for (Observation.ObservationComponentComponent bloodPressureObservation : bloodPressureObservations) {
            if (bloodPressureObservation.getCode().getText().replaceAll(" ", "").equals(this.type)) {
                return getDesiredProperty.apply(bloodPressureObservation);
            }
        }
        return null;
    }

//    private String retrieveHapiUnit(Observation.ObservationComponentComponent bloodPressureComponent) {
//        return bloodPressureComponent.getValueQuantity().getUnit();
//    }
//    private String retrieveHapiValue(Observation.ObservationComponentComponent bloodPressureComponent) {
//        return bloodPressureComponent.getValueQuantity().getValue().toString();
//    }
    public String getUnit() {
        return this.selectBloodPressure(bloodPressureObservation -> bloodPressureObservation.getValueQuantity().getUnit());
    }

    public String getValue() {
        return this.selectBloodPressure(bloodPressureObservation -> bloodPressureObservation.getValueQuantity().getValue().toString());
    }
}
