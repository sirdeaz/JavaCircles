package be.sirdeaz.ghostdragndrop;



import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;


 abstract class AbstractGhostDropManager implements GhostDropListener {
	protected JComponent component;

	public AbstractGhostDropManager() {
		this(null);
	}
	
	public AbstractGhostDropManager(JComponent component) {
		this.component = component;
	}

	protected Point getTranslatedPoint(Point point) {
        Point p = (Point) point.clone();
        SwingUtilities.convertPointFromScreen(p, component);
		return p;
	}

	protected boolean isInTarget(Point point) {
		Rectangle bounds = component.getBounds();
		return bounds.contains(point);
	}

    @Override
	public void ghostDropped(GhostDropEvent e) {
	}
}