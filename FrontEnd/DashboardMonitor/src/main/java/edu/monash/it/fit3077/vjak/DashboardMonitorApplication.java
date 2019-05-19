package edu.monash.it.fit3077.vjak;

import edu.monash.it.fit3077.vjak.api.hapi.HapiPatientLoader;
import edu.monash.it.fit3077.vjak.model.PatientMonitorCollectionModel;
import edu.monash.it.fit3077.vjak.view.DashboardScreenView;
import javafx.application.Application;
import javafx.stage.Stage;

/*
Driver class. It is bootstrapped with JavaFx so it can run a GUI application.
 */
public class DashboardMonitorApplication extends Application {

    /**
     * This is a JavaFX wrapped application. We are specifying how to start up the JavaFX application
     * and intialize the necessary models, patient loader and views.
     * @param primaryStage: JavaFX class used as the basis of the GUI app.
     */
    @Override
    public void start(Stage primaryStage) {
        HapiPatientLoader h = new HapiPatientLoader("30"); // Hardcoded practitioner id.
        PatientMonitorCollectionModel mps = new PatientMonitorCollectionModel(h);
        new DashboardScreenView(primaryStage, mps);
    }

    public static void main(String[] args) {
        launch(args);
    }
}