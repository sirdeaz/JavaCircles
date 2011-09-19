/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.sirdeaz.javacircles;

/**
 *
 * @author fdidd
 */
public interface DropListener<E extends Circle> {
    boolean droppedWithoutTarget(E circle);
    boolean droppedWithTarget(E circle, CircleCanvas target);
}
