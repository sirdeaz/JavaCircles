/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package be.sirdeaz.javacircles;

import be.sirdeaz.ghostdragndrop.GhostDropEvent;
import java.awt.Point;
import javax.swing.JComponent;

/**
 *
 * @author fdidd
 */
class CircleGhostDropEvent extends GhostDropEvent {

    private final Circle circle;
    
    public CircleGhostDropEvent(String action, Point point, JComponent source, Circle circle, JComponent target) {
        super(action, point, source, target);
        this.circle = circle;
    }

    public Circle getCircle() {
        return circle;
    }
    
}
