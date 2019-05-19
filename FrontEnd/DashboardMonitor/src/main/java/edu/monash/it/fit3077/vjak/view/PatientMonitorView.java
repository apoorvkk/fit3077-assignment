package edu.monash.it.fit3077.vjak.view;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.controller.PatientMonitorControllIerInterface;
import edu.monash.it.fit3077.vjak.controller.PatientMonitorController;
import edu.monash.it.fit3077.vjak.model.PatientMonitorModelInterface;
import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;
import edu.monash.it.fit3077.vjak.observer.Observer;
import edu.monash.it.fit3077.vjak.view.health.HealthMeasurementView;
import edu.monash.it.fit3077.vjak.view.health.HealthMeasurementViewCreator;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class PatientMonitorView implements JavaFXView, Observer {
    private final PatientMonitorModelInterface model;
    private final PatientMonitorControllIerInterface controller;
    private LinkedHashMap<String, HealthMeasurementView> healthMeasurementViews;
    private VBox monitorsVBox;
    private Node rootNode;

    PatientMonitorView(PatientMonitorModelInterface pm) {
        this.model = pm;
        this.model.attach(this);
        this.healthMeasurementViews = new LinkedHashMap<String, HealthMeasurementView>();
        this.controller = new PatientMonitorController(this.model, this);
    }

    private void initializePatientDetailsPane(GridPane patientDetailsPane) {
        patientDetailsPane.add(new Text("Id:"), 0, 0);
        patientDetailsPane.add(new Text(this.model.getPatient().getId()), 1, 0);
        patientDetailsPane.add(new Text("Name: "), 0, 1);
        patientDetailsPane.add(new Text(this.model.getPatient().getName()), 1, 1);
    }

    private void initializePatientMonitorVBox(VBox patientMonitorVBox) {
        patientMonitorVBox.setPadding(new Insets(5, 5, 5, 5));
        patientMonitorVBox.setPrefWidth(0.75 * Constant.guiWindowWidth - 20d);
        patientMonitorVBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    private CheckBox initializeMonitorCheckbox(String measurementName, String measurementType) {
        CheckBox checkbox = new CheckBox(measurementName);
        checkbox.setOnAction(event -> { // Add event handlers to select and deselect patients to start/stop monitors respectively.
            if (checkbox.isSelected()) {
                this.controller.trackMeasurement(measurementType);
            } else {
                this.controller.removeHealthMeasurement(measurementType);
            }
        });

        return checkbox;
    }

    private void initializeMonitorOptionsVBox(VBox monitorOptionsVBox) {
        monitorOptionsVBox.setPadding(new Insets(5, 5, 5, 5));
        monitorOptionsVBox.setPrefWidth(200d);
        monitorOptionsVBox.setPrefHeight(20d);

        monitorOptionsVBox.getChildren().add(this.initializeMonitorCheckbox(Constant.cholesterol, Constant.cholesterol));
        monitorOptionsVBox.getChildren().add(this.initializeMonitorCheckbox("Tobacco Use", Constant.tobaccoUse));
        monitorOptionsVBox.getChildren().add(this.initializeMonitorCheckbox("Oral Temperature", Constant.oralTemperature));
        monitorOptionsVBox.getChildren().add(this.initializeMonitorCheckbox("Systolic Blood Pressure", Constant.systolicBloodPressure));
        monitorOptionsVBox.getChildren().add(this.initializeMonitorCheckbox("Diastolic Blood Pressure", Constant.diastolicBloodPressure));
    }

    public void initialize() {
        VBox patientMonitorVBox = new VBox();
        this.initializePatientMonitorVBox(patientMonitorVBox);

        GridPane patientDetailsPane = new GridPane();
        this.initializePatientDetailsPane(patientDetailsPane);

        VBox monitorOptionsVBox = new VBox();
        this.initializeMonitorOptionsVBox(monitorOptionsVBox);

        this.monitorsVBox = new VBox();

        patientMonitorVBox.getChildren().add(patientDetailsPane);
        patientMonitorVBox.getChildren().add(monitorOptionsVBox);
        patientMonitorVBox.getChildren().add(this.monitorsVBox);

        this.rootNode = patientMonitorVBox;
    }

    @Override
    public Node getRootNode() {
        return this.rootNode;
    }

    @Override
    public void update() {
        ArrayList<AbstractHealthMeasurementModel> latestHealthMeasurements = this.model.getHealthMeasurements();
        LinkedHashMap<String, HealthMeasurementView> updatedHealthMeasurementViews = new LinkedHashMap<>();

        latestHealthMeasurements.forEach(healthMeasurementModel -> {
            HealthMeasurementView view;

            if (!this.healthMeasurementViews.containsKey(healthMeasurementModel.getMeasurementType())) {
                view = HealthMeasurementViewCreator.create(healthMeasurementModel);
            } else {
                view = this.healthMeasurementViews.get(healthMeasurementModel.getMeasurementType());
            }

            updatedHealthMeasurementViews.put(healthMeasurementModel.getMeasurementType(), view);
        });
        this.healthMeasurementViews = updatedHealthMeasurementViews;

        this.monitorsVBox.getChildren().clear();
        this.healthMeasurementViews.forEach((key, view) -> {
           this.monitorsVBox.getChildren().add(view.getRootNode());
        });
    }
}
