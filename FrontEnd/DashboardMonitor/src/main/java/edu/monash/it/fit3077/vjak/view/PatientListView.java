package edu.monash.it.fit3077.vjak.view;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.controller.PatientMonitorCollectionController;
import edu.monash.it.fit3077.vjak.controller.PatientMonitorCollectionInterface;
import edu.monash.it.fit3077.vjak.model.AbstractPatientMonitorCollectionModel;
import edu.monash.it.fit3077.vjak.model.AbstractPatientMonitorModel;
import edu.monash.it.fit3077.vjak.observer.Observer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
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
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PatientListView.this.controller.loadMorePatients();
            }
        });

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

    public void update() {
        Platform.runLater(() -> {
            ArrayList<AbstractPatientMonitorModel> patientMonitors = this.model.getPatientMonitors();

            this.patientListVBox.getChildren().clear();
            if (patientMonitors.size() == 0) {
                this.patientListVBox.getChildren().add(new Text("Unable to find patients."));
            } else {
                patientMonitors.forEach(patientMonitor -> {
                    PatientListItemView listItem = new PatientListItemView(patientMonitor.getPatient());
                    this.patientListVBox.getChildren().add(listItem.getRootNode());
                });
            }

            this.loadMoreButton.setText(this.loadMoreButtonText);
            this.loadMoreButton.setDisable(false);
        });
    }
}
