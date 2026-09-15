/**
 * Ballot.java
 * 
 * KIT107 Assignment 2 -- Ballot Implementation
 * 
 * @author Julian Dermoudy
 * @version	31/7/2026
 * 
 * FILE IS COMPLETE
 */


public class Ballot
{
    // instance variables
    protected String electorate;    // name of electorate
    protected String[] votes;       // candidate names
    protected double weight;        // the weight of the ballot
    protected int choice;           // the current preference number

	/**
	 * Constructor
	 * 
	 * @param electorate String -- electorate name
     * @param votes String[] -- the list of candidates in descending
     *                  preference order
	 * 
	 * Precondition: The String and elements in the String array are non
     *                  null
	 * Postcondition: A new Ballot object is created and initialised
     *                  with the given values and others set to 0.
	 * Informally: Initialise a new Ballot.
	 */
    public Ballot(String electorate, String[] votes)
    {
        this.electorate = electorate;
        this.votes = votes;
        this.weight = 1.0;
        this.choice = 0;
    }

	/**
	 * getElectorate()
	 * 
	 * @return String -- the value in the electorate field
	 * 
	 * Precondition: None
	 * Postcondition: the current object's electorate field is returned.
	 * Informally: Get the electorate field.
	 */
    public String getElectorate()
    {
        return electorate;
    }

/**
	 * setElectorate()
	 * 
	 * @param electorate String -- value to be stored in electorate field of
     *                  the ballot
	 * 
	 * Precondition: None
	 * Postcondition: the current object's electorate field is assigned the
     *                  given value.
	 * Informally: Set the electorate field.
	 */
    public void setElectorate(String electorate)
    {
        this.electorate = electorate;
    }

	/**
	 * getVotes()
	 * 
	 * @return String [] -- the value in the votes field
	 * 
	 * Precondition: None
	 * Postcondition: the current object's votes field is returned.
	 * Informally: Get the votes field.
	 */
    public String[] getVotes()
    {
        return votes;
    }

	/**
	 * setVotes()
	 * 
	 * @param votes String [] -- value to be stored in votes field of the
     *                  ballot
	 * 
	 * Precondition: None
	 * Postcondition: the current object's votes field is assigned the given
	 * 					value.
	 * Informally: Set the votes field.
	 */
    public void setVotes(String[] votes)
    {
        this.votes = votes;
    }

	/**
	 * getWeight()
	 * 
	 * @return double -- the value in the weight field
	 * 
	 * Precondition: None
	 * Postcondition: the current object's weight field is returned.
	 * Informally: Get the weight field.
	 */
    public double getWeight()
    {
        return weight;
    }

	/**
     * setWeight()
	 * 
	 * @param weight double -- value to be stored in the weight field of the 
     *                  object
	 * 
	 * Precondition: None
	 * Postcondition: the current object's weight field is assigned the given
	 * 					value.
	 * Informally: Set the weight field.
	 */
    public void setWeight(double weight)
    {
        this.weight = weight;
    }

	/**
	 * getChoice()
	 * 
	 * @return int -- the value in the choice field
	 * 
	 * Precondition: None
	 * Postcondition: the current object's choice field is returned.
	 * Informally: Get the choice field.
	 */
    public int getChoice()
    {
        return choice;
    }

	/**
     * setChoice()
	 * 
	 * @param choice int -- value to be stored in the choice field of the
     *                  object
	 * 
	 * Precondition: None
	 * Postcondition: the current object's choice field is assigned the given
	 * 					value.
	 * Informally: Set the choice field.
	 */
    public void setChoice(int choice)
    {
        this.choice = choice;
    }

	/**
	 * update()
	 * 
	 * Precondition: None
	 * Postcondition: the choice variable is incremented and if all preferences
     *                  have been exhausted then the weight of the ballot is
     *                  set to 0.
	 * Informally: Given the ballot 0 weight if all preferences are exhausted.
	 */
    public void update()
    {
        final double EXHAUSTED = 0; // the weight of an exhausted ballot
 
        // move to the next candidate
        choice++;

        // update weight of the ballot to 0 if all preferences are exhausted
        if (exhausted())
        {
            setWeight(EXHAUSTED);
        }
    }

	/**
	 * getSelection()
	 * 
	 * @return String -- the candidate name of the candidate of the current
     *                  preference
	 * 
	 * Precondition: None
	 * Postcondition: if all preferences have not been exhausted then the name
     *                  of the current preference (choice) candiate is
     *                  returned, otherwise "" is returned.
	 * Informally: Get the name of the current candidate.
	 */
    public String getSelection()
    {
        String result;  // the result of the method

        // initialise the result
        result = "";
        // find the current candidate's name if the ballot isn't exhausted
        if (! exhausted())
        {
            result = votes[choice];
        }

        return result;
    }
    
	/**
	 * getMaxVote()
	 * 
	 * @return int -- the number of candidates on the ballot
	 * 
	 * Precondition: None
	 * Postcondition: the length of the votes array is returned.
	 * Informally: Get the number of candidates on the ballot.
	 */
    public int getMaxVote()
    {
        return votes.length;
    }

	/**
	 * exhausted()
	 * 
	 * @return boolean -- whether all candidates on the ballot have
     *                  been elected or eliminated
	 * 
	 * Precondition: None
	 * Postcondition: whether the current preference exceeds all on
     *                  the ballot.
	 * Informally: Find out if the ballot is exhausted.
	 */
    public boolean exhausted()
    {
        return (getChoice() >= getMaxVote());
    }

	/**
	 * toString()
	 * 
	 * @return String -- printable form of the Ballot
	 * 
	 * Precondition: None
	 * Postcondition: A printable (String) form of the ballot is
     *                  returned.
	 * Informally: Convert the Ballot contents to a multi-line 
     *                  String.
	 */
    public String toString()
    {
        String r;   // result

        // add the electorate name and the candidates to the result
        r = "Electorate: " + electorate + "\n";
        r += "Votes:\n";
        for (int i = 0; i < getMaxVote(); i++) 
        {
            // highlight the selection
            r += (i == getChoice() ? "[" + i + "]" : " " + i + " ");
            r += " - " + votes[i] + "\n";
        }
        // add the weight
        r += "Weight: " + weight;

        return r;
    }
}