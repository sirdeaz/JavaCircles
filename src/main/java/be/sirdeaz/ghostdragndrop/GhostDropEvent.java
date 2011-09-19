package be.sirdeaz.ghostdragndrop;



import java.awt.Point;
import javax.swing.JComponent;

public class GhostDropEvent {
	private final Point point;
	private final String action;
    private final JComponent source;
    private final JComponent target;

	public GhostDropEvent(String action, Point point, JComponent source, JComponent target) {
		this.action = action;
		this.point = point;
        this.source = source;
        this.target = target;
	}

	public String getAction() {
		return action;
	}

	public Point getDropLocation() {
		return point;
	}

    public JComponent getSource() {
        return source;
    }
        
    public JComponent getTarget() {
        return target;
    }
   
}
