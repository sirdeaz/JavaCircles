/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.sirdeaz.javacircles;

import org.jdesktop.core.animation.timing.Animator;

/**
 * 
 * @author fdidd
 */
class SmallCircleAdapter extends AbstractTimingTargetAdapter {

	private static final int MAX_DEGREES = -45;
	private final CircleModel model;

	public SmallCircleAdapter(CircleModel model) {
		this.model = model;
	}

	@Override
	public void begin(Animator source) {

		for (int i = 0; i < this.model.getCircleCount(); i++) {
			Circle circle = this.model.getCircles().get(i);

			if (circle != null) {
				CirclePosition targetPosition = calculateTargetPosition(i);
				if (!circle.getPosition().equals(targetPosition)) {
					addAnimation(new MovingCircleAnimation(circle,
							targetPosition));
				}
			}
		}
	}

	/**
	 * Calculates the target position the circle should reside at based up the
	 * position within the list
	 * 
	 * @param index
	 *            in the list
	 * @return the Calculated position based upon the position within the list
	 */
	private CirclePosition calculateTargetPosition(int i) {
		int layer = getLayer(i);
		double step = 360.0 / getCirclesPerLayer(layer);
		int currentIndex = calculateStartOfLayerIndex(i, layer);
		double degrees = MAX_DEGREES + (step * (currentIndex));
		//System.out.printf("index(%d) -- step(%f) -- degrees(%f)%n", i, step, degrees);
		return new CirclePosition(layer, degrees);
	}

	/**
	 * Calculates the quanity of circles / layer
	 * @param layer
	 * @return 
	 */
	public static int getCirclesPerLayer(int layer) {
		return (int) (3.0 / 4 * Math.pow(2, layer + 4));
	}

	/**
	 * Calculates the current layer based on the provided index
	 * @return 
	 */
	public static int getLayer(int index) {
		return (int) log2(((4.0 / 3) * (index + 12))) - 4;
	}

	public static double log2(double num) {
		return (Math.log(num) / Math.log(2));
	}

	public static int calculateStartOfLayerIndex(int index, int layer) {
		int newStartIndex = 0;
		
		if (layer > 0) {
			int circleCount = 0;
			for (int i = 0; i < layer; i++) {
				circleCount += getCirclesPerLayer(i);
			}
			newStartIndex = index - circleCount;
		} else {
			newStartIndex = index;
		}
		
		//System.out.printf("index (%d), layer (%d), new index(%d)%n", index, layer, newStartIndex);
		
		return newStartIndex;
	}

	@Override
	public void progress(double fraction) {
		this.model.fireModelChanged();
	}
}
