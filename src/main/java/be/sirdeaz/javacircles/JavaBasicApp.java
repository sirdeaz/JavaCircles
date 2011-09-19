/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.sirdeaz.javacircles;

import be.sirdeaz.javacircles.Circle;
import be.sirdeaz.javacircles.CircleController;
import be.sirdeaz.javacircles.CircleManager;
import be.sirdeaz.javacircles.CircleModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Calendar;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author sirdeaz
 */
public class JavaBasicApp {

	private static Random r = new Random(Calendar.getInstance().getTime()
			.getTime());

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		JFrame frame = new JFrame("JavaBasicApp");
		final CircleModel model = new CircleModel();
		final CircleController c1 = CircleManager.getInstance()
				.createNewCircleController("#####left#####", model);
		CircleController c2 = CircleManager.getInstance()
				.createNewCircleController("####right####");

		JPanel centerPanel = new JPanel(new FlowLayout());
		centerPanel.add(c1.getCircleCanvas());
		centerPanel.add(c2.getCircleCanvas());

		frame.setGlassPane(CircleManager.getInstance().getGhostGlassPane());
		JButton b = new JButton("ADD ME ROFLHI");
		b.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				model.addCircle(new TestCircle());
			}
		});

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(b, BorderLayout.NORTH);
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

		frame.pack();
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
	}

	private JavaBasicApp() {
	}

}
