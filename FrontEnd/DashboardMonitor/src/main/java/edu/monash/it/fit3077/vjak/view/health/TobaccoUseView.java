package edu.monash.it.fit3077.vjak.view.health;

import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;
import edu.monash.it.fit3077.vjak.model.health.TobaccoUse.TobaccoUseModelInterface;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class TobaccoUseView extends HealthMeasurementView {
    private Text currentTextNode;

    public TobaccoUseView(AbstractHealthMeasurementModel hm) {
        super(hm);
    }

    private String renderResult() {
        TobaccoUseModelInterface cm = (TobaccoUseModelInterface) this.model;
        if (cm.getStatus() != null) {
            return "Tobacco Use: " + cm.getStatus();
        }
        return "Tobacco Use: " + "N/A";
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


