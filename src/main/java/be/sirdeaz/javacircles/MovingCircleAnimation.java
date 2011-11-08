/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package be.sirdeaz.javacircles;

/**
 *
 * @author fdidd
 */
 class MovingCircleAnimation implements BasicAnimation {

    private final Circle circle;
    private final CirclePosition initialPosition;
    private final CirclePosition targetPosition;

    public MovingCircleAnimation(Circle circle, CirclePosition position) {
        this.circle = circle;
        this.initialPosition = circle.getPosition();
        this.targetPosition = position;
    }

    @Override
    public void doMove(double fraction) {
		double newDegrees = this.initialPosition.getDegrees() - (fraction*(initialPosition.getDegrees()-targetPosition.getDegrees()));
		CirclePosition position = new CirclePosition(targetPosition.getLayer(), newDegrees);
        circle.setPosition(position);
    }
}
