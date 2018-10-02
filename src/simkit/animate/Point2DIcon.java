package simkit.animate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import javax.swing.Icon;
import javax.swing.JComponent;
import simkit.actions.visual.ShapeIcon;

/**
 *
 * @author ahbuss
 */
public class Point2DIcon extends JComponent {
    private Point2D point;
    private Color color;
    private Point2D origin;
    private Icon icon;

    public Point2DIcon(Point2D point, Color color) {
        this.setPoint(point);
        this.setColor(color);
//        this.setOrigin(new Point2D.Double(0.0, 0.0));
        this.setIcon(new ShapeIcon(new Ellipse2D.Double(0, 0, 50, 50), color, color, true));
    }
    
    public Point2DIcon(Point2D point) {
        this(point, Color.BLACK);
    }
    
    public Point2DIcon() {
        this(new Point2D.Double(0.0, 0.0), Color.BLACK);
    }
    
    public Point2D getScreenLocation() {
        Point2D screenLocation = new Point2D.Double(
                origin.getX() + point.getX(),
                origin.getY() - point.getY()
        );
        return screenLocation;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        Point2D location = getScreenLocation();
        setBounds((int) (location.getX() - icon.getIconWidth() * 0.5),
                 (int)(location.getY() - icon.getIconHeight() * 0.5), 
                 icon.getIconWidth(), icon.getIconHeight());
        icon.paintIcon(this, g, 0, 0);
    }
    
    /**
     * @return the point
     */
    public Point2D getPoint() {
        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(Point2D point) {
        this.point = point;
        this.setToolTipText(String.format("(%.2f,%.2f)", point.getX(), point.getY()));
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the origin
     */
    public Point2D getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(Point2D origin) {
        this.origin = origin;
    }

    /**
     * @return the icon
     */
    public Icon getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    
    
}
