package edu.monash.it.fit3077.vjak.view.health;

import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;
import edu.monash.it.fit3077.vjak.observer.Observer;
import edu.monash.it.fit3077.vjak.view.JavaFXView;
import javafx.scene.Node;

public abstract class HealthMeasurementView implements JavaFXView, Observer {
    protected AbstractHealthMeasurementModel model;
    protected Node rootNode;

    public HealthMeasurementView(AbstractHealthMeasurementModel hm) {
        this.model = hm;
        this.model.attach(this);
        this.setUpView();
    }

    abstract void setUpView();

    @Override
    public Node getRootNode() {
        return this.rootNode;
    }
}
