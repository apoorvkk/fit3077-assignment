package edu.monash.it.fit3077.akql.api.hapi.HapiBloodPressure;

import edu.monash.it.fit3077.akql.api.hapi.HapiObservationModel;
import edu.monash.it.fit3077.akql.model.BloodPressureModel.BloodPressureObservationModelInterface;
import org.hl7.fhir.dstu3.model.Observation;

import java.util.List;
import java.util.function.Function;

/**
 * Represents HAPI's version of the blood pressure measurement.
 */
public class HapiBloodPressureObservationModel extends HapiObservationModel implements BloodPressureObservationModelInterface {

    private String type;

    /**
     * Instantiates the blood pressure measurement presentation model.
     * @param resource: HAPI's blood pressure measurement.
     * @param type: the blood pressure type (systolic or diastolic).
     */
    HapiBloodPressureObservationModel(Observation resource, String type) {

        super(resource);
        this.type = type;
    }

    /**
     * Gets the type of blood pressure this model holds.
     * @return the type of the blood pressure measurement.
     */
    public String getType() { return this.type; }

    /**
     * Performs an operation on a blood pressure measurement of this model's type.
     * @param getDesiredProperty: the operation to perform on the blood pressure measurement.
     * @return
     */
    private String selectBloodPressure(Function<Observation.ObservationComponentComponent, String> getDesiredProperty) {

        List<Observation.ObservationComponentComponent> bloodPressureObservations = this.observation.getComponent();

        for (Observation.ObservationComponentComponent bloodPressureObservation : bloodPressureObservations) {
            if (bloodPressureObservation.getCode().getText().replaceAll(" ", "").equals(this.type)) {
                return getDesiredProperty.apply(bloodPressureObservation);
            }
        }
        return null;
    }

    /**
     * Gets the unit of the blood pressure measurement from the HAPI blood pressure measurement.
     * @return the unit of the blood pressure measurement.
     */
    public String getUnit() {
        return this.selectBloodPressure(bloodPressureObservation -> bloodPressureObservation.getValueQuantity().getUnit());
    }

    /**
     * Gets the value of the blood pressure measurement from the HAPI blood pressure measurement.
     * @return the value of the blood pressure measurement.
     */
    public String getValue() {
        return this.selectBloodPressure(bloodPressureObservation -> bloodPressureObservation.getValueQuantity().getValue().toString());
    }
}
