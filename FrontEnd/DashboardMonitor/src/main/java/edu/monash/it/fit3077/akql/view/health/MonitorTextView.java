package edu.monash.it.fit3077.akql.view.health;

import edu.monash.it.fit3077.akql.model.health.AbstractHealthMeasurementModel;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/*
This class is responsible to render a text based version of a given monitor.
 */
public abstract class MonitorTextView extends AbstractHealthMeasurementView {
    private Text currentTextNode;

    /**
     * Initialization.
     * @param hm: the health measurement model.
     */
    MonitorTextView(AbstractHealthMeasurementModel hm) {
        super(hm);
    }

    /**
     * Used to get the result from the model.
     * @return the text string to render.
     */
    abstract protected String renderResult();

    /**
     * Method to initialize the view. Here we create a simple HBox and render a text node into it.
     * We keep a reference to the text node so that we can set its text whenever the model updates.
     */
    @Override
    protected void setUpView() {
        HBox hbox = new HBox();
        this.currentTextNode = new Text(this.renderResult());
        hbox.getChildren().add(this.currentTextNode);
        this.rootNode = hbox;
    }

    /*
    This method is called whenever the model updates. Here, we just rerender the result into the existing text node.
     */
    @Override
    public void update() {
        this.currentTextNode.setText(this.renderResult());
    }
}
