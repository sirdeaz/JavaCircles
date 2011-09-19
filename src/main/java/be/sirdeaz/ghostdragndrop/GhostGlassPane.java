package be.sirdeaz.ghostdragndrop;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GhostGlassPane extends JPanel {

    private static final long serialVersionUID = 1L;
    private AlphaComposite composite;
    private BufferedImage dragged = null;
    private Point location = new Point(0, 0);

    public GhostGlassPane() {
        setOpaque(false);
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
    }

    public void setImage(BufferedImage dragged) {
        this.dragged = dragged;
    }

    public void setPoint(Point location) {
        this.location = location;
    }

    protected Image getImage() {
        return this.dragged;
    }

    protected Point getPoint() {
        return this.location;
    }

    protected AlphaComposite getComposite() {
        return this.composite;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (dragged == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(composite);
        int x = (int) (location.getX() - (dragged.getWidth(this) / 2));
        int y = (int) (location.getY() - (dragged.getHeight(this) / 2));
        g2.drawImage(dragged,
                x,
                y,
                null);
    }
}
