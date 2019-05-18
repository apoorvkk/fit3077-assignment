package edu.monash.it.fit3077.vjak.view.health;

import edu.monash.it.fit3077.vjak.Constant;
import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;
import edu.monash.it.fit3077.vjak.model.health.QuantityModelInterface;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class OralTemperatureView extends HealthMeasurementView {
    private Text currentTextNode;

    public OralTemperatureView(AbstractHealthMeasurementModel hm) {
        super(hm);
    }

    private String renderResult() {
        QuantityModelInterface cm = (QuantityModelInterface) this.model;
        if (cm.getValue() != null) {
            return Constant.oralTemperature + ": " + cm.getValue() + " " + cm.getUnit();
        }
        return Constant.oralTemperature + ": " + "N/A";
    }

    @Override
    void setUpView() {
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
