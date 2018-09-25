package simkit.animate;

import java.awt.BorderLayout;
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

    public SandboxFrameWithGrid() {
        super();
        this.sandboxWithGrid = new SandboxWithGrid();
        this.sandboxWithGrid.setSize(500, 500);
        this.sandboxWithGrid.setGridWidth(50);
        this.getContentPane().add(sandboxWithGrid, BorderLayout.CENTER);
        CoordinateListener coordinateListener = new CoordinateListener();
        this.sandboxWithGrid.addMouseMotionListener(coordinateListener);
        getContentPane().add(coordinateListener, BorderLayout.SOUTH);
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
            int x = e.getX();
            int y = e.getY();
            Point2D origin = sandboxWithGrid.getOrigin();
            double trueX = (x - origin.getX()) * sandboxWithGrid.getScale();
            double trueY = (origin.getY() - y) * sandboxWithGrid.getScale();
            setText(String.format("(%.2f, %.2f)", trueX, trueY));
        }
        
    }
    
}
