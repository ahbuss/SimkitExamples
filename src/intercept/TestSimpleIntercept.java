package intercept;

import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 *
 * @author ahbuss
 */
public class TestSimpleIntercept {

    private static final Logger LOGGER = Logger.getLogger(TestSimpleIntercept.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Point2D targetLocation = new Point2D.Double(10.0, 20.0);
        Point2D targetVelocity = new Point2D.Double(15.0, -10.0);

        Point2D seekerLocation = new Point2D.Double(0.0, -30.0);
        double seekerSpeed = 25;

        double targetVelocitySquared = targetLocation.distanceSq(0.0, 0.0);

        Point2D relativeLocation = new Point2D.Double(targetLocation.getX() - seekerLocation.getX(),
                targetLocation.getY() - seekerLocation.getY());

        double a = seekerSpeed * seekerSpeed - targetVelocitySquared;
        double b = relativeLocation.getX() * targetVelocity.getX() + relativeLocation.getY() * targetVelocity.getY();
        double c = relativeLocation.distanceSq(0.0, 0.0);

        double minimumSpeedToIntercept
                = sqrt(targetVelocitySquared + b * b/ c);
        System.out.printf("Min speed to intercept target: %,.4f%n", minimumSpeedToIntercept);

        double discriminant = a * c - b * b;
        if (discriminant < 0.0) {
            System.out.printf("No intercept possible; discriminant = %,.4f%n", discriminant);
            return;
        } else {
            System.out.printf("Intercept possible; discriminant = %,.4f%n", discriminant);
        }

        double first = b / a;
        double second = sqrt(discriminant) / a;

        System.out.printf("Roots: (%,.4f, %,.4f)%n", first - second, first + second);

        double t = first + second;

        Point2D seekerVelocity = new Point2D.Double(
                targetVelocity.getX() + relativeLocation.getX() / t,
                targetVelocity.getY() + relativeLocation.getY() / t
        );
        System.out.printf("Seeker velocity: %s%n", seekerVelocity);

        Point2D targetAtIntercept = new Point2D.Double(
                targetLocation.getX() + t * targetVelocity.getX(),
                targetLocation.getY() + t * targetVelocity.getY()
        );

        System.out.printf("Target at intercept: %s%n", targetAtIntercept);

        Point2D seekerAtIntercept = new Point2D.Double(
                seekerLocation.getX() + t * seekerVelocity.getX(),
                seekerLocation.getY() + t * seekerVelocity.getY()
        );
        System.out.printf("Seeker at intercept: %s%n", seekerAtIntercept);
        
        double[] eqn = new double[] {-c, -2*b, a};
        double[] res = new double[2];
        int result = QuadCurve2D.solveQuadratic(eqn, res);
        System.out.println(result);
        System.out.println(Arrays.toString(eqn));
        System.out.println(Arrays.toString(res));
    }

}
