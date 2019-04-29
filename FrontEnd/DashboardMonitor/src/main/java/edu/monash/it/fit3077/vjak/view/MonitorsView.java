package edu.monash.it.fit3077.vjak.view;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.AbstractPatientMonitorCollectionModel;
import edu.monash.it.fit3077.vjak.model.PatientMonitorModelInterface;
import edu.monash.it.fit3077.vjak.model.health.HealthMeasurementModel;
import edu.monash.it.fit3077.vjak.observer.Observer;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class MonitorsView implements JavaFXView, Observer {
    private Node rootNode;
    private AbstractPatientMonitorCollectionModel model;
    private VBox patientDetailListVBox;

    public MonitorsView(AbstractPatientMonitorCollectionModel model) {
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
        Platform.runLater(() -> {
            ArrayList<PatientMonitorModelInterface> selectedPatientMonitors = this.model.getSelectedPatientMonitors();

            this.patientDetailListVBox.getChildren().clear();

            selectedPatientMonitors.forEach(patientMonitor -> {
                GridPane patientDetailsPane = new GridPane();
                patientDetailsPane.setPrefWidth(0.75 * Constant.guiWindowWidth - 20d);
                patientDetailsPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                patientDetailsPane.add(new Text("Id:"), 0, 0);
                patientDetailsPane.add(new Text(patientMonitor.getPatient().getId()), 1, 0);
                patientDetailsPane.add(new Text("Name: "), 0, 1);
                patientDetailsPane.add(new Text(patientMonitor.getPatient().getName()), 1, 1);

                int nextRow = 2;
                for (HealthMeasurementModel healthMeasurement: patientMonitor.getHealthMeasurements()) {
                    patientDetailsPane.add(new Text(healthMeasurement.toString() + ": "), 0, nextRow);
                    patientDetailsPane.add(new Text(healthMeasurement.getHealthMeasurementResult()), 1, nextRow);
                    nextRow++;
                }

                this.patientDetailListVBox.getChildren().add(patientDetailsPane);
            });
        });
    }
}