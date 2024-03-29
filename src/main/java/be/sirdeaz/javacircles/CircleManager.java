/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.sirdeaz.javacircles;

import be.sirdeaz.ghostdragndrop.GhostDropEvent;
import be.sirdeaz.ghostdragndrop.GhostDropListener;
import be.sirdeaz.ghostdragndrop.GhostGlassPane;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ToolTipManager;
import org.jdesktop.core.animation.timing.TimingSource;
import org.jdesktop.swing.animation.timing.sources.SwingTimerTimingSource;

/**
 *
 * @author fdidd
 */
public class CircleManager implements GhostDropListener {

	private static final CircleManager INSTANCE = new CircleManager();
	private final MyGlassPane glassPane;
	private final List<DropListener<? extends Circle>> dropListeners = new ArrayList<DropListener<? extends Circle>>();
	private TimingSource ts;
	private boolean clearCircleBackground = true;

	private CircleManager() {
		glassPane = new MyGlassPane();
		ToolTipManager.sharedInstance().setInitialDelay(0);
	}

	/**
	 * Indicates that the circle background is cleared before repainted. In some occasions clearing the background causes 
	 * paint artifacts. Default value is true.
	 * @param clearCircleBackground 
	 */
	public void setClearCircleBackground(boolean clearCircleBackground) {
		this.clearCircleBackground = clearCircleBackground;
	}

	public boolean isClearCircleBackground() {
		return clearCircleBackground;
	}

	public TimingSource getTimingSource() {
		if (ts == null) {
			ts = new SwingTimerTimingSource();
			ts.init();
		}
		return ts;
	}

	public GhostGlassPane getGhostGlassPane() {
		return this.glassPane;
	}

	public static CircleManager getInstance() {
		return INSTANCE;
	}

	public synchronized <E extends Circle> void addDropListener(DropListener<E> listener) {
		this.dropListeners.add(listener);
	}

	public synchronized <E extends Circle> void removeDropListener(DropListener<E> listener) {
		this.dropListeners.remove(listener);
	}

	private CircleController createNewCircleController(CircleCanvas c) {
		CircleComponentAdapter cca = new CircleComponentAdapter(c, glassPane, c.getTitle());
		cca.addGhostDropListener(CircleManager.getInstance());
		c.addMouseListener(cca);
		c.addMouseMotionListener(cca);

		CircleController controller = new CircleController(c);
		// force an update on the model
		c.getModel().fireCircleAdded(null);
		return controller;
	}

	public CircleController createNewCircleController(String title,
			CircleModel circleModel,
			InnerCircleLabelGenerator innerCircleLabelGenerator,
			InnerCircleColorGenerator innerCircleColorGenerator) {
		CircleCanvas c = new CircleCanvas(title, circleModel);
		if (innerCircleColorGenerator != null) {
			c.setInnerCircleColorGenerator(innerCircleColorGenerator);
		}
		if (innerCircleLabelGenerator != null) {
			c.setInnerCircleLabelGenerator(innerCircleLabelGenerator);
		}
		return createNewCircleController(c);
	}

	public CircleController createNewCircleController(String title, CircleModel circleModel) {
		CircleCanvas c = new CircleCanvas(title, circleModel);
		return createNewCircleController(c);
	}

	public CircleController createNewCircleController(String title) {
		CircleCanvas c = new CircleCanvas(title, new DefaultCircleModel());
		return createNewCircleController(c);
	}

	@Override
	public void ghostDropped(GhostDropEvent e) {
		// if there's no target specified the circle can be removed
		// fetch the current circle
		Circle circle = ((CircleGhostDropEvent) e).getCircle();
		if (e.getTarget() == null) {
			doMoveWithoutTarget(circle, e);
		} else if (e instanceof CircleGhostDropEvent
				&& e.getTarget() instanceof CircleCanvas
				&& e.getSource() instanceof CircleCanvas) {
			// only initiate a move when the source and target differ
			if (e.getSource() != e.getTarget()) {
				CircleCanvas target = (CircleCanvas) e.getTarget();
				doMoveWithTarget(circle, target, e);
			}
		}
	}

	private void doMoveWithoutTarget(Circle circle, GhostDropEvent e) {
		if (fireDroppedWithoutTarget(circle)) {
			// remove the circle at the source canvas
			CircleCanvas source = (CircleCanvas) e.getSource();
			source.getModel().removeCircle(circle);
		}
	}

	private void doMoveWithTarget(Circle circle, CircleCanvas target, GhostDropEvent e) {
		if (fireDroppedWithTarget(circle, target)) {
			// fetch the current circle
			// remove the circle at the source canvas
			CircleCanvas source = (CircleCanvas) e.getSource();
			source.getModel().removeCircle(circle);
			// reset the position
			circle.setPosition(Circle.START_POSITION);
			// add it to the target canvas
			target.getModel().addCircle(circle);
		}
	}

	public boolean fireDroppedWithoutTarget(Circle circle) {
		boolean isValid = true;
		for (DropListener listener : dropListeners) {
			isValid = isValid && listener.droppedWithoutTarget(circle);
		}

		return isValid;
	}

	public boolean fireDroppedWithTarget(Circle circle, CircleCanvas target) {
		boolean isValid = true;
		for (DropListener listener : dropListeners) {
			isValid = isValid && listener.droppedWithTarget(circle, target);
		}

		return isValid;
	}
}
