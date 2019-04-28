package edu.monash.it.fit3077.vjak.view;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.controller.PatientMonitorCollectionController;
import edu.monash.it.fit3077.vjak.controller.PatientMonitorCollectionInterface;
import edu.monash.it.fit3077.vjak.model.AbstractPatientMonitorCollectionModel;
import edu.monash.it.fit3077.vjak.model.PatientMonitorModelInterface;
import edu.monash.it.fit3077.vjak.observer.Observer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class PatientListView implements JavaFXView, Observer {
    private VBox rootNode;
    private VBox patientListVBox;
    private AbstractPatientMonitorCollectionModel model;
    private PatientMonitorCollectionInterface controller;
    private Button loadMoreButton;
    private final String loadMoreButtonText = "Load more patients";

    public PatientListView(AbstractPatientMonitorCollectionModel model) {
        this.model = model;
        this.model.attach(this);
        this.rootNode = new VBox();

        this.initializePatientList();
        this.initializeLoadMoreButton();
        this.controller = new PatientMonitorCollectionController(model, this);
        this.controller.loadMorePatients();
    }

    private void initializeLoadMoreButton() {
        Button button = new Button();
        button.setPrefWidth(0.25 * Constant.guiWindowWidth);
        button.setOnAction(event -> this.controller.loadMorePatients());

        this.loadMoreButton = button;
        this.rootNode.getChildren().add(this.loadMoreButton);
    }

    private void initializePatientList() {
        this.patientListVBox = new VBox();

        ScrollPane patientListScrollPane = new ScrollPane();
        patientListScrollPane.setPrefWidth(0.25 * Constant.guiWindowWidth);
        patientListScrollPane.setPrefHeight(Constant.guiWindowHeight - 50d);
        patientListScrollPane.setContent(this.patientListVBox);

        this.rootNode.getChildren().add(patientListScrollPane);
    }

    public void setViewToFetchingState() {
        this.loadMoreButton.setText("Loading patients ...");
        this.loadMoreButton.setDisable(true);
    }

    public Node getRootNode() {
        return this.rootNode;
    }

    private void updatePatientList(ArrayList<PatientMonitorModelInterface> patientMonitors) {
        this.patientListVBox.getChildren().clear();
        if (patientMonitors.size() == 0) {
            this.patientListVBox.getChildren().add(new Text("Unable to find patients."));
        } else {
            patientMonitors.forEach(patientMonitor -> {
                HBox cellNode = new HBox();
                CheckBox cb = new CheckBox(patientMonitor.getPatient().getName());
                if (patientMonitor.isBeingMonitored()) {
                    cb.setSelected(true);
                }
                cellNode.getChildren().add(cb);
                cb.setOnAction(event -> {
                    if (cb.isSelected()) {
                        this.controller.startMonitoring(patientMonitor);
                    } else {
                        this.controller.stopMonitoring(patientMonitor);
                    }

                });

                this.patientListVBox.getChildren().add(cellNode);
            });
        }
    }

    private void updateLoadMoreButton() {
        this.loadMoreButton.setText(this.loadMoreButtonText);
        this.loadMoreButton.setDisable(false);
    }

    public void update() {
        Platform.runLater(() -> {
            this.updatePatientList(this.model.getPatientMonitors());
            this.updateLoadMoreButton();
        });
    }
}
