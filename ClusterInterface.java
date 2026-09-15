/**
 * ClusterInterface.java
 * 
 * KIT107 Assignment 2 -- Cluster Specification
 * 
 * @author Julian Dermoudy
 * @version	30/7/2026
 * 
 * FILE IS COMPLETE
 */


public interface ClusterInterface
{
    // Constructor
    // public Cluster(String candidate);
    
    // Accessor (Getter) methods
    public Ballot getFirstBallot();
    public int getRawCount();
    public double getWeightedCount();
    public String getBundleName();

    // Mutator (Setter) methods
    public int votesFor(String candidate, int vote);
    public Ballot transfer(double residual);

    // Other (Doer) methods
    public boolean isEmpty();
    public void addBallotToCluster(Ballot b);
    public String toString();
}