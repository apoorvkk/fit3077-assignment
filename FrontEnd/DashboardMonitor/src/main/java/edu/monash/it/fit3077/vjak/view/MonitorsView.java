package edu.monash.it.fit3077.vjak.view;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.AbstractPatientMonitorCollectionModel;
import edu.monash.it.fit3077.vjak.model.PatientMonitorModelInterface;
import edu.monash.it.fit3077.vjak.observer.Observer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/*
This class is responsible for rendering the main dashboard view and updating the monitors whenever selected patient measurement
data changes.
 */
public class MonitorsView implements JavaFXView, Observer {
    private final Node rootNode;
    private final AbstractPatientMonitorCollectionModel model;
    private final VBox patientDetailListVBox;
    private LinkedHashMap<PatientMonitorModelInterface, PatientMonitorView> currentList;

    MonitorsView(AbstractPatientMonitorCollectionModel model) {
        /*
        Observe the model and setup the view base infrastructure. This class does not need a controller at the moment
        because it is not receiving any user input.
         */
        this.currentList = new LinkedHashMap<>();
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
            /*
            Since we do not want to recreate all the subviews, we synchronize the new list of patient monitors with the
            existing subviews and simply create new subviews when needed (eg. new patient monitor selected but there is no view).
             */
            ArrayList<PatientMonitorModelInterface> latestSelectedPatientMonitors = this.model.getSelectedPatientMonitors();

            LinkedHashMap<PatientMonitorModelInterface, PatientMonitorView> updatedList = new LinkedHashMap<>();
            latestSelectedPatientMonitors.forEach(pm -> {
                PatientMonitorView view;

                if (this.currentList.containsKey(pm)) {
                    view = this.currentList.get(pm);
                } else {
                    view = new PatientMonitorView(pm);
                }
                updatedList.put(pm, view);
            });
            this.currentList = updatedList;

            // Render the new list of view nodes.
            ArrayList<Node> nodes = new ArrayList<>();
            for (PatientMonitorModelInterface pm : this.currentList.keySet()) {
                PatientMonitorView view = this.currentList.get(pm);
                nodes.add(view.getRootNode());
            }
            this.patientDetailListVBox.getChildren().clear();
            this.patientDetailListVBox.getChildren().addAll(nodes);
        });
    }
}
