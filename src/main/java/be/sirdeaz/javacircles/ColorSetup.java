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
public class ColorSetup {

    private final static ColorSetup INSTANCE = new ColorSetup();
    private  Color smallCircleColor = new Color(0xF2583E);
    private  Color outerCircleColor = new Color(0x4086AA);
    private  Color innerCircleColor = new Color(0x91C3DC);

    private ColorSetup() {
    }

    public static ColorSetup getInstance() {
        return INSTANCE;
    }

    public Color getInnerCircleColor() {
        return innerCircleColor;
    }

    public void setInnerCircleColor(Color innerCircleColor) {
        this.innerCircleColor = innerCircleColor;
    }

    public Color getOuterCircleColor() {
        return outerCircleColor;
    }

    public void setOuterCircleColor(Color outerCircleColor) {
        this.outerCircleColor = outerCircleColor;
    }

    public Color getSmallCircleColor() {
        return smallCircleColor;
    }

    public void setSmallCircleColor(Color smallCircleColor) {
        this.smallCircleColor = smallCircleColor;
    }
}
