package edu.monash.it.fit3077.vjak.view;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.AbstractPatientMonitorCollectionModel;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DashboardScreenView {
    public DashboardScreenView(Stage primaryStage, AbstractPatientMonitorCollectionModel model) {
        PatientListView plv = new PatientListView(model);
        MonitorsView mv = new MonitorsView(model);

        AnchorPane ap = new AnchorPane(plv.getRootNode(), mv.getRootNode());
        Scene scene = new Scene(ap, Constant.guiWindowWidth, Constant.guiWindowHeight);

        primaryStage.setTitle("Dashboard Monitor");
        primaryStage.setMaxHeight(Constant.guiWindowHeight);
        primaryStage.setMinHeight(Constant.guiWindowHeight);
        primaryStage.setMaxWidth(Constant.guiWindowWidth);
        primaryStage.setMinWidth(Constant.guiWindowWidth);
        primaryStage.setFullScreen(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            // Deregister from the server so it does not keep sending more events to a closed application.
            model.getPatientMonitors().forEach(patientMonitor -> {
                patientMonitor.removeMeasurements();
            });
        });
    }
}
