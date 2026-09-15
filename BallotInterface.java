/**
 * BallotInterface.java
 * 
 * KIT107 Assignment 2 -- Ballot Specification
 * 
 * @author Julian Dermoudy
 * @version	31/7/2026
 * 
 * FILE IS COMPLETE
 */


public interface BallotInterface
{
    // Constructor
    // public Ballot(String electorate, String[] votes);

    // Accessor (Getter) methods
    public String getElectorate();
    public String[] getVotes();
    public double getWeight();
    public int getChoice();

    // Mutator (Setter) methods
    public void setElectorate(String electorate);
    public void setVotes(String[] votes);
    public void setWeight(double weight);
    public void setChoice(int choice);

    // Other (Doer) methods
    public void update();
    public String getSelection();
    public int getMaxVote();
    public boolean exhausted();
    public String toString();
}