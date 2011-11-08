package be.sirdeaz.javacircles;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface CircleModel {

	public static final String PROP_MODEL_CHANGED = "CIRCLE_MODEL_CHANGED";
	public static final String PROP_CIRCLE_ADDED = "CIRCLE_MODEL_TASK_ADDED";
	public static final String PROP_CIRCLE_REMOVED = "CIRCLE_MODEL_TASK_REMOVED";

	void addPropertyChangeListener(PropertyChangeListener listener);

	void removePropertyChangeListener(PropertyChangeListener listener);

	void addCircles(List<Circle> circles);

	void addCircle(Circle circle);

	void removeCircle(Circle circle);

	int getCircleCount();

	boolean retainAll(List<Circle> circles);

	int getIndexOf(Circle circle);

	List<Circle> getCircles();

	void fireModelChanged();

	void fireCircleAdded(Circle circle);

	void fireCircleRemoved(Circle circle);

}