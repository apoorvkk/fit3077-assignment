package edu.monash.it.fit3077.vjak.view;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.controller.PatientMonitorCollectionController;
import edu.monash.it.fit3077.vjak.controller.PatientMonitorCollectionControllerInterface;
import edu.monash.it.fit3077.vjak.model.AbstractPatientMonitorCollectionModel;
import edu.monash.it.fit3077.vjak.model.PatientMonitorModelInterface;
import edu.monash.it.fit3077.vjak.observer.Observer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;

/*
This class is responsible for rendering the sidebar that has a list of available patients and to also let the
patient load more patients.
 */
public class PatientListView implements JavaFXView, Observer {
    private final VBox rootNode;
    private VBox patientListVBox;
    private final AbstractPatientMonitorCollectionModel model;
    private final PatientMonitorCollectionControllerInterface controller;
    private Button loadMoreButton;

    PatientListView(AbstractPatientMonitorCollectionModel model) {
        /*
        Observe the model and initialize the controller.
         */
        this.model = model;
        this.model.attach(this);
        this.rootNode = new VBox();
        this.controller = new PatientMonitorCollectionController(model, this);
    }

    public void initializeLoadMoreButton() {
        // Setup the load more button and attach it to the host node.
        Button button = new Button();
        button.setPrefWidth(0.25 * Constant.guiWindowWidth);
        button.setOnAction(event -> this.controller.loadMorePatients());

        this.loadMoreButton = button;
        this.rootNode.getChildren().add(this.loadMoreButton);
    }

    public void initializePatientList() {
        // Setup the patient list sidebar and attach it to the host node.
        this.patientListVBox = new VBox();

        ScrollPane patientListScrollPane = new ScrollPane();
        patientListScrollPane.setPrefWidth(0.25 * Constant.guiWindowWidth);
        patientListScrollPane.setPrefHeight(Constant.guiWindowHeight - 50d);
        patientListScrollPane.setContent(this.patientListVBox);

        this.rootNode.getChildren().add(patientListScrollPane);
    }

    public void setViewToFetchingState() {
        // Ensure that load more button is disabled when patients are being loaded.
        this.loadMoreButton.setText("Loading patients ...");
        this.loadMoreButton.setDisable(true);
    }

    public Node getRootNode() {
        return this.rootNode;
    }

    private void updatePatientList(ArrayList<PatientMonitorModelInterface> patientMonitors) {
        /*
        This method is responsible for fetching the latest version of the patient list and simply rendering
        them into the side bar as selectable items.
         */
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
                cb.setOnAction(event -> { // Add event handlers to select and deselect patients to start/stop monitors respectively.
                    if (cb.isSelected()) {
                        this.controller.startMonitoring(patientMonitor);
                    } else {
                        this.controller.stopMonitoring(patientMonitor);
                    }

                });
                cellNode.setPrefHeight(20d);
                this.patientListVBox.getChildren().add(cellNode);
            });
        }
    }

    private void updateLoadMoreButton() {
        // Reset the load more button.
        String loadMoreButtonText = "Load more patients";
        this.loadMoreButton.setText(loadMoreButtonText);
        this.loadMoreButton.setDisable(false);
    }

    public void update() {
        Platform.runLater(() -> { // run on main thread instead of the thread that received the event because that thread cannot update view due to Java FX restrictions.
            this.updatePatientList(this.model.getPatientMonitors());
            this.updateLoadMoreButton();
        });
    }
}
