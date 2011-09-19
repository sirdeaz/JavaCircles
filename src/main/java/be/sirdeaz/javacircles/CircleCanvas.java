/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CircleCanvas.java
 *
 * Created on Aug 24, 2011, 8:09:54 PM
 */
package be.sirdeaz.javacircles;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JComponent;

/**
 * 
 * @author sirdeaz
 */
public class CircleCanvas extends JComponent implements PropertyChangeListener {

    private static final long serialVersionUID = 1L;
    // old values - only small change if canvas receives focus
    // public static final int OUTER_CIRCLE_MAX_EXTEND = 20;
    // public static final int OUTER_CIRCLE = 235;
    public static final int OUTER_CIRCLE_MAX_EXTEND = 110;
    public static final int OUTER_CIRCLE = 125;
    private static final int SMALL_CIRCLE = 50;
    private static final int INNER_CIRCLE = 125;
    private static final int START_ANGLE = 270;
    // properties
    public static final String PROP_FOCUS_RECEIVED = "CIRCLE_CANVAS_FOCUS_RECEIVED";
    public static final String PROP_FOCUS_LOST = "CIRCLE_CANVAS_FOCUS_LOST";
    public static final String PROP_DROP_RECEIVED = "CIRCLE_CANVAS_DROP_RECEIVED";
    private final Shape basicShape = new Ellipse2D.Float(0, 0, SMALL_CIRCLE,
            SMALL_CIRCLE);
    private final CircleModel model;
    private final String title;
    private int outerCircleRadius;
    private final Font myFont = new Font("verdana", Font.BOLD, 12);

    private InnerCircleLabelGenerator innerCircleLabelGenerator;
    private Color innerCircleColor;
    
    
    
    public CircleCanvas(String title, CircleModel model) {
        this(title, model, new DefaultInnerCircleLabelGenerator());
    }
    
