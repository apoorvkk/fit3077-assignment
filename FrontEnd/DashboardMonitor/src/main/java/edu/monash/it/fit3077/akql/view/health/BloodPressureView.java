package edu.monash.it.fit3077.akql.view.health;

import edu.monash.it.fit3077.akql.model.health.AbstractHealthMeasurementModel;
import edu.monash.it.fit3077.akql.model.health.BloodPressure.BloodPressureInterface;
import javafx.application.Platform;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
This class is responsible to render a graph for the blood pressure monitor.
 */
public class BloodPressureView extends AbstractHealthMeasurementView {
    private XYChart.Series<String, Number> series;
    private final int windowSize = 10;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    private NumberAxis yAxis;
    private Text crisisWarningNode;

    /**
     * Initialization.
     * @param hm: the health measurement model that holds the necessary data to show on the graph.
     */
    BloodPressureView(AbstractHealthMeasurementModel hm) {
        super(hm);
    }

    /**
     * Initializes the necessary JavaFx components to show the linechart.
     */
    @Override
    protected void setUpView() {
        VBox bloodPressureSection = new VBox();

        // Define axes.
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Time/s");
        xAxis.setAnimated(false); // axis animations are removed
        this.yAxis = new NumberAxis();
        this.yAxis.setAnimated(false); // axis animations are removed

        // Initialize chart with axes.
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, this.yAxis);
        lineChart.setTitle(this.model.getDescription());
        lineChart.setAnimated(false); // disable animations

        // Set data object and attach to line chart.
        this.series = new XYChart.Series<>();
        series.setName(this.model.getDescription());
        lineChart.getData().add(series);

        bloodPressureSection.getChildren().add(lineChart);

        // Setup warning text.
        this.crisisWarningNode = new Text();
        bloodPressureSection.getChildren().add(this.crisisWarningNode);

        this.rootNode = bloodPressureSection;
    }

    /**
     * This method is called whenever the registered model updates. In this case, we update the graph
     * and show a warning text if the patient is in hypertensive crisis.
     */
    @Override
    public void update() {
        Platform.runLater(() -> { // run on main thread instead of the thread that received the event because that thread cannot update view due to Java FX restrictions.
            BloodPressureInterface bloodPressureModel = (BloodPressureInterface) this.model;

            if (bloodPressureModel.isInHypertensiveCrisis()) {
                this.crisisWarningNode.setText("WARNING: PATIENT IN HYPERTENSIVE CRISIS!");
            } else {
                this.crisisWarningNode.setText("");
            }

            Double latestValue = bloodPressureModel.getValue();

            this.yAxis.setLabel(bloodPressureModel.getUnit()); // set here because the unit data comes from backend and not hardcoded.
            if (latestValue != null) {
                Date now = new Date();
                this.series.getData().add(new XYChart.Data<>(this.simpleDateFormat.format(now), latestValue));
                if (series.getData().size() > this.windowSize) {
                    series.getData().remove(0);
                }
            }
        });
    }
}
