/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.sirdeaz.javacircles;

/**
 *
 * @author fdidd
 */
public class CirclePosition {

	private final int layer;
	private final double degrees;

	public CirclePosition(int layer, double degrees) {
		this.layer = layer;
		this.degrees = degrees;
	}

	public double getDegrees() {
		return degrees;
	}

	public int getLayer() {
		return layer;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof CirclePosition)) {
			return false;
		}

		CirclePosition otherObj = (CirclePosition) obj;

		return this.getDegrees() == otherObj.getDegrees() && this.getLayer() == otherObj.getLayer();
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 37 * hash + this.layer;
		hash = 37 * hash + (int) (Double.doubleToLongBits(this.degrees) ^ (Double.doubleToLongBits(this.degrees) >>> 32));
		return hash;
	}

}
