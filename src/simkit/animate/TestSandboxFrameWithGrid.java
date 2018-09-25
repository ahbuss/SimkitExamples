package simkit.animate;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 *
 * @author ahbuss
 */
public class TestSandboxFrameWithGrid {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SandboxFrameWithGrid sbfwg = new SandboxFrameWithGrid();
        SandboxWithGrid sandbox = sbfwg.getSandboxWithGrid();
        sandbox.setOpaque(true);
        sandbox.setSize(800, 800);
        sandbox.setBackground(Color.WHITE);
        sbfwg.setSize(sandbox.getWidth(), sandbox.getHeight());
        sandbox.setDrawAxes(true);
        sandbox.setOrigin(new Point2D.Double(sandbox.getWidth() / 2, sandbox.getHeight() / 2));
        sbfwg.setVisible(true);
    }

}
