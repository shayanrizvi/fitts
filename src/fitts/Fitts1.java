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
 * Th is scientific law predicts that the time required to rapidly move to a target area is a function of
 * the ratio between the distance to the target and the size of the target.
 * 
 * @author Shayan Rizvi
 * 
 * @reference <a href="https://en.wikipedia.org/wiki/Fitts%27s_law">Fitt's Law</a>
 * 
 * @version 1
 * 
 */
@SuppressWarnings("serial")
public class Fitts1 extends JFrame {
	
	//initialize variables
    int screenw = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();				// screen width
    int screenh = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();			// screen height
    
	RButton button = new RButton("Click the white circles");								// button

	String direction;																		// button direction relative to center of screen
	int x;																					// button x coordinate
    int y;																					// button y coordinate
	int diameter;																			// button diameter
	int distance;																			// button distance from center of screen

	double newTime;																			// time at current button click
	double oldTime;																			// time at last button click
	double trialTime;																		// time between button clicks

    int rl;																					// random button location
    int rd;																					// random button dimensions
	
	int clicks = 0;																			// number of button clicks
	int misClicks = 0;																		// number of misclicks
	
	int totalMisClicks;																		// total number of misclicks
	double totalTime;																		// total test time
	
	int ratio;																				// button distance / diameter ratio
    
    Robot robot;																			// robot to reset mouse cursor
    
    
    // primary constructor
	public Fitts1() {
		
	    // configure JFrame
	    this.setTitle("Fitts");																// title
	    this.setSize(screenw, screenh);														// size
	    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);						// exit program on close
	    this.setVisible(true);																// visibility
	    this.setLayout(null);																// null layout
	    this.setAlwaysOnTop(true);															// focus
	    this.getContentPane().setBackground(Color.BLACK);									// black background
	    this.add(button);																	// add button
	    
	    MouseAdapter frameMouseListener = new MouseAdapter() {								// define frame mouse adapter
	        @Override
	        public void mousePressed(MouseEvent e) {
	        		misClicks++;															// increment misclicks on frame click
		    }
	     };
	    
	    this.addMouseListener(frameMouseListener);											// add mouse adapter to frame

	    // configure button
	    MouseAdapter mouseListener = new MouseAdapter() {									// define button mouse adapter
	        @Override
	        public void mousePressed(MouseEvent e) {
	        	
	        	rl = new Random().nextInt(4);												// generate random button location (1 of 4 possibilities)
	        	rd = new Random().nextInt(3);												// generate random button dimension (1 of 3 possibilities)
	        	
	        	// initial configuration
	        	if (clicks == 0) {
	        		
			        oldTime = System.currentTimeMillis();;									// record start time
	            	button.setText("");														// clear button text
	            	
	        	}
	        	
	        	// configuration during test (12 trials)
	        	else if (clicks > 0 && clicks <= 12) {
	        		
		            newTime = System.currentTimeMillis();									// record click time
		            trialTime = newTime - oldTime;											// calculate trial time
		            oldTime = newTime;														// set next trial time

	            	ratio = distance / diameter;											// calculate button difficulty index
		            
	            	totalMisClicks = totalMisClicks + misClicks;							// add misclicks to total miclicks counter
	            	totalTime = totalTime + trialTime;										// add trial time to total test time
	            	
		            //print trial results
		            System.out.printf("| Trial #: %2d " , clicks);							// print number
		            System.out.printf("| Direction: %5s " , direction);						// print direction
		            System.out.printf("| Distance: %3d " , distance);						// print button distance
		            System.out.printf("| Diameter: %2d " , diameter);						// print button diameter
		            System.out.printf("| Misclicks: %2d " , misClicks);						// print misclicks
		            System.out.printf("| Ratio: %3d " , ratio);								// print button distance / diameter ratio
		            System.out.printf("| Time: %5.2f " , trialTime / 1000);					// print time
		            System.out.println("|");												// print new line
		            
	            }
	        	
		        // test completion configuration
		        if (clicks == 12) {
		        	
		        	// print totals
		            System.out.println();													// print new line
	            	System.out.printf("| Total trials: %2d " , clicks);						// print total trials
	            	System.out.printf("| Total misclicks: %2d " , totalMisClicks);			// print total misclicks
		            System.out.printf("| Average trial time: %5.2f " ,						// print average trial time
		            		(totalTime / 1000) / clicks);
		            System.out.printf("| Total time: %5.2f " , totalTime / 1000);			// print total test time
		            System.out.println("|");												// print new line
		        	
	        		button.setBounds(screenw/2-150, screenh/2-150, 300,300);				// change button location and dimensions to completion settings
	            	button.setText("TEST COMPLETED");										// change button text to completion setting
	            	clicks++;																// terminate button functionality
	            	
		        }
		        
	        	// reset for next trial
		        if (clicks < 12) {
		        	
		        	// button location randomization logic (4 possible locations)
			        if (rl == 1) {
		            	x = (screenw/2) - 133;												// set button location x coordinate
		            	direction = "left";													// set button direction
		            	distance = 128;														// set button distance from center
		            }
		            else if (rl == 2) {
		            	x = (screenw/2) - 517;												// set button location x coordinate
		            	direction = "left";													// set button direction
		            	distance = 512;														// set button distance from center
		            }
		            else if (rl == 3) {
		            	x = (screenw/2) + 123;												// set button location x coordinate
		            	direction = "right";												// set button direction
		            	distance = 128;														// set button distance from center
		            }
		            else {
		            	x = (screenw/2) + 507;												// set button location x coordinate
		            	direction = "right";												// set button direction
		            	distance = 512;														// set button distance from center
		            }	            
		            
			        // button dimensions randomization logic (3 possible dimensions)
		            if (rd == 1) {
		            	y = (screenh/2) - 8;												// set button location y coordinate
		            	diameter = 16;														// set button distance from center
		            }
		            else if (rd == 2) {
		            	y = (screenh/2) - 16;												// set button location y coordinate
		            	diameter = 32;														// set button distance from center
		            }
		            else {
		            	y = (screenh/2) - 32;												// set button location y coordinate
		            	diameter = 64;														// set button distance from center
		            }
		            
		            // reset cursor to center of screen
					try {
						
						robot = new Robot();	
			        	robot.mouseMove(screenw/2+5, screenh/2+25);
						
					} catch (AWTException e1) {
		
						e1.printStackTrace();
						
					}
		        	
		        	// reset button and click variables
	            	button.setBounds(x, y, diameter, diameter);								// change button location and dimensions to randomly generated values
		            misClicks = 0;															// reset misclicks
			        clicks++;																// increment clicks
			        
		        }
		        
	        }
	        
	     };
	     
	     button.setBounds(screenw/2-150, screenh/2-150, 300,300);							// initial button bounds
		 button.addMouseListener(mouseListener);											// add mouse adapter to button
		 
	}
	
}

