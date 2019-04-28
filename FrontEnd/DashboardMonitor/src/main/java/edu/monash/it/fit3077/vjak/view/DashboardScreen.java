package edu.monash.it.fit3077.vjak.view;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.AbstractPatientMonitorCollectionModel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DashboardScreen {
    public DashboardScreen(Stage primaryStage, AbstractPatientMonitorCollectionModel model) {
        PatientList plv = new PatientList(model);
        Group group = new Group(plv.getRootNode()); // root node
        Scene scene = new Scene(group, Constant.guiWindowWidth, Constant.guiWindowHeight);
        scene.setFill(Color.BROWN);

        primaryStage.setTitle("Dashboard Monitor");
        primaryStage.setMaxHeight(Constant.guiWindowHeight);
        primaryStage.setMinHeight(Constant.guiWindowHeight);
        primaryStage.setMaxWidth(Constant.guiWindowWidth);
        primaryStage.setMinWidth(Constant.guiWindowWidth);
        primaryStage.setFullScreen(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // terminate gui app and all running threads.
                System.exit(0);
            }
        });
    }
}
