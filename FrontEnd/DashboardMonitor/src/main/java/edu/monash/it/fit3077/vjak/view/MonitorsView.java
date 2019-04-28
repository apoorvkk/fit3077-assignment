package edu.monash.it.fit3077.vjak.view;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.AbstractPatientMonitorCollectionModel;
import edu.monash.it.fit3077.vjak.model.PatientMonitorModelInterface;
import edu.monash.it.fit3077.vjak.observer.Observer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
        sp.setPrefHeight(Constant.guiWindowHeight - 25d);

        this.patientDetailListVBox = new VBox(5);
        patientDetailListVBox.getChildren().add(new Text("YOOOOO"));
        sp.setContent(patientDetailListVBox);

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
            patientDetailListVBox.getChildren().add(new Text("YOOOOO"));

            selectedPatientMonitors.forEach(patientMonitor -> {
                FlowPane patientMonitorPane = new FlowPane();
                patientMonitorPane.setPrefWidth(0.75 * Constant.guiWindowWidth - 20d);
                patientMonitorPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                GridPane patientDetailsPane = new GridPane();
                patientDetailsPane.add(new Text("Id:"), 0, 0);
                patientDetailsPane.add(new Text(patientMonitor.getPatient().getId()), 1, 0);
                patientDetailsPane.add(new Text("Name: "), 0, 1);
                patientDetailsPane.add(new Text(patientMonitor.getPatient().getName()), 1, 1);

                HBox monitors = new HBox();

                patientMonitorPane.getChildren().add(patientDetailsPane);
                patientMonitorPane.getChildren().add(monitors);

                this.patientDetailListVBox.getChildren().add(patientMonitorPane);
            });
        });
    }
}
