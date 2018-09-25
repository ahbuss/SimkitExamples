package simkit.animate;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author ahbuss
 */
public class SandboxWithGrid extends Sandbox {

    private int gridWidth;
    
    private double scale;
    
    public SandboxWithGrid() {
        super();
        this.scale = 1.0;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int height = getHeight();
        int width = getWidth();

        ((Graphics2D) g).setStroke(new BasicStroke(2));
        g.setColor(Color.LIGHT_GRAY);
        for (int column = 0; column <= width; column += gridWidth) {
            g.drawLine(column, 0, column, height);
        }

        for (int row = 0; row <= height; row += gridWidth) {
            g.drawLine(0, row, width, row);
        }

        if (isDrawAxes()) {
            g.setColor(Color.BLACK);
            g.drawLine(0, (int) getOrigin().getY(), getWidth(),
                    (int) getOrigin().getY());
            g.drawLine((int) getOrigin().getX(), 0,
                    (int) getOrigin().getX(), getHeight());
        }

    }

    /**
     * @return the gridWidth
     */
    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * @param gridWidth the gridWidth to set
     */
    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    /**
     * @return the scale
     */
    public double getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

}
