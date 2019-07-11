package simkit.animate;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * How to put a background image in a Sandbox.
 *
 * @author ahbuss
 */
public class TestBackgroundImage {

    private static final Logger LOGGER = Logger.getLogger(TestBackgroundImage.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        The argument should point to the desired image on the filesystem. In this
//        instance, the image file was in src/images (in the Netbeasn project).
//        The exact placement depends of course.
        URL url
                = TestBackgroundImage.class.getResource("../../images/monterey-peninsula-map-max.jpg");
//        This was just to verify that the image file had been found -
//        it outputs null if not found
        System.out.println(url);
        try {
//            Create the Image instance
            Image image = ImageIO.read(url);
            SandboxFrame sandboxFrame = new SandboxFrame("Test Background Image");
//            Grab the Sandbox instance
            Sandbox sandbox = sandboxFrame.getSandbox();
//            Set the background to the Image
            sandbox.setBackroundImage(image);
//            This is optional - a huge image may produce weird results!
            sandboxFrame.setSize(image.getWidth(sandbox), image.getWidth(sandbox));
            sandbox.setOrigin(new Point2D.Double(image.getWidth(sandbox) / 2, image.getHeight(sandbox) /2));
            sandbox.setDrawAxes(true);
            
            sandboxFrame.setVisible(true);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }
}
