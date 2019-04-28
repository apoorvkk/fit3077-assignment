package edu.monash.it.fit3077.vjak;

import edu.monash.it.fit3077.vjak.api.hapi.HapiPatientLoader;
import edu.monash.it.fit3077.vjak.model.PatientMonitorCollectionModel;
import edu.monash.it.fit3077.vjak.view.DashboardScreenView;
import javafx.application.Application;
import javafx.stage.Stage;

public class DashboardMonitorApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        HapiPatientLoader h = new HapiPatientLoader("3252");
        PatientMonitorCollectionModel mps = new PatientMonitorCollectionModel(h);
        DashboardScreenView dv = new DashboardScreenView(primaryStage, mps);
    }

    public static void main(String[] args) {
        launch(args);
    }
}