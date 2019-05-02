package edu.monash.it.fit3077.vjak.view;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.AbstractPatientMonitorCollectionModel;
import edu.monash.it.fit3077.vjak.model.PatientMonitorModelInterface;
import edu.monash.it.fit3077.vjak.model.health.HealthMeasurementModel;
import edu.monash.it.fit3077.vjak.observer.Observer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

/*
This class is responsible for rendering the main dashboard view and updating the monitors whenever selected patient measurment
data changes.
 */
public class MonitorsView implements JavaFXView, Observer {
    private final Node rootNode;
    private final AbstractPatientMonitorCollectionModel model;
    private final VBox patientDetailListVBox;

    MonitorsView(AbstractPatientMonitorCollectionModel model) {
        /*
        Observe the model and setup the view base infrastructure. This class does not need a controller at the moment
        because it is not receiving any user input.
         */
        this.model = model;
        this.model.attach(this);

        ScrollPane sp = new ScrollPane();
        sp.setPrefWidth(0.75 * Constant.guiWindowWidth);
        sp.setPrefHeight(Constant.guiWindowHeight - 23d);

        this.patientDetailListVBox = new VBox(5);

        Text title = new Text("SafeHeart Monitor");
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        VBox mainVBox = new VBox(title, this.patientDetailListVBox);
        mainVBox.setSpacing(10);
        sp.setContent(mainVBox);

        AnchorPane.setRightAnchor(sp, 0d);
        this.rootNode = sp;
    }

    public Node getRootNode() {
        return this.rootNode;
    }

    public void update() {
        Platform.runLater(() -> { // run on main thread instead of the thread that received the event because that thread cannot update view due to Java FX restrictions.
            ArrayList<PatientMonitorModelInterface> selectedPatientMonitors = this.model.getSelectedPatientMonitors();

            this.patientDetailListVBox.getChildren().clear(); // Remove all monitors.

            // Rerender new patients and their relevant monitors.
            selectedPatientMonitors.forEach(patientMonitor -> {
                GridPane patientDetailsPane = new GridPane();
                patientDetailsPane.setPrefWidth(0.75 * Constant.guiWindowWidth - 20d);
                patientDetailsPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                patientDetailsPane.add(new Text("Id:"), 0, 0);
                patientDetailsPane.add(new Text(patientMonitor.getPatient().getId()), 1, 0);
                patientDetailsPane.add(new Text("Name: "), 0, 1);
                patientDetailsPane.add(new Text(patientMonitor.getPatient().getName()), 1, 1);

                // Render the monitors for a given patient.
                int nextRow = 2;
                for (HealthMeasurementModel healthMeasurement: patientMonitor.getHealthMeasurements()) {
                    healthMeasurement.attach(MonitorsView.this);
                    patientDetailsPane.add(new Text(healthMeasurement.toString() + ": "), 0, nextRow);
                    patientDetailsPane.add(new Text(healthMeasurement.getHealthMeasurementResult()), 1, nextRow);
                    nextRow++;
                }

                // Attach to the view.
                this.patientDetailListVBox.getChildren().add(patientDetailsPane);
            });
        });
    }
}
