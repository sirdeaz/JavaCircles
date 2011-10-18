/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.sirdeaz.javacircles;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author fdidd
 */
public class CircleModel {

    private final List<Circle> taskCircles = new ArrayList<Circle>();
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public static final String PROP_MODEL_CHANGED = "CIRCLE_MODEL_CHANGED";
    public static final String PROP_CIRCLE_ADDED = "CIRCLE_MODEL_TASK_ADDED";
    public static final String PROP_CIRCLE_REMOVED = "CIRCLE_MODEL_TASK_REMOVED";

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public void addCircles(List<Circle> circles) {
        taskCircles.addAll(circles);
        fireCircleAdded();
    }
    public void addCircle(Circle circle) {
        taskCircles.add(circle);
        fireCircleAdded();
    }

    public void removeCircle(Circle circle) {
        taskCircles.remove(circle);
        fireCircleRemoved();
    }

    public int getCircleCount() {
        return taskCircles.size();
    }

    public boolean retainAll(List<Circle> circles) {
        boolean changed = taskCircles.retainAll(circles);
        if (changed) {
            fireCircleRemoved();
        }
        return changed;
    }
    
    public Circle getCircleAt(int i) {
        if (i >= taskCircles.size()) {
            throw new IllegalArgumentException("index cannot be higher than " + taskCircles.size());
        }

        return taskCircles.get(i);
    }

    public int getIndexOf(Circle taskCircle) {
        int counter = 0;
        for (Circle circle : taskCircles) {
            if (circle == taskCircle) {
                return counter;
            } else {
                ++counter;
            }
        }

        return -1;
    }

    public List<Circle> getCircles() {
        return Collections.unmodifiableList(taskCircles);
    }

    public void fireModelChanged() {
        this.pcs.firePropertyChange(PROP_MODEL_CHANGED, null, null);
    }

    public void fireCircleAdded() {
        this.pcs.firePropertyChange(PROP_CIRCLE_ADDED, null, null);
    }

    public void fireCircleRemoved() {
        this.pcs.firePropertyChange(PROP_CIRCLE_REMOVED, null, null);
    }

}
