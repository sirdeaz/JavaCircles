/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.sirdeaz.javacircles;

import be.sirdeaz.ghostdragndrop.GhostGlassPane;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author fdidd
 */
 class MyGlassPane extends GhostGlassPane {

    private static final long serialVersionUID = 1L;
    private Mode mode = Mode.NONE;

    private void drawMode(Graphics2D g2, int x, int y, int width, int height) {
        switch (this.mode) {
            case NONE:
                break;
            case ADD:
                g2.setPaint(Color.green);
                g2.fillOval(x+width, y-25, width, height);
                break;
            case REMOVE:
                g2.setPaint(Color.red);
                g2.fillOval(x+width, y-25, width, height);
                break;
        }
    }

    public enum Mode {

        ADD, REMOVE, NONE
    };

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public void paintComponent(Graphics g) {
        Image dragged = getImage();
        Point location = getPoint();

        if (dragged == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(getComposite());
        int x = (int) (location.getX() - (dragged.getWidth(this) / 2));
        int y = (int) (location.getY() - (dragged.getHeight(this) / 2));
        g2.drawImage(dragged,
            x,
            y,
            null);

        drawMode(g2, x, y, dragged.getWidth(this), dragged.getHeight(this));
    }

}
