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
public class DefaultCircleModel implements CircleModel {

	private final List<Circle> circles = new ArrayList<Circle>();
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}


	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	@Override
	public void addCircles(List<Circle> circles) {
		this.circles.addAll(circles);
		fireCircleAdded(null);
	}

	@Override
	public void addCircle(Circle circle) {
		this.circles.add(circle);
		fireCircleAdded(circle);
	}


	@Override
	public void removeCircle(Circle circle) {
		this.circles.remove(circle);
		fireCircleRemoved(circle);
	}

	@Override
	public int getCircleCount() {
		return circles.size();
	}

	@Override
	public boolean retainAll(List<Circle> circles) {
		boolean changed = this.circles.retainAll(circles);
		if (changed) {
			fireCircleRemoved(null);
		}
		return changed;
	}

	@Override
	public int getIndexOf(Circle circle) {
		int counter = 0;
		for (Circle myCircle : circles) {
			if (circle == myCircle) {
				return counter;
			} else {
				++counter;
			}
		}

		return -1;
	}

	@Override
	public List<Circle> getCircles() {
		return Collections.unmodifiableList(this.circles);
	}

	@Override
	public void fireModelChanged() {
		this.pcs.firePropertyChange(PROP_MODEL_CHANGED, null, null);
	}

	@Override
	public void fireCircleAdded(Circle circle) {
		this.pcs.firePropertyChange(PROP_CIRCLE_ADDED, null, circle);
	}

	@Override
	public void fireCircleRemoved(Circle circle) {
		this.pcs.firePropertyChange(PROP_CIRCLE_REMOVED, null, circle);
	}
}
