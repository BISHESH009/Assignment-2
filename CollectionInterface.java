/**
 * CollectionInterface.java
 * 
 * KIT107 Assignment 2 -- Collection Specification
 * 
 * @author Julian Dermoudy
 * @version	31/7/2026
 * 
 * FILE IS COMPLETE
 */


public interface CollectionInterface
{
    // Constructor
    // public Collection(int num_reps);

    // Accessor (Getter) methods
    public String getElectorateName();

    // Other (Doer) methods
    public boolean isEmpty();
    public void addBallotToCollection(Ballot v);
    public boolean validCandidate(String n);
    public void showDistribution();
    public void showCandidateVotes(String c);
    public void closeElection();
    public String distributePreferences();
    public String toString();
}