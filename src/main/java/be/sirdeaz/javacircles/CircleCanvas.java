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
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
	public static final int OUTER_CIRCLE_EXTEND = 90;
	public static final int OUTER_CIRCLE = 100;
	private static final int SMALL_CIRCLE = 35;
	private static final int INNER_CIRCLE = 100;
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
	private InnerCircleColorGenerator innerCircleColorGenerator = new DefaultInnerCircleColorGenerator();
	private boolean collapsible = true;
	private Stroke myStroke = new BasicStroke(2.0f);

	CircleCanvas(String title, CircleModel model) {
		this(title, model, new DefaultInnerCircleLabelGenerator());
	}

	CircleCanvas(String title, CircleModel model, InnerCircleLabelGenerator innerCircleLabelGenerator) {
		initComponents();
		setOpaque(true);
		this.model = model;
		this.model.addPropertyChangeListener(this);
		this.title = title;
		this.innerCircleLabelGenerator = innerCircleLabelGenerator;
		this.outerCircleRadius = OUTER_CIRCLE;
	}

	public int getExtendedWidth() {
	return getLayerCount() * OUTER_CIRCLE_EXTEND;
	}

	public int getLayerCount() {
		//return (this.model.getCircleCount() / 10) + 1;
		return SmallCircleAdapter.getLayer(this.model.getCircleCount()) + 1;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(OUTER_CIRCLE + getExtendedWidth(), OUTER_CIRCLE + getExtendedWidth());
	}

	public boolean isCollapsible() {
		return collapsible;
	}

	public void setCollapsible(boolean collapsible) {
		this.collapsible = collapsible;
	}

	@Override
	public int getWidth() {
		return getPreferredSize().width;
	}

	@Override
	public int getHeight() {
		return getPreferredSize().height;
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getSize() {
		return getPreferredSize();
	}

	public InnerCircleLabelGenerator getInnerCircleLabelGenerator() {
		return innerCircleLabelGenerator;
	}

	public void setInnerCircleLabelGenerator(InnerCircleLabelGenerator innerCircleLabelGenerator) {
		this.innerCircleLabelGenerator = innerCircleLabelGenerator;
	}

	public InnerCircleColorGenerator getInnerCircleColorGenerator() {
		return innerCircleColorGenerator;
	}

	public void setInnerCircleColorGenerator(InnerCircleColorGenerator innerCircleColorGenerator) {
		this.innerCircleColorGenerator = innerCircleColorGenerator;
	}

	public static int getStartAngle() {
		return START_ANGLE;
	}

	public int getOuterCircleRadius() {
		if (isCollapsible()) {
			return outerCircleRadius;
		} else {
			return OUTER_CIRCLE + getExtendedWidth();
		}
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
		int x = (getWidth() / 2) - getOuterCircleRadius() / 2;
		int y = (getHeight() / 2) - getOuterCircleRadius() / 2;
		return new Ellipse2D.Double(x, y, getOuterCircleRadius() - delta,
				getOuterCircleRadius() - delta);
	}

	private Shape getTransformedShape(Circle circle) {
		AffineTransform t = new AffineTransform();
		t.translate(getWidth() / 2, getHeight() / 2);
		t.rotate(Math.toRadians(circle.getPosition().getDegrees()));
		t.translate((INNER_CIRCLE / 2) + (SMALL_CIRCLE * circle.getPosition().getLayer()) + 1, (-SMALL_CIRCLE / 2) + 1);
		return t.createTransformedShape(this.basicShape);
	}

	public Shape getHoveredShape(Point p) {
		for (Circle circle : model.getCircles()) {
			// added non draggable support!
			if (getTransformedShape(circle).contains(p) && circle.isDraggable()) {
				return basicShape;
			}
		}

		return null;
	}

	public Circle getSelectedCircle(Point p) {
		for (Circle circle : model.getCircles()) {
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
	 * http://weblogs.java.net/blog/campbell/archive/2006/07/java_2d_tricker.html
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// clears the background
		if (CircleManager.getInstance().isClearCircleBackground()) {
			g.clearRect(0, 0, getWidth(), getHeight());
		}
		// Create a translucent intermediate image in which we can perform
		// the soft clipping
		GraphicsConfiguration gc = ((Graphics2D) g).getDeviceConfiguration();
		BufferedImage img = gc.createCompatibleImage(getWidth(), getHeight(),
				Transparency.TRANSLUCENT);
		Graphics2D g2 = img.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// Clear the image so all pixels have zero alpha
		g2.setComposite(AlphaComposite.Clear);
		g2.fillRect(0, 0, getWidth(), getHeight());

		// Render our clip shape into the image. Note that we enable
		// antialiasing to achieve the soft clipping effect. Try
		// commenting out the line that enables antialiasing, and
		// you will see that you end up with the usual hard clipping.
		g2.setComposite(AlphaComposite.Src);
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

		Point2D center = new Point2D.Float(getWidth() / 2, getWidth() / 2);
		RadialGradientPaint outerGradientPaint = new RadialGradientPaint(
				center, getOuterCircleRadius(), new float[]{0.0f, 1.0f},
				new Color[]{Color.darkGray, Color.white});
		g2.setPaint(outerGradientPaint);
		drawCircle(g2, getOuterCircleRadius(), getOuterCircleRadius());

		RadialGradientPaint innerCircleGradient = new RadialGradientPaint(
				center, INNER_CIRCLE, new float[]{0.0f, 1.0f},
				new Color[]{this.innerCircleColorGenerator.getColor(this), Color.DARK_GRAY});

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
		int y = (getHeight() / 2) - (getInnerCircleLabelHeight(g2, text) / 4);
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
		g2.setStroke(myStroke);
		g2.drawOval(x, y, width - 1, height - 1);
	}

	public void drawSmallCircles(Graphics2D g2) {
		for (Circle circle : this.model.getCircles()) {
			if (circle.isVisible()) {
				Shape s = getTransformedShape(circle);
				g2.setPaint(circle.getColor());
				g2.fill(s);

				// draw an outline if an outline color is set.
				if (circle.getOutlineColor() != null) {
					g2.setColor(circle.getOutlineColor());
					g2.draw(s);
				}

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
		return getOuterCircleRadius() == (OUTER_CIRCLE + getExtendedWidth());
	}
}
