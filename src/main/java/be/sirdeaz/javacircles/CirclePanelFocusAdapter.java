package be.sirdeaz.javacircles;

import org.jdesktop.core.animation.timing.Animator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fdidd
 */
class CirclePanelFocusAdapter extends AbstractTimingTargetAdapter {

    public final CircleCanvas circleCanvas;

    public CirclePanelFocusAdapter(CircleCanvas circleCanvas) {
        this.circleCanvas = circleCanvas;
    }

    @Override
    public void begin(Animator source) {
        addAnimation(new CircleSizeAnimation(circleCanvas));
    }

    @Override
    public void progress(double fraction) {
        
    }

}