    public CircleCanvas(String title, CircleModel model, InnerCircleLabelGenerator innerCircleLabelGenerator) {
        initComponents();
        setOpaque(true);
        this.model = model;
        this.model.addPropertyChangeListener(this);
        this.title = title;
        this.innerCircleLabelGenerator = innerCircleLabelGenerator;
        this.outerCircleRadius = OUTER_CIRCLE;
        this.innerCircleColor = ColorSetup.getInstance().getInnerCircleColor();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250, 250);
    }
    
    public InnerCircleLabelGenerator getInnerCircleLabelGenerator() {
        return innerCircleLabelGenerator;
    }

    public void setInnerCircleLabelGenerator(InnerCircleLabelGenerator innerCircleLabelGenerator) {
        this.innerCircleLabelGenerator = innerCircleLabelGenerator;
    }

    public void setInnerCircleColor(Color innerCircleColor) {
        this.innerCircleColor = innerCircleColor;
    }

    public Color getInnerCircleColor() {
        return innerCircleColor;
    }
    
    public static int getStartAngle() {
        return START_ANGLE;
    }

    public int getOuterCircleRadius() {
        return outerCircleRadius;
    }

    public CircleModel getModel() {
        return this.model;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isInCircle(Point p) {
        return getOuterCircleShape(0).contains(p);
    }

    public Shape getOuterCircleShape(int delta) {
        int x = (getWidth() / 2) - outerCircleRadius / 2;
        int y = (getHeight() / 2) - outerCircleRadius / 2;
        return new Ellipse2D.Double(x, y, outerCircleRadius - delta,
                outerCircleRadius - delta);
    }

    public Shape getTransformedShape(Circle circle) {
        AffineTransform t = new AffineTransform();
        t.translate(getWidth() / 2, getHeight() / 2);
        t.rotate(Math.toRadians(circle.getDegrees()));
        t.translate((INNER_CIRCLE / 2) + 1, (-SMALL_CIRCLE / 2) + 1);
        return t.createTransformedShape(this.basicShape);
    }

    public Shape getHoveredShape(Point p) {
        List<Circle> circles = model.getCircles();
        for (Circle circle : circles) {
            if (getTransformedShape(circle).contains(p)) {
                return basicShape;
            }
        }

        return null;
    }

    public Circle getSelectedCircle(Point p) {
        List<Circle> circles = model.getCircles();
        for (Circle circle : circles) {
            if (getTransformedShape(circle).contains(p)) {
                return circle;
            }
        }

        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    /**
     * Draws the circles. Soft clipping hack used:
     * http://weblogs.java.net/blog/campbell
     * /archive/2006/07/java_2d_tricker.html
     * 
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Clear the background to black
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        // Create a translucent intermediate image in which we can perform
        // the soft clipping
        GraphicsConfiguration gc = ((Graphics2D) g).getDeviceConfiguration();
        BufferedImage img = gc.createCompatibleImage(getWidth(), getHeight(),
                Transparency.TRANSLUCENT);
        Graphics2D g2 = img.createGraphics();

        // Clear the image so all pixels have zero alpha
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Render our clip shape into the image. Note that we enable
        // antialiasing to achieve the soft clipping effect. Try
        // commenting out the line that enables antialiasing, and
        // you will see that you end up with the usual hard clipping.
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(getOuterCircleShape(0));

        // Here's the trick... We use SrcAtop, which effectively uses the
        // alpha value as a coverage value for each pixel stored in the
        // destination. For the areas outside our clip shape, the destination
        // alpha will be zero, so nothing is rendered in those areas. For
        // the areas inside our clip shape, the destination alpha will be fully
        // opaque, so the full color is rendered. At the edges, the original
        // antialiasing is carried over to give us the desired soft clipping
        // effect.
        g2.setComposite(AlphaComposite.SrcAtop);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // MultipleGradientPaint outerGradientPaint =
        // new RadialGradientPaint(new Point2D.Double(getWidth() / 2,
        // getHeight() / 2),
        // outerCircleRadius,
        // new float[]{0.0f, 1.0f},
        // new Color[]{Color.darkGray, Color.white});
        // //new Color[]{ColorSetup.getInstance().getOuterCircleColor(),
        // // new Color(0, 0, 0, 0)});

        Point2D center = new Point2D.Float(getWidth() / 2, getWidth() / 2);
        RadialGradientPaint outerGradientPaint = new RadialGradientPaint(
                center, outerCircleRadius, new float[]{0.0f, 1.0f},
                new Color[]{Color.darkGray, Color.white});
        g2.setPaint(outerGradientPaint);
        drawCircle(g2, outerCircleRadius, outerCircleRadius);

        RadialGradientPaint innerCircleGradient = new RadialGradientPaint(
                center, INNER_CIRCLE, new float[]{0.0f, 1.0f},
                new Color[]{this.innerCircleColor, Color.DARK_GRAY});

        g2.setPaint(innerCircleGradient);
        drawCircle(g2, INNER_CIRCLE, INNER_CIRCLE, getTitle());

        drawSmallCircles(g2);

        g2.dispose();

        // Copy our intermediate image to the screen
        g.drawImage(img, 0, 0, null);
    }

    public void drawCircle(Graphics2D g2, int width, int height, String text) {
        drawCircle(g2, width, height);
        drawInnerCircleText(g2, width, height, this.innerCircleLabelGenerator.generateLabel(this));
    }
    
    public void drawInnerCircleText(Graphics2D g2, int width, int height, String text) {
        int y = (getHeight() / 2) - (getInnerCircleLabelHeight(g2, text)/4);
        FontMetrics m = getFontMetrics(this.myFont);
        g2.setFont(myFont);
        g2.setPaint(Color.black);
        for (String entry : text.split("\\n")) {
            Rectangle2D textRect = m.getStringBounds(entry, g2);
            int x = (int) ((getWidth() / 2) - (textRect.getWidth() / 2));
            g2.drawString(entry, x, y);
            y += textRect.getHeight();
        }
    }
    
    /**
     * Calculates the total height of the provided text. 
     * Newline chars will create additional lines.
     * @param g2
     * @param text
     * @return 
     */
    private int getInnerCircleLabelHeight(Graphics2D g2, String text) {
        FontMetrics m = getFontMetrics(this.myFont);
        int height = 0;
        for (String entry : text.split("\\n")) {
            Rectangle2D textRect = m.getStringBounds(entry, g2);
            height += textRect.getHeight();
        }
        
        return height;
    }

    public void drawCircle(Graphics2D g2, int width, int height) {
        int x = (getWidth() / 2) - width / 2;
        int y = (getHeight() / 2) - height / 2;

        g2.fillOval(x, y, width, height);
        g2.setPaint(Color.black);
        g2.setStroke(new BasicStroke(2.0f));
        g2.drawOval(x, y, width - 1, height - 1);
    }

    public void drawSmallCircles(Graphics2D g2) {
        for (Circle circle : this.model.getCircles()) {
            Shape s = getTransformedShape(circle);
            g2.setPaint(circle.getColor());
            g2.fill(s);

            Rectangle shapeRect = s.getBounds();
            String text = "" + circle.getText();
            FontMetrics m = getFontMetrics(myFont);
            Rectangle2D textRect = m.getStringBounds(text, g2);
            g2.setPaint(Color.black);

            g2.drawString(
                    text,
                    (float) (shapeRect.getCenterX() - (textRect.getWidth() / 2)),
                    (float) (shapeRect.getCenterY()));
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(CircleModel.PROP_MODEL_CHANGED)) {
            repaint();
        }
    }

    public void fireFocusReceived() {
        firePropertyChange(PROP_FOCUS_RECEIVED, null, null);
    }

    public void fireFocusLost() {
        firePropertyChange(PROP_FOCUS_LOST, null, null);
    }

    public void extendOuterCircleRadius(int extend) {
        this.outerCircleRadius = OUTER_CIRCLE + extend;
        repaint();
    }

    public boolean isFullyStretched() {
        return getOuterCircleRadius() == (OUTER_CIRCLE + OUTER_CIRCLE_MAX_EXTEND);
    }
}