package be.sirdeaz.ghostdragndrop;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;

import javax.swing.SwingUtilities;

public class GhostMotionAdapter extends MouseMotionAdapter {

    private final GhostGlassPane glassPane;
    private final JComponent targetComponent;

    public GhostMotionAdapter(GhostGlassPane glassPane, JComponent targetComponent) {
        this.glassPane = glassPane;
        this.targetComponent = targetComponent;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (System.currentTimeMillis() % 2 == 0) {
            Component c = e.getComponent();

            Point p = (Point) e.getPoint().clone();
            SwingUtilities.convertPointToScreen(p, c);
            SwingUtilities.convertPointFromScreen(p, glassPane);
            glassPane.setPoint(p);
            glassPane.repaint();

            Point dropPoint = e.getPoint();
            dropPoint = SwingUtilities.convertPoint(c, dropPoint.x, dropPoint.y, this.targetComponent);

            if (targetComponent.contains(dropPoint)) {

                Component target = SwingUtilities.getDeepestComponentAt(this.targetComponent, dropPoint.x, dropPoint.y);
                System.out.println(target);
                if (target != null) { //be safe
                    MouseEvent newEvent = SwingUtilities.convertMouseEvent(c, e, target);
                    this.glassPane.getToolkit().getSystemEventQueue().postEvent(newEvent);
                }

                e.consume(); // <- important
            }
        }
    }
}
