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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
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

    public void initialize() {
        VBox patientMonitorVBox = new VBox();
        patientMonitorVBox.setPadding(new Insets(5, 5, 5, 5));
        patientMonitorVBox.setPrefWidth(0.75 * Constant.guiWindowWidth - 20d);
        patientMonitorVBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        GridPane patientDetailsPane = new GridPane();
        patientDetailsPane.add(new Text("Id:"), 0, 0);
        patientDetailsPane.add(new Text(this.model.getPatient().getId()), 1, 0);
        patientDetailsPane.add(new Text("Name: "), 0, 1);
        patientDetailsPane.add(new Text(this.model.getPatient().getName()), 1, 1);
        patientMonitorVBox.getChildren().add(patientDetailsPane);

        HBox monitorSectionHBox = new HBox();

        VBox monitorOptionsVBox = new VBox();
        monitorOptionsVBox.setPadding(new Insets(5, 5, 5, 5));
        monitorOptionsVBox.setPrefWidth(200d);
        monitorOptionsVBox.setPrefHeight(20d);

        CheckBox cholesterolOptionCheckbox = new CheckBox(Constant.cholesterol);
        cholesterolOptionCheckbox.setOnAction(event -> { // Add event handlers to select and deselect patients to start/stop monitors respectively.
            if (cholesterolOptionCheckbox.isSelected()) {
                this.controller.trackMeasurement(Constant.cholesterol);
            } else {
                this.controller.removeHealthMeasurement(Constant.cholesterol);
            }
        });
        CheckBox oralTemperatureOptionCheckbox = new CheckBox("Oral Temperature");
        oralTemperatureOptionCheckbox.setOnAction(event -> { // Add event handlers to select and deselect patients to start/stop monitors respectively.
            if (oralTemperatureOptionCheckbox.isSelected()) {
                this.controller.trackMeasurement(Constant.oralTemperature);
            } else {
                this.controller.removeHealthMeasurement(Constant.oralTemperature);
            }
        });
        CheckBox tobaccoUseOptionCheckbox = new CheckBox("Tobacco Use");
        tobaccoUseOptionCheckbox.setOnAction(event -> { // Add event handlers to select and deselect patients to start/stop monitors respectively.
            if (tobaccoUseOptionCheckbox.isSelected()) {
                this.controller.trackMeasurement(Constant.tobaccoUse);
            } else {
                this.controller.removeHealthMeasurement(Constant.tobaccoUse);
            }
        });
        CheckBox systolicBloodPressureOptionCheckbox = new CheckBox("Systolic Blood Pressure");
        systolicBloodPressureOptionCheckbox.setOnAction(event -> { // Add event handlers to select and deselect patients to start/stop monitors respectively.
            if (systolicBloodPressureOptionCheckbox.isSelected()) {
                this.controller.trackMeasurement(Constant.systolicBloodPressure);
            } else {
                this.controller.removeHealthMeasurement(Constant.systolicBloodPressure);
            }
        });
        CheckBox diastolicBloodPressureOptionCheckbox = new CheckBox("Diastolic Blood Pressure");
        diastolicBloodPressureOptionCheckbox.setOnAction(event -> { // Add event handlers to select and deselect patients to start/stop monitors respectively.
            if (diastolicBloodPressureOptionCheckbox.isSelected()) {
                this.controller.trackMeasurement(Constant.diastolicBloodPressure);
            } else {
                this.controller.removeHealthMeasurement(Constant.diastolicBloodPressure);
            }
        });

        monitorOptionsVBox.getChildren().add(cholesterolOptionCheckbox);
        monitorOptionsVBox.getChildren().add(tobaccoUseOptionCheckbox);
        monitorOptionsVBox.getChildren().add(oralTemperatureOptionCheckbox);
        monitorOptionsVBox.getChildren().add(systolicBloodPressureOptionCheckbox);
        monitorOptionsVBox.getChildren().add(diastolicBloodPressureOptionCheckbox);

        monitorSectionHBox.getChildren().add(monitorOptionsVBox);

        patientMonitorVBox.getChildren().add(monitorSectionHBox);

        this.monitorsVBox = new VBox();
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
