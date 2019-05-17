package edu.monash.it.fit3077.vjak.model;

public class CholesterolMonitorEventModel extends MonitorEventModel {

    private String unit;
    private String value;

    CholesterolMonitorEventModel(CholesterolPatientMonitorModel pm) {
        super(pm);
        this.unit = pm.getMeasurementUnit();
        this.value = pm.getMeasurementValue();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
