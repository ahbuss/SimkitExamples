package misctests;

import java.awt.geom.Point2D;
import simkit.Schedule;
import simkit.SimEntityBase;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;
import simkit.smd.BasicLinearMover;
import simkit.smd.CookieCutterSensor;
import simkit.smd.Mover;
import simkit.smd.Sensor;

/**
 *
 * @author ahbuss
 */
public class TestInterruptWithArgs extends SimEntityBase {

    private Mover[] movers;

    private Sensor[] sensors;

    public void doRun() {
        for (int i = 0; i < sensors.length; ++i) {
            for (int j = 0; j < movers.length; ++j) {
                if (i == j) {
                    continue;
                }
                waitDelay("EnterRange", 10.0 + i + j, sensors[i], movers[j]);
            }
        }
    }

    public void doStartMove(Mover mover) {
        for (Sensor sensor : sensors) {
            interrupt("EnterRange", sensor, mover);
        }
    }

    public void doStartMove(Sensor sensor) {
        for (Mover mover : movers) {
            interrupt("EnterRange", sensor, mover);
        }
    }

    /**
     * @return the sensors
     */
    public Sensor[] getSensors() {
        return sensors;
    }

    /**
     * @param sensors the sensors to set
     */
    public void setSensors(Sensor[] sensors) {
        this.sensors = sensors;
    }

    /**
     * @return the movers
     */
    public Mover[] getMovers() {
        return movers;
    }

    /**
     * @param movers the movers to set
     */
    public void setMovers(Mover[] movers) {
        this.movers = movers;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        RandomVariate rv = RandomVariateFactory.getInstance("Normal", 0.0, 5.0);

        Mover[] movers = new Mover[5];
        for (int i = 0; i < movers.length; ++i) {
            movers[i] = new BasicLinearMover(new Point2D.Double(rv.generate(), rv.generate()), 0);
        }

        Sensor[] sensors = new Sensor[movers.length];
        for (int i = 0; i < sensors.length; ++i) {
            sensors[i] = new CookieCutterSensor(movers[i], 30.0);
        }

        TestInterruptWithArgs tiwa = new TestInterruptWithArgs();
        tiwa.setMovers(movers);
        tiwa.setSensors(sensors);
        System.out.println(tiwa);

        Schedule.setVerbose(true);
        Schedule.addIgnoreOnDump("RegisterMover");
        Schedule.addIgnoreOnDump("RegisterSensor");
        Schedule.addIgnoreOnDump("Run");

        movers[0].addSimEventListener(tiwa);
        sensors[0].addSimEventListener(tiwa);

        Schedule.reset();
        movers[0].waitDelay("StartMove", 10.0, movers[0]);
        Schedule.startSimulation();

    }

}
