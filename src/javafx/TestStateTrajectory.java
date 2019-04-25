package javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import misctests.LineChartPanel;
import simkit.Schedule;
import simkit.components.ArrivalProcess;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class TestStateTrajectory extends Application {

    private static ArrivalProcess arrivalProcess;

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
//        root.getChildren().add(btn);

        arrivalProcess = new ArrivalProcess(
                RandomVariateFactory.getInstance("Exponential", 3.5)
        );

        LineChartPanel lineChartPanel = new LineChartPanel(root);
        arrivalProcess.addPropertyChangeListener(lineChartPanel);
//        Scene scene = new Scene(root, 500, 400);

        primaryStage.setTitle("ArrivalProcess (" + arrivalProcess.getInterarrivalTimeGenerator() + ")");
        primaryStage.setScene(lineChartPanel);
//        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Schedule.setVerbose(true);
                Schedule.stopAtTime(10000.0);
                Schedule.reset();
                Schedule.startSimulation();
            }
        });
    }

}
