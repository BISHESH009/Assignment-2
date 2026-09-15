/**
 * FileHandler.java
 * 
 * KIT107 Assignment 2 -- FileHandler Implementation
 * 
 * @author Julian Dermoudy
 * @version	3/8/2026
 * 
 * FILE IS COMPLETE but you can enable/disable output during development
 */


import java.io.*;


public class FileHandler implements FileHandlerInterface
{
	// final instance variables
    final protected String FILENAME;	// name of datafile
    final int LIMIT;    				// number of votes to process

	// non-final instance variables
    protected BufferedReader input;	// input stream


	/**
	 * Constructor
	 * 
	 * @param FILENAME final String -- filename for dataset
	 * @param LIMIT final int -- number of rows to read
	 * 
	 * Precondition: None
	 * Postcondition: The new instance will have its FILENAME field assigned
	 * 					the given value, its LIMIT field assigned the given
	 * 					value, and its input field assigned null.
	 * Informally: Initialise a FileHandler.
	 */
    public FileHandler(final String FILENAME, final int LIMIT)
    {
        this.FILENAME = FILENAME;
		this.LIMIT = LIMIT;
        input = null;
    }
   

	/**
	 * openFile()
	 * 
	 * Precondition: fileName has been initialised to the name/path of an
     *                  existing, readable file.
	 * Postcondition: The nominated file is open for reading and the
     *                  BufferedReader object is assigned to the input
     *                  instance variable.  An exit status of 1 is given
	 * 					if there is an error opening the file.
	 * Informally: Open the datafile.
	 */
    public void openFile()
    {
        try
      	{
			// open the file
		    input = new BufferedReader(new FileReader(FILENAME));
		}
      	catch (IOException e)
		{
			// problem opening the file -- complain and quit!
		    System.err.println("Error opening " + FILENAME + " for loading");
		    System.exit(1);
		}
    }    
      

	/**
	 * closeFile()
	 * 
	 * Precondition: input refers to an open BufferedReader object.
	 * Postcondition: The file referred to by the input instance variable is
     *                  closed.
	 * Informally: Close the datafile.
	 */
    public void closeFile()
    {
        try
		{
			// close the file
			input.close();
		}
		catch (IOException e)
		{
			// problem opening the file -- complain!
			System.err.println("Error closing " + FILENAME + " -- " + e.toString());
		}
    }    
    

	/**
	 * readLine()
	 * 
     * @return String -- the next line of data from the comma-separated-value 
	 * 						(CSV) file
     * 
	 * Precondition: a CSV file (with comma-separated data) has been opened
     *                  for reading and its reference assigned to the input
     *                  instance variable.
	 * Postcondition: the next (non-comment) line from the CSV file is
     *                  returned (or null if end-of-file is reached).  An 
	 * 					exit status of 2 is given if there is an error when
	 * 					reading the file.
	 * Informally: Get the next line of data from the datafile.
	 */
	protected String readLine()
	{
   		String line;    // line from file

		// initialise variable
   		line = null;

   		try
   		{
            // get line from the file, skipping lines beginning with # (comments!)
   			line = input.readLine();
   			while ((line != null) && (line.indexOf('#') == 0)) // skip the comments
   			{
   				line = input.readLine();
   			}
   		}
		catch (IOException e)
		{
			// problem reading the file -- complain and quit!
            System.err.println("Error in " + FILENAME + " data");
            System.exit(2);
		}

	   	return line;
	}    
 

	/**
	 * readFile()
	 * 
     * @param collection Collection -- the collection of votes to be constructed
     * 
	 * Precondition: a text file with comma-separated values exists and the
     *                  program has read permission for it, the file's name
     *                  has been stored in the fileName instance variable,
     *                  and the given Collection parameter has been properly
     *                  constructed.
	 * Postcondition: the file is opened, read -- filling the collection
     *                  referred to on the parameter list up to LIMIT rows of
	 * 					data of the given year -- and closed.  An exit status
	 * 					of 3 is given if there is an error when instantiating
	 * 					the Ballot based upon a line of input.
	 * Informally: Read the data from the datafile for the given parameter and 
	 * 					store the data within the given collection parameter.
	 */
    public void readFile(Collection collection)
    {
		// final variables
		final String DELIMITER = ",";	// comma separated data
        
		// non-final variables
        String line;        // line of data read from file
        String []tokens;    // line of data broken into fields separated by DELIMITER
		int numFields;		// number of votes in each ballot (i.e. row of data) -- read from file
		String electorate;	// name of the electorate -- read from file
        Ballot vote;    	// ballot paper created from the line of data
        int count;          // count of ballots read from file so that stopping at LIMIT can be achieved

        // prepare for reading, no data yet
        count = 0;
		numFields = 0;
		electorate = "";
		
		// open the file
        openFile();

		// get number of fields (candidates)
        line = readLine();
		if (line != null)
		{
			// extract value from line
			numFields = Integer.parseInt(line);
		}
		else
		{
			// value not present so stop
			System.err.println("Number of candidates not found!");
			System.exit(3);
		}

		// get name of electorate
		line = readLine();
		if (line != null)
		{
			// whole line represents electorate
			electorate = line;
		}
		else
		{
			// value not present so stop
			System.err.println("Electorate name not found!");
			System.exit(4);
		}

        // until end-of-file or LIMIT reached, read ballot data, tokenise, and store in collection
        line = readLine();
        while ((line != null) && (count < LIMIT))
        {
			// split line of data into fields
            tokens = line.split(DELIMITER);
			if (tokens.length != numFields)
			{
				// not enough data on line -- complain and quit!
            	System.err.println("Insufficient fields on line " + count);
				System.err.println("Only " + tokens.length + " field(s):");
				for (int i = 0; i < tokens.length; i++)
				{
					System.err.println(tokens[i]);
				}
				System.exit(5);
			}
			
			// got a ballot, so count it, and if developing, show it
			count++;
			//System.out.println("Line " + count + " of " + LIMIT + " read"); // uncomment when debugging
			//System.out.println("\n\t" + line + "\n"); // uncomment when debugging
			
			// create Ballot using input values
			vote = new Ballot(electorate, tokens);

			// add the vote to the collection
			collection.addBallotToCollection(vote);

			// move on to next line of data
            line = readLine();
        }

        // close the file
        closeFile();
    }
}