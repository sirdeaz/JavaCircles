/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.sirdeaz.javacircles;

import be.sirdeaz.javacircles.Circle;
import be.sirdeaz.javacircles.CircleCanvas;
import java.awt.Color;

/**
 *
 * @author fdidd
 */
public class TestCircle implements Circle {
    private int degrees = CircleCanvas.getStartAngle();

    public int getId() {
        return 10;
    }

    public Color getColor() {
        return Color.red;
    }

    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }

    public int getDegrees() {
        return this.degrees;
    }

    public String getToolTip() {
        return "roflhi I'm a tooltip";
    }

    public String getText() {
        return "100";
    }
    
}
