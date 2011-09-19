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
    private static final int STEP = 35;
   
    private final CircleModel model;
    
    public SmallCircleAdapter(CircleModel model) {
        this.model = model;
    }

    @Override
    public void begin(Animator source) {

        for (int i = 0; i < this.model.getCircleCount(); i++) {
            Circle taskCircle = this.model.getCircleAt(i);
            if (taskCircle.getDegrees() != calculateTargetPosition(i)) {
                addAnimation(new MovingCircleAnimation(taskCircle, calculateTargetPosition(i)));
            }
        }
    }

    /**
     * Calculates the target position the circle should reside at based up the position within the list
     * @param index in the list
     * @return the Calculated position based upon the position within the list
     */
    private int calculateTargetPosition(int i) {
        return MAX_DEGREES + (STEP * i);
    }

    @Override
    public void progress(double fraction) {
        this.model.fireModelChanged();
    }

}
