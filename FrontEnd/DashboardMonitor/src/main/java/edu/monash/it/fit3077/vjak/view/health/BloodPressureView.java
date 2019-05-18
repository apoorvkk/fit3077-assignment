package edu.monash.it.fit3077.vjak.view.health;

import edu.monash.it.fit3077.vjak.model.health.AbstractHealthMeasurementModel;
import edu.monash.it.fit3077.vjak.model.health.BloodPressure.BloodPressureInterface;
import edu.monash.it.fit3077.vjak.model.health.QuantityModelInterface;
import javafx.application.Platform;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BloodPressureView extends HealthMeasurementView {
    private XYChart.Series<String, Number> series;
    private final int windowSize = 10;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    private NumberAxis yAxis;
    private Text crisisWarningNode;

    BloodPressureView(AbstractHealthMeasurementModel hm) {
        super(hm);
    }

    @Override
    void setUpView() {
        VBox bloodPressureSection = new VBox();

        CategoryAxis xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();

        xAxis.setLabel("Time/s");
        xAxis.setAnimated(false); // axis animations are removed
        this.yAxis.setAnimated(false); // axis animations are removed

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(this.model.getDescription());
        lineChart.setAnimated(false); // disable animations
        this.series = new XYChart.Series<>();
        series.setName(this.model.getDescription());
        lineChart.getData().add(series);
        bloodPressureSection.getChildren().add(lineChart);

        this.crisisWarningNode = new Text();
        bloodPressureSection.getChildren().add(this.crisisWarningNode);

        this.rootNode = bloodPressureSection;
    }

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

            this.yAxis.setLabel(bloodPressureModel.getUnit());
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
