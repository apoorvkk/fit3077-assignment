package edu.monash.it.fit3077.vjak.view.health;

import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;
import edu.monash.it.fit3077.vjak.model.health.QuantityModelInterface;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public abstract class MonitorTextView extends HealthMeasurementView {
    private Text currentTextNode;

    MonitorTextView(AbstractHealthMeasurementModel hm) {
        super(hm);
    }

    protected String renderResult() {
        QuantityModelInterface cm = (QuantityModelInterface) this.model;
        if (cm.getValue() != null) {
            return this.getMeasurementName() + ": " + cm.getValue() + " " + cm.getUnit();
        }
        return this.getMeasurementName() + ": " + "N/A";
    }

    public abstract String getMeasurementName();

    @Override
    protected void setUpView() {
        HBox hbox = new HBox();
        this.currentTextNode = new Text(this.renderResult());
        hbox.getChildren().add(this.currentTextNode);
        this.rootNode = hbox;
    }

    @Override
    public void update() {
        this.currentTextNode.setText(this.renderResult());
    }
}
