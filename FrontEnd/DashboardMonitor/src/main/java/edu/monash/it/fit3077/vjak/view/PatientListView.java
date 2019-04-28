package edu.monash.it.fit3077.vjak.view;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.AbstractPatientMonitorCollectionModel;
import edu.monash.it.fit3077.vjak.observer.Observer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class PatientListView implements JavaFXView, Observer {
    private VBox rootNode;
    private AbstractPatientMonitorCollectionModel model;

    public PatientListView(AbstractPatientMonitorCollectionModel model) {
        this.model = model;
        this.model.attach(this);
        VBox patientsVBox = new VBox();

        patientsVBox.getChildren().add(this.renderPatientList(true));
        patientsVBox.getChildren().add(this.renderLoadMorePatientsButton());

        this.rootNode = patientsVBox;
    }

    private Button renderLoadMorePatientsButton() {
        Button button = new Button("Load more patients");
        button.setPrefWidth(0.25 * Constant.guiWindowWidth);
        return button;
    }

    private ScrollPane renderPatientList(Boolean isLoading) {
        ScrollPane sp = new ScrollPane();
        sp.setPrefWidth(0.25 * Constant.guiWindowWidth);
        sp.setPrefHeight(Constant.guiWindowHeight - 50d);
        VBox patientVBox = new VBox();

        if (isLoading) {
            patientVBox.getChildren().add(new Text("Loading your patients..."));
        } else if (this.model.getPatientMonitors().size() == 0) {
            patientVBox.getChildren().add(new Text("Unable to find any patients."));
        } else {
            this.model.getPatientMonitors()
                .forEach(patientMonitor -> {
                    PatientListItemView listItem = new PatientListItemView(patientMonitor.getPatient());

                    patientVBox.getChildren().add(listItem.getRootNode());
                });
        }

        sp.setContent(patientVBox);
        return sp;
    }

    public Node getRootNode() {
        return this.rootNode;
    }


    public void update() {
        Platform.runLater(() -> PatientListView.this.rootNode.getChildren().set(0, PatientListView.this.renderPatientList(false)));
    }
}
