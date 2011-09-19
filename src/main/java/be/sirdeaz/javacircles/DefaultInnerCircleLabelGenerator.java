/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.sirdeaz.javacircles;

/**
 *
 * @author fdidd
 */
public class DefaultInnerCircleLabelGenerator implements InnerCircleLabelGenerator {

    public String generateLabel(CircleCanvas canvas) {
        return canvas.getTitle();
    }
    
}
