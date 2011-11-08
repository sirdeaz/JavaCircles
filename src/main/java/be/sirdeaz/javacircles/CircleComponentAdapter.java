/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.sirdeaz.javacircles;

import be.sirdeaz.ghostdragndrop.GhostDropAdapter;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

/**
 * 
 * @author fdidd
 */
class CircleComponentAdapter extends GhostDropAdapter {

    private final CircleCanvas circleCanvas;
    private CircleCanvas dropTarget;
    private Circle selectedCircle;

    public CircleComponentAdapter(CircleCanvas circleCanvas,
            MyGlassPane glassPane, String action) {
        super(glassPane, action);
        this.circleCanvas = circleCanvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component c = e.getComponent();

        if (!(c instanceof CircleCanvas)) {
            throw new IllegalStateException("Invalid component");
        }

        Point p = (Point) e.getPoint().clone();

        Shape shape = this.circleCanvas.getHoveredShape(p);
        if (shape != null) {
            this.selectedCircle = this.circleCanvas.getSelectedCircle(p);
            BufferedImage image = new BufferedImage((int) shape.getBounds().getWidth(), (int) shape.getBounds().getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) image.getGraphics();
            g.setColor(ColorSetup.getInstance().getSmallCircleColor());
            g.fill(shape);

            glassPane.setVisible(true);

            SwingUtilities.convertPointToScreen(p, c);
            SwingUtilities.convertPointFromScreen(p, glassPane);

            glassPane.setPoint(p);
            glassPane.setImage(image);
            glassPane.repaint();
        }
    }

    public void reset() {
        this.dropTarget = null;
        this.selectedCircle = null;
        ((MyGlassPane) this.glassPane).setMode(MyGlassPane.Mode.NONE);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.selectedCircle != null) {
            Component c = e.getComponent();

            Point p = (Point) e.getPoint().clone();
            SwingUtilities.convertPointToScreen(p, c);

            Point eventPoint = (Point) p.clone();
            SwingUtilities.convertPointFromScreen(p, glassPane);

            glassPane.setPoint(p);
            glassPane.setVisible(false);
            glassPane.setImage(null);

            fireGhostDropEvent(new CircleGhostDropEvent(action, eventPoint,
                    this.circleCanvas, this.selectedCircle, this.dropTarget));
        }

        reset();
    }

    private void updateFocusedComponents(MouseEvent e) {
        Component c = e.getComponent();
        Point dropPoint = (Point) e.getPoint().clone();
        dropPoint = SwingUtilities.convertPoint(c, dropPoint.x, dropPoint.y,
                getTopParent(this.circleCanvas));
        Component target = getDeepestComponentAt(dropPoint);
        CircleCanvas targetCanvas = null;
        if (target != null && target instanceof CircleCanvas) {
            targetCanvas = (CircleCanvas) target;
			
            Point componentPoint = SwingUtilities.convertPoint(
                    getTopParent(this.circleCanvas), dropPoint, targetCanvas);
            if (targetCanvas.isInCircle(componentPoint)) {
                ((MyGlassPane) this.glassPane).setMode(MyGlassPane.Mode.ADD);
                this.dropTarget = targetCanvas;
                this.dropTarget.fireFocusReceived();
            } else {
                ((MyGlassPane) this.glassPane).setMode(MyGlassPane.Mode.REMOVE);
                this.dropTarget = null;
                resetFocusedCanvases(getTopParent(this.circleCanvas));
            }
        } else {
            resetFocusedCanvases(getTopParent(this.circleCanvas));
        }
    }

    private Component getDeepestComponentAt(Point dropPoint) {
        boolean gpIsVisible = this.glassPane.isVisible();
        this.glassPane.setVisible(false);
        Component target = SwingUtilities.getDeepestComponentAt(
                getTopParent(this.circleCanvas), dropPoint.x, dropPoint.y);
        this.glassPane.setVisible(gpIsVisible);
        return target;
    }

    /**
     * Notifies all CircleCanvas objects contained with the provided CircleCanvas parent that they've 
     * lost their focus.
     * @param parent The parent container containing the CircleCanvas which should be notified
     */
    private void resetFocusedCanvases(Container parent) {
        for (int i = 0; i < parent.getComponentCount(); i++) {
            Component c = parent.getComponent(i);
            if (c instanceof CircleCanvas) {
                ((CircleCanvas) c).fireFocusLost();
            }
            if (c instanceof Container) {
                resetFocusedCanvases((Container) c);
            }
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateFocusedComponents(e);

        Point p = (Point) e.getPoint().clone();

        Circle circle = this.circleCanvas.getSelectedCircle(p);
        if (circle != null) {
            circleCanvas.setToolTipText(circle.getToolTip());
        } else {
            circleCanvas.setToolTipText(null);
        }
        
        //glassPane.setVisible(true);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        updateFocusedComponents(e);

        Component c = e.getComponent();

        Point p = (Point) e.getPoint().clone();
        SwingUtilities.convertPointToScreen(p, c);
        SwingUtilities.convertPointFromScreen(p, glassPane);
        glassPane.setPoint(p);
        glassPane.repaint();
    }

    private Container getTopParent(Component component) {
        Container parent = component.getParent();
        while (parent.getParent() != null) {
            parent = parent.getParent();
        }

        return parent;
    }
}
