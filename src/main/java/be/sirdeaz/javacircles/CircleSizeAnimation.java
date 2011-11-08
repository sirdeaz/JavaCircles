/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package be.sirdeaz.javacircles;

/**
 *
 * @author fdidd
 */
class CircleSizeAnimation implements BasicAnimation {

    private final CircleCanvas circleCanvas;
    private final int radiusDiff;

    public CircleSizeAnimation(CircleCanvas circleCanvas) {
        this.circleCanvas = circleCanvas;
        if (circleCanvas.getOuterCircleRadius() > CircleCanvas.OUTER_CIRCLE) {
            this.radiusDiff = circleCanvas.getOuterCircleRadius() - CircleCanvas.OUTER_CIRCLE;
        } else {
			//
            //this.radiusDiff = CircleCanvas.OUTER_CIRCLE_EXTEND;
			this.radiusDiff = circleCanvas.getExtendedWidth();
        }
    }

    @Override
    public void doMove(double fraction) {
       this.circleCanvas.extendOuterCircleRadius((int)(fraction*radiusDiff));
    }

}
