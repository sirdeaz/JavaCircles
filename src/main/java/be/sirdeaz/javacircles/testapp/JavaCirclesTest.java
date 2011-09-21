/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.sirdeaz.javacircles.testapp;

import be.sirdeaz.javacircles.Circle;
import be.sirdeaz.javacircles.CircleCanvas;
import be.sirdeaz.javacircles.CircleController;
import be.sirdeaz.javacircles.CircleManager;
import be.sirdeaz.javacircles.InnerCircleLabelGenerator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author sirdeaz
 */
public class JavaCirclesTest {

    private List<CircleController> circles = new ArrayList<CircleController>();
    private Random r = new Random(new Date().getTime());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new JavaCirclesTest();
    }

    private JavaCirclesTest() {
        JFrame frame = createGui();
        frame.setVisible(true);
    }

    private void addCircle(JPanel parent, String title) {

        CircleController c = CircleManager.getInstance().createNewCircleController(title);
        c.getCircleCanvas().setInnerCircleLabelGenerator(new MyLabelGenerator());
        parent.add(c.getCircleCanvas());
        this.circles.add(c);
    }

    private void addRandomCircle() {
        int index = r.nextInt(circles.size());
        circles.get(index).getCircleCanvas().getModel().addCircle(new TestCircle());
    }

    private JFrame createGui() {
        JFrame frame = new JFrame("JavaCircleTest");

        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createTitledBorder("center"));
        for (int i = 0; i < 5; i++) {
            addCircle(centerPanel, "circle" + i);
        }

        JPanel southPanel = new JPanel();
        southPanel.setBorder(BorderFactory.createTitledBorder("south"));
        for (int i = 0; i < 5; i++) {
            addCircle(southPanel, "circle" + (5 + i));
        }

        frame.setGlassPane(CircleManager.getInstance().getGhostGlassPane());
        JButton b = new JButton("ADD ME ROFLHI");
        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                addRandomCircle();
            }
        });

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(b, BorderLayout.NORTH);
        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
        frame.getContentPane().add(southPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(3);

        return frame;
    }

    private static class MyLabelGenerator implements InnerCircleLabelGenerator {

        public String generateLabel(CircleCanvas canvas) {
            StringBuilder sb = new StringBuilder();
            sb.append(canvas.getTitle());
            sb.append("\n");
            sb.append(String.format("%d circle(s)", canvas.getModel().getCircleCount()));
            sb.append("\n");
            return sb.toString();
        }
    }

    private static class TestCircle implements Circle {

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

        public Color getOutlineColor() {
            return null;
        }
    }
}
