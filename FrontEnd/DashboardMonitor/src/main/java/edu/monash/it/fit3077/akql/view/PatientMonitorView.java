package edu.monash.it.fit3077.akql.view;

import edu.monash.it.fit3077.akql.Constant;
import edu.monash.it.fit3077.akql.controller.PatientMonitorControllerInterface;
import edu.monash.it.fit3077.akql.controller.PatientMonitorController;
import edu.monash.it.fit3077.akql.model.PatientMonitorModelInterface;
import edu.monash.it.fit3077.akql.model.health.AbstractHealthMeasurementModel;
import edu.monash.it.fit3077.akql.observer.Observer;
import edu.monash.it.fit3077.akql.view.health.AbstractHealthMeasurementView;
import edu.monash.it.fit3077.akql.view.health.HealthMeasurementViewCreator;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/*
This class is responsible for rendering selected patient monitors in the main view. This will show the patient details
and an available list of health measurements (eg. cholesterol, blood pressure etc.) a practitioner can track for the
selected patient.
 */
public class PatientMonitorView implements JavaFXView, Observer {
    private final PatientMonitorModelInterface model;
    private final PatientMonitorControllerInterface controller;
    private LinkedHashMap<String, AbstractHealthMeasurementView> healthMeasurementViews;
    private VBox monitorsVBox;
    private Node rootNode;

    /**
     * Setup model and view references and health measurement views list.
     * @param pm: the patient monitor.
     */
    PatientMonitorView(PatientMonitorModelInterface pm) {
        this.model = pm;
        this.model.attach(this);
        this.healthMeasurementViews = new LinkedHashMap<String, AbstractHealthMeasurementView>();
        this.controller = new PatientMonitorController(this.model, this);
    }

    /**
     * This method is responsible for customizing JavaFX component that show the patient details (id, name).
     * @param patientDetailsPane: JavaFX node.
     */
    private void initializePatientDetailsPane(GridPane patientDetailsPane) {
        patientDetailsPane.add(new Text("Id:"), 0, 0);
        patientDetailsPane.add(new Text(this.model.getPatient().getId()), 1, 0);
        patientDetailsPane.add(new Text("Name: "), 0, 1);
        patientDetailsPane.add(new Text(this.model.getPatient().getName()), 1, 1);
    }

    /**
     * This method is responsible for customizing JavaFX component that holds all content for the selected patient
     * in the main view.
     * @param patientMonitorVBox: JavaFX node.
     */
    private void initializePatientMonitorVBox(VBox patientMonitorVBox) {
        patientMonitorVBox.setPadding(new Insets(5, 5, 5, 5));
        patientMonitorVBox.setPrefWidth(0.75 * Constant.guiWindowWidth - 20d);
        patientMonitorVBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    /**
     * Used to create the checkboxes and attach the event handlers that tack/detrack measurements for a selected patient.
     * @param measurementName: used to display measurement (eg. "Oral Temperature").
     * @param measurementType used to select measurement (eg. "OralTemperature").
     * @return JavaFx node.
     */
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

    /**
     * Populates component with health measurement option checkboxes.
     * @param monitorOptionsVBox: Java FX node.
     */
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

    /**
     * Initialize the view by creating the necessary JavaFX components.
     */
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

    /**
     * Used so parent views can attach nodes of child views to itself.
     * @return JavaFX node
     */
    @Override
    public Node getRootNode() {
        return this.rootNode;
    }

    /**
     * This method is called whenever the model updates. This will get the latest set of selected health measurements
     * for a selected patient monitor and render the relevant health measurement views. We avoid recreating every view
     * by comparing the old list of views with the lastest list of health measurements to determine the new list of health
     * measurement views.
     */
    @Override
    public void update() {
        ArrayList<AbstractHealthMeasurementModel> latestHealthMeasurements = this.model.getHealthMeasurements();
        LinkedHashMap<String, AbstractHealthMeasurementView> updatedHealthMeasurementViews = new LinkedHashMap<>();

        latestHealthMeasurements.forEach(healthMeasurementModel -> {
            AbstractHealthMeasurementView view;

            if (!this.healthMeasurementViews.containsKey(healthMeasurementModel.getMeasurementType())) { // health measurement view does not exist so create it.
                view = HealthMeasurementViewCreator.create(healthMeasurementModel);
            } else { // it already exists so just use that and don't create the view.
                view = this.healthMeasurementViews.get(healthMeasurementModel.getMeasurementType());
            }

            updatedHealthMeasurementViews.put(healthMeasurementModel.getMeasurementType(), view);
        });
        this.healthMeasurementViews = updatedHealthMeasurementViews;

        // Render into the app.
        this.monitorsVBox.getChildren().clear();
        this.healthMeasurementViews.forEach((key, view) -> {
           this.monitorsVBox.getChildren().add(view.getRootNode());
        });
    }
}
