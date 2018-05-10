package fitts;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * 
 * This program demonstrates Fitts's law: T = a + b * log2 (2d/w).
 * 
 * Fitts's law is a predictive model of human movement primarily used in human–computer interaction and ergonomics.
 * This scientific law predicts that the time required to rapidly move to a target area is a function of
 * the ratio between the distance to the target and the size of the target.
 * 
 * @author Shayan Rizvi
 * 
 * @reference <a href="https://en.wikipedia.org/wiki/Fitts%27s_law">Fitt's Law</a>
 * 
 * @version 2
 * 
 */
@SuppressWarnings("serial")
public class Fitts2 extends JFrame {
	
	//initialize variables
    int screenw = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();				// screen width
    int screenh = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();			// screen height

	RButton button = new RButton("Follow Me");												// button
	
	int x;																					// button x coordinate
    int y;																					// button y coordinate	
	int diameter;																			// button diameter
	int distance;																			// button distance from center of screen

	int trial;																				// trial number
	double totalTime;																		// total test time
	double totalFitts;																		// sum of fitts values
	
	double newTime;																			// trial pass time
	double oldTime;																			// trial start time
	double trialTime;																		// time between trials

	double fitts;																			// fitts value
    double correlation;																		// correlation between fitt's value and trial time
    
    Robot robot;																			// robot to reset mouse cursor
    
    
    
    // primary constructor
	public Fitts2(int trials) {

	    // configure JFrame
	    this.setTitle("Fitts");																// title
	    this.setVisible(true);																// visibility
	    this.setLayout(null);																// null layout
	    this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);				// fill screen
	    this.setAlwaysOnTop(true);															// focus
	    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);						// exit program on close
	    this.getContentPane().setBackground(Color.BLACK);									// black background
	    this.add(button);																	// add button

	    // configure button
	    MouseAdapter mouseListener = new MouseAdapter() {									// define button mouse adapter
	        @Override
	        public void mouseEntered(MouseEvent e) {
	        	
	        	// initial configuration
	        	if (trial == 0) {
	        		
			        oldTime = System.currentTimeMillis();;									// record start time
	            	button.setText("");														// clear button text
	            	
	        	}
	        	
	        	// configuration during test
	        	if (trial > 0 && trial <= trials) {
	        		
		            newTime = System.currentTimeMillis();									// record pass time
		            trialTime = newTime - oldTime;											// calculate trial time
		            oldTime = newTime;														// set next trial start time
		            
	            	fitts = 0.14 * Math.log(distance * 2/diameter) / Math.log(2);			// calculate fitts value
	            	
	            	correlation = correlation + fitts / (trialTime/1000);					// calculate correlation
	            	
	            	totalTime = totalTime + trialTime;										// add trial time to total test time
	            	totalFitts = totalFitts + fitts;										// add fitts value to total fitts values
	            	
		            //print trial results
		            System.out.printf("| Trial #: %3d " , trial);							// print number
		            System.out.printf("| Distance: %3d " , distance);						// print button distance
		            System.out.printf("| Diameter: %3d " , diameter);						// print button diameter
		            System.out.printf("| Fitts: %5.2f " , fitts);							// print fitts value
		            System.out.printf("| Time: %5.2f " , trialTime/1000);					// print time
		            System.out.printf("| Correlation: %5.2f " , correlation / trial);		// print correlation
		            System.out.println("|");												// print new line
		            
	            }
	        	
		        // test completion configuration
		        if (trial == trials) {
		        	
		        	// print summary
		            System.out.println();													// print new line
		            System.out.println("Test completed.");									// print test completion message
					System.out.println();													// print new line

		            System.out.printf("| Average fitts value: %5.2f " ,						// print average fitts value
		            		totalFitts / trial);
		            System.out.printf("| Average trial time: %5.2f " ,						// print average trial time
		            		(totalTime/1000) / trial);
		            System.out.printf("| Total fitts: %5.2f " , totalFitts);				// print fitts values total
		            System.out.printf("| Total time: %5.2f " , totalTime/1000);				// print total test time
		            System.out.println("|");												// print new line
		            
		       		dispatchEvent(new WindowEvent(											// close window
		       				Fitts2.this , WindowEvent.WINDOW_CLOSING));
	            	
		        }
		        
	        	// reset for next trial
		        if (trial < trials) {
		        	
		        	// button randomization logic
		        	do {
		        		
			        	diameter = 10 + (new Random().nextInt(150));						// randomize diameter
			        	
				        x = new Random().nextInt(screenw - diameter) + diameter/2;			// randomize x coordinate
				        y = new Random().nextInt(screenh - diameter - 55) + diameter/2;		// randomize y coordinate
				        
				        distance = (int) Math.sqrt(											// calculate button center distance from screen center based on coordinates
				        		
				        	Math.pow(
				        		
				        		Math.max(x, screenw/2)
				        	  - Math.min(x, screenw/2)
				        		
				        	  , 2)
				        		  
				        	+
				        	
				        	Math.pow(
				        		
				        		Math.max(y, screenh/2)
				        	  - Math.min(y, screenh/2)
				        	  
				        	  , 2));
				        
		        	} while (distance < diameter / 2 + 150);								// set base button distance from mouse cursor location at screen center
		        	
		            // reset cursor to center of screen
					try {
						
						robot = new Robot();	
			        	robot.mouseMove(screenw/2, screenh/2 + 21);
						
					} catch (AWTException e1) {
						
						e1.printStackTrace();
						
					}
		        	
	            	button.setBounds(x - diameter/2, y - diameter/2, diameter, diameter);	// reset button location and diameter to randomly generated values
	            	trial++;																// increment trials
			        
		        }
		        
	        }
	        	        
	     };
	     
	     button.setBounds(screenw/2 - 100, screenh/2 - 100, 200, 200);						// initial button bounds
		 button.addMouseListener(mouseListener);											// add mouse adapter to button
		 
	}
	
}

