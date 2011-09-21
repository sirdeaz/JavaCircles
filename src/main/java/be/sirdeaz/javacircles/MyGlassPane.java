/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.sirdeaz.javacircles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import be.sirdeaz.ghostdragndrop.GhostGlassPane;

/**
 *
 * @author fdidd
 */
 class MyGlassPane extends GhostGlassPane {

    private static final long serialVersionUID = 1L;
    private Mode mode = Mode.NONE;
    
    private static Image addImage = loadImage("add.png");
    private static Image deleteImage = loadImage("delete.png");
    
    public MyGlassPane() {
		
	}
    
    private void drawMode(Graphics2D g2, int x, int y, int width, int height) {
        switch (this.mode) {
            case NONE:
                break;
            case ADD:
                g2.setPaint(Color.green);
                //g2.fillOval(x+width, y-25, width, height);
                g2.drawImage(addImage, x+width-2, y+height/+2, addImage.getWidth(null), addImage.getHeight(null), null);
                break;
            case REMOVE:
                g2.setPaint(Color.red);
                //g2.fillOval(x+width, y-25, width, height);
                g2.drawImage(deleteImage, x+width-2, y+height/2+2, deleteImage.getWidth(null), deleteImage.getHeight(null), null);
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
    
    private static Image loadImage(String name) {
    	BufferedImage image = null;
    	try {
    		URL url = MyGlassPane.class.getResource(name);
			image = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new IllegalStateException(String.format("Image %s not found in classpath.", image));
		}
    	
    	return image;
    }

}
