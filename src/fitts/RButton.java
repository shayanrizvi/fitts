package fitts;
import javax.swing.JButton;
import java.awt.*;
import java.awt.geom.*;

/**
 * 
 * This class defines a round JButton.
 * 
 * @author Shayan
 * 
 */
@SuppressWarnings("serial")
public class RButton extends JButton {

	// initialize
	FontMetrics metrics;
	int stringWidth;
	int stringHeight;
	int diameter;										// diameter
	int radius;											// radius
	
	// Constructor for a button with text
	public RButton(String text) {
		
		super(text);									// set text
		setOpaque(false);								// opacity
		setBorderPainted(false);						// no border
		
	}

	// Constructor for a button without text
	public RButton() {
		
		setOpaque(false);								// opacity
		setBorderPainted(false);						// no border
		
	}
	
	@Override
	public boolean contains(int x, int y) {
		
		return Point2D.distance(x, y,					// return true if cursor distance from center
				radius, radius) < radius;				// of circle is less than circle radius
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		diameter = Math.min(getWidth(), getHeight());	// diameter
		radius = diameter/2;							// radius
		
		metrics = g.getFontMetrics(getFont());			// get font metrics
		stringWidth = metrics.stringWidth(getText());	// font width
		stringHeight = metrics.getHeight();				// font height
		
		g.setColor(Color.WHITE);						// set color
		g.fillOval(0, 0, diameter, diameter);			// paint
		g.setColor(Color.BLACK);						// set color for text
		g.setFont(getFont());							// set font
		g.drawString(getText(), radius - stringWidth/2,	// draw text
				radius + stringHeight/4);
		
	}
	
}

