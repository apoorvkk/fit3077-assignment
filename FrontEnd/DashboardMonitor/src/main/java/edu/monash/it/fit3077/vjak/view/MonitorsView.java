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

    /**
     * Observe the model and setup the view base infrastructure. This class does not need a controller at the moment
     * because it is not receiving any user input.
     * @param model: the patient monitor collection model to observe.
     */
    MonitorsView(AbstractPatientMonitorCollectionModel model) {
        this.currentList = new LinkedHashMap<>();
        this.model = model;
        this.model.attach(this);

        // Initialize the view by creating the JavaFx components.
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

    /**
     * Used so parent views can attach nodes of child views to itself.
     * @return JavaFX node
     */
    public Node getRootNode() {
        return this.rootNode;
    }

    /**
     * This method is called when the model is updated. Here, we update the list of selected monitors for a patient.
     * Since we do not want to recreate all the subviews, we synchronize the new list of patient monitors with the existing
     * subviews and simply create new subviews when needed (eg. new patient monitor selected but there is no view).
     */
    public void update() {
        Platform.runLater(() -> { // run on main thread instead of the thread that received the event because that thread cannot update view due to Java FX restrictions.
            ArrayList<PatientMonitorModelInterface> latestSelectedPatientMonitors = this.model.getSelectedPatientMonitors();

            LinkedHashMap<PatientMonitorModelInterface, PatientMonitorView> updatedList = new LinkedHashMap<>();
            latestSelectedPatientMonitors.forEach(pm -> {
                PatientMonitorView view;

                if (this.currentList.containsKey(pm)) { // view already exists so don't create it again and just reuse the old one.
                    view = this.currentList.get(pm);
                } else { // view does not exist so create a view.
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
