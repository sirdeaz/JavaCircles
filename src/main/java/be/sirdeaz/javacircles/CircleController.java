/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.sirdeaz.javacircles;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.TimeUnit;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.Animator.Direction;
import org.jdesktop.core.animation.timing.AnimatorBuilder;
import org.jdesktop.core.animation.timing.interpolators.AccelerationInterpolator;

/**
 * 
 * @author fdidd
 */
public class CircleController implements PropertyChangeListener {

	private final CircleCanvas circleCanvas;
	private final Animator movingCircleAnimator;
	private final Animator circleSizeAnimator;

	public CircleController(CircleCanvas circleCanvas) {
		this.circleCanvas = circleCanvas;
		this.circleCanvas.addPropertyChangeListener(this);
		this.circleCanvas.getModel().addPropertyChangeListener(this);
		
		movingCircleAnimator = new AnimatorBuilder(CircleManager.getInstance().getTimingSource())
				.addTarget(new SmallCircleAdapter(circleCanvas.getModel()))
				.setDuration(500, TimeUnit.MILLISECONDS)
				.setInterpolator(new AccelerationInterpolator(0.5, 0.5))
				.build();
		circleSizeAnimator = new AnimatorBuilder(CircleManager.getInstance().getTimingSource())
				.addTarget(new CirclePanelFocusAdapter(this.circleCanvas))
				.setDuration(300, TimeUnit.MILLISECONDS)
				.setInterpolator(new AccelerationInterpolator(0.2, 0.3))
				.build();
	}

	// @Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(CircleModel.PROP_CIRCLE_ADDED)
				|| evt.getPropertyName()
						.equals(CircleModel.PROP_CIRCLE_REMOVED)) {
			circlesChanged();
		} else if (evt.getPropertyName().equals(CircleCanvas.PROP_FOCUS_LOST)) {
			focusLost();
		} else if (evt.getPropertyName().equals(
				CircleCanvas.PROP_FOCUS_RECEIVED)) {
			focusReceived();
		}
	}

	public void focusReceived() {
		if (!this.circleSizeAnimator.isRunning()
				&& !this.circleCanvas.isFullyStretched()) {
			this.circleSizeAnimator.start();
		} else if (this.circleSizeAnimator.isRunning()
				&& this.circleSizeAnimator.getCurrentDirection() == Direction.BACKWARD) {
			this.circleSizeAnimator.reverseNow();
		}
	}

	public void focusLost() {
		if (!this.circleSizeAnimator.isRunning()
				&& this.circleCanvas.getOuterCircleRadius() > CircleCanvas.OUTER_CIRCLE) {
			this.circleSizeAnimator.startReverse();
		} else if (this.circleSizeAnimator.isRunning()
				&& this.circleSizeAnimator.getCurrentDirection() == Direction.FORWARD) {
			this.circleSizeAnimator.reverseNow();
		}
	}

	public void dropReceived() {
	}

	public CircleCanvas getCircleCanvas() {
		return this.circleCanvas;
	}

	public void circlesChanged() {
		this.movingCircleAnimator.stop();
		this.movingCircleAnimator.start();
                focusReceived();
	}
        
        public void setInnerCircleColor(Color c) {
            this.circleCanvas.setInnerCircleColor(c);
        }
        
        public Color getInnerCircleColor() {
            return this.circleCanvas.getInnerCircleColor();
        }
}