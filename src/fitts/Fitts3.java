package fitts;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

/**
 * 
 * This program demonstrates Fitts's law: T = a + b * log2 (2d/w).
 * 
 * Fitts's law is a predictive model of human movement primarily used in human–computer interaction and ergonomics.
 * Th is scientific law predicts that the time required to rapidly move to a target area is a function of
 * the ratio between the distance to the target and the size of the target.
 * 
 * @author Shayan Rizvi
 * 
 * @reference <a href="https://en.wikipedia.org/wiki/Fitts%27s_law">Fitt's Law</a>
 * 
 * @version 3
 * 
 */
@SuppressWarnings("serial")
public class Fitts3 extends JFrame {
	
	//initialize variables
    int screenw = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();		// screen width
    int screenh = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();	// screen height
    
	RButton circle = new RButton();													// circle
	
	int trial;																		// trial number
	
	double passTime;																// trial pass time
	double startTime;																// trial start time
	double trialTime;																// time between trials
	
	double fitts;																	// fitts value
    double correlation;																// correlation between fitt's value and trial time
    
	double totalTime;																// sum of trial times
	double totalFitts;																// sum of fitts values
    double totalCorrelation;														// sum of correlation values
    
	int x;																			// circle x coordinate
    int y;																			// circle y coordinate	
	int diameter;																	// circle diameter
	int distance;																	// circle distance from center of screen
	
	int cx;																			// cursor x coordinate
	int cy;																			// cursor y coordinate
	
    Random r = new Random();														// random variable
    
    // primary constructor
	public Fitts3(int trials) {
		
		// configure JFrame
	    setTitle("Fitts");															// title
	    setVisible(true);															// visibility
	    setLayout(null);															// null layout
	    setSize(screenw, screenh);													// size
	    setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);			// fill screen
        setResizable(false);														// unresizable
	    setAlwaysOnTop(true);														// focus
	    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);					// exit program on close
	    getContentPane().setBackground(Color.BLACK);								// black background
	    add(circle);																// add circle
	    
	    // configure circle
	    MouseAdapter mouseListener = new MouseAdapter() {							// define circle mouse adapter
	    	
	        @Override
	        public void mouseEntered(MouseEvent e) {
	        	
	        	// test configuration
	        	if (trial > 0) {
	        		
		            passTime = System.currentTimeMillis();							// record trial pass time
		            trialTime = passTime - startTime;								// calculate trial time
		            
	            	fitts = 0.14 * Math.log(distance * 2/diameter) / Math.log(2);	// calculate fitts value
	            	correlation = fitts / (trialTime/1000);							// calculate correlation
	            	
	            	totalTime = totalTime + trialTime;								// add trial time to total test time
	            	totalFitts = totalFitts + fitts;								// add fitts value to total fitts values
	            	totalCorrelation = totalCorrelation + correlation;				// calculate correlation
	            	
		            //print trial results
		            System.out.printf("| Trial #: %3d " , trial);					// print number
		            System.out.printf("| Distance: %4d " , distance);				// print circle distance
		            System.out.printf("| Diameter: %3d " , diameter);				// print circle diameter
		            System.out.printf("| Fitts: %5.2f " , fitts);					// print fitts value
		            System.out.printf("| Time: %5.2f " , trialTime/1000);			// print time
		            System.out.printf("| Correlation: %5.2f " , correlation);		// print correlation
		            System.out.println("|");										// print new line
		            
	        	}
	        	
		        // test completion configuration
		        if (trial == trials) {
		        	
		        	// print summary
		            System.out.println();											// print new line
		            System.out.println("Test completed.");							// print test completion message
					System.out.println();											// print new line
					
		            System.out.printf("| Average fitts value: %5.2f " ,				// print average fitts value
		            		totalFitts / trial);
		            System.out.printf("| Average trial time: %5.2f " ,				// print average trial time
		            		(totalTime/1000) / trial);
		            System.out.printf("| Average correlation: %5.2f " ,				// print fitts values total
		            		totalCorrelation / trial);
		            System.out.println("|");										// print new line
		            
		       		dispatchEvent(new WindowEvent(									// close window
		       				Fitts3.this , WindowEvent.WINDOW_CLOSING));
	            	
		        }
		        
	        	// reset for next trial
			    cx = e.getXOnScreen();												// record cursor x coordinate
			    cy = e.getYOnScreen();												// record cursor y coordinate
			     
	        	// circle randomization logic
	        	do {
	        		
		        	diameter = 8 + (r.nextInt(80));									// randomize diameter
			        x = r.nextInt(screenw - diameter);								// randomize circle x coordinate
			        y = r.nextInt(screenh - diameter - 55);							// randomize circle y coordinate
			        distance = (int) Point2D.distance(								// calculate circle center distance from cursor
			        		cx, cy, x + diameter/2, y + diameter/2);
			        
	        	} while (distance < diameter / 2 + 300);							// minimum circle distance from cursor
	        	
            	circle.setBounds(x, y, diameter, diameter);							// reset circle location and diameter to randomly generated values
            	trial++;															// increment trials
		        startTime = System.currentTimeMillis();;							// record trial start time
            	
	        }
		    
		};
		
		circle.setBounds(screenw/2 - 100, screenh/2 - 100, 200, 200);				// initial circle bounds
		circle.addMouseListener(mouseListener);										// add mouse adapter to circle
		 
	}
	
}

