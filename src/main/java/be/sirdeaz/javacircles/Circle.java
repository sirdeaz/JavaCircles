/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.sirdeaz.javacircles;

import java.awt.Color;

/**
 *
 * @author fdidd
 */
public abstract class Circle {

	public static final CirclePosition START_POSITION = new CirclePosition(1, 270);
	private CirclePosition position;

	public Circle() {
		this.position = START_POSITION;
	}

	public abstract int getId();

	public abstract boolean isDraggable();

	public abstract void setVisible(boolean isVisible);

	public abstract boolean isVisible();

	public abstract Color getColor();

	public abstract Color getOutlineColor();

	public abstract String getToolTip();

	public abstract String getText();

	final CirclePosition getPosition() {
		return this.position;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Circle)) {
			return false;
		}

		Circle otherCircle = (Circle) obj;

		return this.getId() == otherCircle.getId();
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 79 * hash + this.getId();
		return hash;
	}

	final void setPosition(CirclePosition position) {
		this.position = position;
	}
}
