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
public class DefaultInnerCircleColorGenerator implements InnerCircleColorGenerator {

    public Color getColor(CircleCanvas canvas) {
        return ColorSetup.getInstance().getInnerCircleColor();
    }
    
}
