/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package be.sirdeaz.javacircles;

import java.util.ArrayList;
import java.util.List;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;

/**
 *
 * @author fdidd
 */
abstract class AbstractTimingTargetAdapter extends TimingTargetAdapter {

    private List<BasicAnimation> animations = new ArrayList<BasicAnimation>();

    public AbstractTimingTargetAdapter() {
    }

    /**
     * Provides a hook to initialize the animations
     * @param source
     */
    @Override
    public void begin(Animator source) {
    }

    @Override
    public final void timingEvent(Animator source, double fraction) {
        for (BasicAnimation animation : animations) {
            animation.doMove(fraction);
        }
        progress(fraction);
    }

    @Override
    public final void end(Animator source) {
       this.animations.clear();
    }

    public final void addAnimation(BasicAnimation animation) {
        this.animations.add(animation);
    }

    public abstract void progress(double fraction);
}
