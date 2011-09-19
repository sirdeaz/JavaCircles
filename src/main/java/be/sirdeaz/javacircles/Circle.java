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
public interface Circle {

    int getId();
    
    Color getColor();
    
    void setDegrees(int degrees);
    int getDegrees();
    
    String getToolTip();
    String getText();
 
}
