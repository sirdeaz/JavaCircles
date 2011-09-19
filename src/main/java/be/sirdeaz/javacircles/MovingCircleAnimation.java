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
    private final int initialDegrees;
    private final int targetDegrees;

    public MovingCircleAnimation(Circle circle, int targetDegrees) {
        this.circle = circle;
        this.initialDegrees = circle.getDegrees();
        this.targetDegrees = targetDegrees;
    }

    @Override
    public void doMove(double fraction) {
        circle.setDegrees(this.initialDegrees - (int)(fraction*(initialDegrees-targetDegrees)));
    }
}
