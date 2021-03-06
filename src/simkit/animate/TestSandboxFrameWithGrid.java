package simkit.animate;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.Icon;
import simkit.actions.visual.ShapeIcon;
import simkit.smd.BasicLinearMover;
import simkit.smd.CookieCutterSensor;
import simkit.smd.Mover;
import simkit.smd.Sensor;
import simkit.smd.WayPoint;

/**
 *
 * @author ahbuss
 */
public class TestSandboxFrameWithGrid {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SandboxFrameWithGrid frame = new SandboxFrameWithGrid();
        SandboxWithGrid sandbox = frame.getSandboxWithGrid();
        sandbox.setOpaque(true);
        sandbox.setSize(1000, 1000);
//        sandbox.setScale(0.2);
        sandbox.setBackground(Color.WHITE);
        frame.setSize(sandbox.getWidth(), sandbox.getHeight());
        sandbox.setDrawAxes(true);
        sandbox.setOrigin(new Point2D.Double(sandbox.getWidth() / 2, sandbox.getHeight() / 2));

        Mover mover = new BasicLinearMover("SensorMover", new Point2D.Double(250.0, 125.0), 0);
        Sensor sensor = new CookieCutterSensor(mover, 75.0);
        frame.addSensor(sensor);
        Icon icon = new ShapeIcon(new Rectangle2D.Double(0, 0, 10, 10), Color.black, Color.BLUE, true);
        MoverIcon moverIcon = new MoverIcon(mover, icon);

        ShapeIcon footprint = new ShapeIcon(new Ellipse2D.Double(0, 0, 2.0 * sensor.getMaxRange(),
                2.0 * sensor.getMaxRange()));
        footprint.setFilled(false);
        SensorIcon sensorIcon = new SensorIcon(sensor, footprint);
        sensorIcon.setColor(Color.RED);
        sensorIcon.addMouseMotionListener(frame.getCoordinateListener());
        sandbox.add(sensorIcon);
        sandbox.add(moverIcon);
        
        Mover anotherMover = new BasicLinearMover(new Point2D.Double(50, 100), 0);
        Icon anotherIcon = new ShapeIcon(new Ellipse2D.Double(0, 0, 40, 40), Color.BLACK, Color.CYAN);
        MoverIcon anotherMoverIcon = new MoverIcon(anotherMover, anotherIcon);
        anotherMoverIcon.addMouseMotionListener(frame.getCoordinateListener());
        sandbox.add(anotherMoverIcon);
        
        WayPoint[] waypoints = new WayPoint[] {
            new WayPoint(new Point2D.Double(0.0, 0.0)),
            new WayPoint(new Point2D.Double(50.0, 60.0)),
        };
        for (WayPoint wp: waypoints) {
            Point2DIcon wpicon = new Point2DIcon(wp.getPoint(), Color.GRAY);
            wpicon.setOrigin(sandbox.getOrigin());
            wpicon.addMouseMotionListener(frame.getCoordinateListener());
            sandbox.add(wpicon);
        }
        
        frame.setVisible(true);
    }

}
