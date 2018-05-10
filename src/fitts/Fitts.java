package fitts;
import java.util.Scanner;

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
 * @version This is the main method which launches version of the program specified by the user.
 * 
 */
public class Fitts {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);					// input scanner
		String input;											// user input
		int version;
		int trials;												// number of trials

		do {
			
			System.out.print("Enter program version number: ");	// input prompt
			
			input = in.nextLine();								// record user input
			
			try {
				
			    version = Integer.parseInt(input);				// copy input to trial number variable
			    
			} catch (NumberFormatException ex) {

				version = 0;									// set trials to invalid number
				
			}
			
			// check for valid input range
			if (version < 1 || version > 3) {

				System.out.println();							// print new line
				System.out.println("Invalid input.");			// invalid input prompt
				System.out.println("Available versions:");		// available versions message
				System.out.println("| 1 | 2 | 3 |");			// print available version numbers
				System.out.println();							// print new line
			}
			
		} while (version < 1 || version > 3);
		
		if (version == 1) {

			new Fitts1();										// start Fitts version 1

			System.out.println("Click on the white circles.");	// input prompt
			System.out.println();								// print new line
			
		} else {
			
			do {
				
				System.out.print("Enter number of trials: ");	// input prompt
				
				input = in.nextLine();							// record user input
				
				try {
					
				    trials = Integer.parseInt(input);			// copy input to trial number variable
				    
				} catch (NumberFormatException ex) {
	
					trials = 0;									// set trials to invalid number
					
				}
				
				// check for valid input range
				if (trials < 1) {
					
					System.out.println();						// print new line
					System.out.println("Invalid input.");		// invalid input prompt
					System.out.println();						// print new line
				}
				
			} while (trials < 1);
			
			if (version == 2) {

				new Fitts2(trials);								// start Fitts version 2
				
			}

			if (version == 3) {

				new Fitts3(trials);								// start Fitts version 3
				
			}
			
			System.out.println();								// print new line
			System.out.println("Follow the white circles.");	// input prompt
			System.out.println();								// print new line
			
		}
		
		in.close();												// close input
		
	}

}
