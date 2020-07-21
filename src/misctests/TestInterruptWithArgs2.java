package misctests;

import java.awt.geom.Point2D;
import simkit.Schedule;
import simkit.SimEntityBase;
import simkit.smd.BasicLinearMover;
import simkit.smd.CookieCutterSensor;
import simkit.smd.Mover;
import simkit.smd.Sensor;

/**
 *
 * @author ahbuss
 */
public class TestInterruptWithArgs2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mover mover = new BasicLinearMover("Fred", new Point2D.Double(10, 20), 30);
        Mover mover2 = new BasicLinearMover("Barney", new Point2D.Double(30, 40), 50);
        Sensor sensor = new CookieCutterSensor(mover, 25);
        Sensor sensor2 = new CookieCutterSensor(mover2, 35);

        SimEntityBase obj = new CancelClass(mover, sensor2);

        Schedule.setVerbose(true);

        Schedule.reset();
        obj.waitDelay("EnterRange", 15, mover, sensor2);
        obj.waitDelay("ExitRange", 20, mover, sensor2);
        obj.waitDelay("Cancel", 5);
        Schedule.startSimulation();

    }

    public static class CancelClass extends SimEntityBase {

        private Mover mover;
        private Sensor sensor;
        public CancelClass(Mover mover, Sensor sensor) {
            this.mover = mover;
            this.sensor = sensor;
        }
        public void doCancel() {
            interrupt("ExitRange", mover, sensor);
        }

    }

}
