package be.sirdeaz.javacircles;

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

/** * Showcase app to play around with the features added. *  * @author sirdeaz */
public class Test {

	private List<CircleController> circles = new ArrayList<CircleController>();
	private Random r = new Random(new Date().getTime());

	public static void main(String[] args) {
		new Test();
	}

	private Test() {
		TestCircle t = new TestCircle();
		if (t instanceof Circle) {
			System.out.println("gogogo");
		}
		//CircleManager.getInstance().setClearCircleBackground(false);
		JFrame frame = createGui();
		frame.setVisible(true);
	}

	/**	 * Adds a circle to the provided JPanel.	 * 	 * @param parent	 *            JPanel where the CircleCanvas will be added to	 * @param title	 *            Title of the CircleCanvas	 */
	private void addCircle(JPanel parent, String title) {
		CircleController c = CircleManager.getInstance().createNewCircleController(title);
		c.getCircleCanvas().setInnerCircleLabelGenerator(new MyLabelGenerator());
		parent.add(c.getCircleCanvas());
		this.circles.add(c);
	}

	/**	 * Adds a Circle to one of the visible CircleCanvases.	 */
	private void addRandomCircle() {
		int index = r.nextInt(circles.size());
		circles.get(index).getCircleCanvas().getModel().addCircle(new TestCircle());
	}

	/**	 * Creates the JFrame containing 2 separate panels with 5 CircleCanvases	 * each.	 * 	 * @return The created JFrame	 */
	private JFrame createGui() {
		final JFrame frame = new JFrame("JavaCircleTest");
		final JPanel centerPanel = new JPanel();
		centerPanel.setBorder(BorderFactory.createTitledBorder("center"));
		for (int i = 0; i < 1; i++) {
			addCircle(centerPanel, "circle" + i);
		}
		JPanel southPanel = new JPanel();
		southPanel.setBorder(BorderFactory.createTitledBorder("south"));
		for (int i = 0; i < 0; i++) {
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
		
		CircleManager.getInstance().addDropListener(new DropListener<TestCircle>() {

			@Override
			public boolean droppedWithoutTarget(TestCircle circle) {
				
				return false;
			}

			@Override
			public boolean droppedWithTarget(TestCircle circle,
					CircleCanvas target) {
				centerPanel.repaint();
				centerPanel.getLayout().layoutContainer(centerPanel);
				return false;
			}
		});
		
		return frame;
	}

	/**	 * An example of how to overwrite the InnerCircleGenerator.	 * 	 * @author sirdeaz	 * 	 */
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

	/**	 * An example implementation of the Circle that will be added to the	 * CircleCanvas.	 * 	 * @author sirdeaz	 * 	 */
	private static class TestCircle extends Circle {
		
		public static int id = 0;
		private CirclePosition position;

		public TestCircle() {
			id++;
		}
		
		public int getId() {
			return id;
		}

		public Color getColor() {
			return Color.red;
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

		public boolean isDraggable() {
			return true;
		}
		
		@Override
		public boolean equals(Object obj) {
			TestCircle testCircle = (TestCircle)obj;
			return this.getId() == testCircle.getId();
		}

		public void setVisible(boolean isVisible) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		public boolean isVisible() {
			return true;
		}

	}
}
