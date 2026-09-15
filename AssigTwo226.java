/**
 * AssigTwo226.java
 * 
 * KIT107 Assignment 2 -- Harness Class
 * 
 * @author Julian Dermoudy
 * @version	3/8/2026
 * 
 * FILE IS COMPLETE but you can alter LIMIT and POSITIONS and also
 * 		enable/disable output during development
 */


import java.util.Scanner;


public class AssigTwo226
{
	/**
	 * main() -- entry point
	 * 
	 * @param args String[] -- command line arguments
	 * 
	 * Precondition: None
	 * Postcondition: A title will be displayed, a dataset read and stored,
	 * 					and then results will be shown on the screen for the
	 * 					specified searches.
	 * Informally: Read the datafile, store it, process it, and produce the
	 * 					results.
	 */
    public static void main(String []args)
    {
		// final local variables
		final String FILENAME = "votes.csv";	// Filename of the file holding the dataset
		final int LIMIT = Integer .MAX_VALUE;;    // number of votes to process; reduce to, e.g., 1 then 10 then 50 then 100, then 1000, then Integer.MAX_VALUE when debugging
		final int POSITIONS = 4;				// Number of positions to elect; reduce to, e.g., 1 then 2, then 4 when debugging

		// non final local variables
		FileHandler myFile;		// object for file handling
		Collection collection;	// collection of data within the program
		Scanner sc;				// scanner for input
		String candidate;		// candidate name for summary
		int round;				// round number of preference distribution
		String outcome;			// result of preference distribution for the round

		// produce title
		System.out.println();
		System.out.println("Australia Votes 2026");
		System.out.println("====================\n");

		// initialise scanner
		sc = new Scanner(System.in);

		// initialise collection and read in ballots
		collection = new Collection(POSITIONS);
		myFile = new FileHandler(FILENAME, LIMIT);
		myFile.readFile(collection);
		collection.closeElection();

		/* 
		System.out.println(collection.toString());
		*/

		// produce histogram of raw votes per candidate
		collection.showDistribution();

		
		// display all preferences for a candidate of the users' choice
		System.out.print("Enter name of candidate: ");
		candidate = sc.nextLine();
		while ((! collection.isEmpty()) && (! collection.validCandidate(candidate)))
		{
			System.out.println(candidate + " not registered as a candidate, try again.");
			System.out.println();
			System.out.print("Enter name of candidate: ");
			candidate = sc.nextLine();
		}
		collection.showCandidateVotes(candidate);
		

		// conduct the distribution of preferences until the required number of people are elected
		System.out.print("Using the Hare-Clark Electoral System to fill " + POSITIONS);
		System.out.println(POSITIONS == 1 ? " vacancy..." : " vacancies...");
		round = 1;
		outcome = collection.distributePreferences();	// complete round 1
		while (! outcome.equals(""))	// more rounds required
		{
			System.out.println("Outcome of round " + round + ": " + outcome);	// show outcome from this round
			collection.showDistribution();	// show current vote distribution of remaining candidates

			round++;	// move to next round
			outcome = collection.distributePreferences();	// complete distribution for that round
		}
		/**/

		// close the Scanner object
		sc.close();
    }
}