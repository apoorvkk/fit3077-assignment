package edu.monash.it.fit3077.akql.view;

import edu.monash.it.fit3077.akql.Constant;
import edu.monash.it.fit3077.akql.controller.PatientMonitorCollectionController;
import edu.monash.it.fit3077.akql.controller.PatientMonitorCollectionControllerInterface;
import edu.monash.it.fit3077.akql.model.AbstractPatientMonitorCollectionModel;
import edu.monash.it.fit3077.akql.model.PatientMonitorModelInterface;
import edu.monash.it.fit3077.akql.observer.Observer;
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

    /**
     * Observe the model and initialize the controller.
     * @param model: the patient montor conllection model to observe.
     */
    PatientListView(AbstractPatientMonitorCollectionModel model) {
        this.model = model;
        this.model.attach(this);
        this.rootNode = new VBox();
        this.controller = new PatientMonitorCollectionController(model, this);
    }

    /**
     * Setup JavaFx components to create a load more button and attach it to the host node.
     */
    public void initializeLoadMoreButton() {
        Button button = new Button();
        button.setPrefWidth(0.25 * Constant.guiWindowWidth);
        button.setOnAction(event -> this.controller.loadMorePatients());

        this.loadMoreButton = button;
        this.rootNode.getChildren().add(this.loadMoreButton);
    }

    /**
     * Setup the patient list sidebar and attach it to the host node.
     */
    public void initializePatientList() {
        this.patientListVBox = new VBox();

        ScrollPane patientListScrollPane = new ScrollPane();
        patientListScrollPane.setPrefWidth(0.25 * Constant.guiWindowWidth);
        patientListScrollPane.setPrefHeight(Constant.guiWindowHeight - 50d);
        patientListScrollPane.setContent(this.patientListVBox);

        this.rootNode.getChildren().add(patientListScrollPane);
    }

    /**
     * Ensure that load more button is disabled when patients are being loaded.
     */
    public void setViewToFetchingState() {
        this.loadMoreButton.setText("Loading patients ...");
        this.loadMoreButton.setDisable(true);
    }

    /**
     * Used so parent views can attach nodes of child views to itself.
     * @return JavaFX node.
     */
    @Override
    public Node getRootNode() {
        return this.rootNode;
    }

    /**
     * This method is responsible for rendering the latest version of the patient list
     * into the side bar as selectable items.
     * @param patientMonitors: the patient monitors to attach to the sidebar.
     */
    private void updatePatientList(ArrayList<PatientMonitorModelInterface> patientMonitors) {
        this.patientListVBox.getChildren().clear(); // Reset the sidebar and add the newly updated list of patient monitors.
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

    /**
     * Reset the load more button.
     */
    private void updateLoadMoreButton() {
        String loadMoreButtonText = "Load more patients";
        this.loadMoreButton.setText(loadMoreButtonText);
        this.loadMoreButton.setDisable(false);
    }

    /**
     * This method is called whenever the model updates. Here we update the load more button and the patient list in the sidebar.
     */
    public void update() {
        Platform.runLater(() -> { // run on main thread instead of the thread that received the event because that thread cannot update view due to Java FX restrictions.
            this.updatePatientList(this.model.getPatientMonitors());
            this.updateLoadMoreButton();
        });
    }
}
