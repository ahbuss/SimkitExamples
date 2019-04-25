package misctests;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import simkit.Schedule;

/**
 *
 * @author ahbuss
 */
public class LineChartPanel extends Scene implements PropertyChangeListener, EventHandler<ActionEvent> {

    private static final Logger LOGGER = Logger.getLogger(LineChartPanel.class.getName());

    private final LineChart<Number, Number> lineChart;

    private final NumberAxis xAxis;

    private final NumberAxis yAxis;

    private String state;

    private final XYChart.Series<Number, Number> series;

    public LineChartPanel(Parent root) {
        super(root, 500, 400);
        this.xAxis = new NumberAxis();
        this.xAxis.setLabel("Simulate Time (simTime)");

        this.yAxis = new NumberAxis();
        this.lineChart = new LineChart<>(xAxis, yAxis);

        this.series = new XYChart.Series<>();
        lineChart.getData().add(series);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (state == null) {
            state = evt.getPropertyName();
        }
        if (state.equals(evt.getPropertyName())) {
            if (evt.getNewValue() instanceof Number) {
                XYChart.Data<Number, Number> newValue =
                        new XYChart.Data<>(Schedule.getSimTime(),
                        (Number) evt.getNewValue());
                series.getData().add(newValue);
            }
        }

    }

    @Override
    public void handle(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
