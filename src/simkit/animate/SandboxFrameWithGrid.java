package simkit.animate;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import javax.swing.JLabel;

/**
 *
 * @author ahbuss
 */
public class SandboxFrameWithGrid extends SandboxFrame {

    private SandboxWithGrid sandboxWithGrid;
    private CoordinateListener coordinateListener;

    public SandboxFrameWithGrid() {
        super();
        this.sandboxWithGrid = new SandboxWithGrid();
        this.sandboxWithGrid.setSize(500, 500);
        this.sandboxWithGrid.setGridWidth(50);
        this.getContentPane().add(sandboxWithGrid, BorderLayout.CENTER);
        coordinateListener = new CoordinateListener();
        this.sandboxWithGrid.addMouseMotionListener(coordinateListener);
        getContentPane().add(coordinateListener, BorderLayout.SOUTH);
    }

    /**
     * @return the coordinateListener
     */
    public CoordinateListener getCoordinateListener() {
        return coordinateListener;
    }

    /**
     * @return the sandboxWithGrid
     */
    public SandboxWithGrid getSandboxWithGrid() {
        return sandboxWithGrid;
    }

    /**
     * @param sandboxWithGrid the sandboxWithGrid to set
     */
    public void setSandboxWithGrid(SandboxWithGrid sandboxWithGrid) {
        this.sandboxWithGrid = sandboxWithGrid;
    }

    private class CoordinateListener extends JLabel implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Point sandboxLocation = sandboxWithGrid.getLocationOnScreen();
            Point mouseLocation = e.getLocationOnScreen();
            Point2D origin = sandboxWithGrid.getOrigin();
            int x = mouseLocation.x - sandboxLocation.x;
            int y = mouseLocation.y - sandboxLocation.y;
            double trueX = (x - origin.getX()) * sandboxWithGrid.getScale();
            double trueY = (origin.getY() - y) * sandboxWithGrid.getScale();
            setText(String.format("(%.2f, %.2f) ", trueX, trueY));
        }

    }

}
