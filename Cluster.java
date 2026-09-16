/**
 * Cluster.java
 * 
 * KIT107 Assignment 2 -- Cluster Implementation
 * 
 * @author Bishesh kc 778434
 * @version	14/09/2026
 * 
 * purpose: stores and manages the ballots for a single candidate, keeping them sorted by prefernce
 */

public class Cluster implements ClusterInterface
{
    // instance variables
    protected String bundleName;        // candidate for whom this cluster of votes is for
    protected double weightedCount;     // weight of votes in this cluster
    protected int rawCount;             // raw count of votes in this cluster
    protected Node firstBallot;       //  reference to the first ballot node; ballots are strored in descending preference order
	/**
	 * Constructor
	 * 
     * @param candidate String -- the name of the candidate whose votes 
     *                  this bundle (cluster) is for
     * 
	 * Precondition: The String is defined and unique
	 * Postcondition: The new instance will have its instance variable(s)
     *                  initialised to indicate an empty cluster.
	 * Informally: Initialise the cluster of ballots.
	 */
    public Cluster(String candidate)
    {
       // save the candidate name
	   bundleName = candidate;

	   // stating with no ballots in the cluster
	   rawCount = 0;
	   weightedCount = 0;
	   firstBallot = null;
    }

	/**
	 * isEmpty()
	 * 
	 * @return boolean -- whether the cluster is empty
	 * 
	 * Precondition: None
	 * Postcondition: True is returned if the Cluster is empty; false is
     *                  returned otherwise.
	 * Informally: Check whether the Cluster is empty.
	 */
    public boolean isEmpty()
    {
        // if firstBallot is null, there are the no ballots in the cluster, so it is empty
		if (firstBallot == null) 
		{
			return true;
		}
		else 
		{
			return false;
		}

    }

 	/**
	 * getFirstBallot()
	 * 
	 * @return Ballot -- the first ballot paper in the cluster
	 * 
	 * Precondition: None
	 * Postcondition: the first ballot in the cluster is returned if the
     *                  cluster is non-empty; null is returned otherwise.
	 * Informally: Get the first ballot paper in the cluster, which is the one with the lowest preference number stored
	 */
    public Ballot getFirstBallot()
    {
        // if the cluster is empty, there is nothing to give back, so return null
		if (isEmpty())
		{
        return null;
		}
		else
		{

		
	      // take the data out of the firstBallot node and change it to a Ballot 
		  Ballot firstOne = (Ballot) firstBallot.getData();
        return firstOne; 
		}
    }

 	/**
	 * getRawCount()
	 * 
	 * @return int -- the raw count of ballot papers in the cluster
	 * 
	 * Precondition: None
	 * Postcondition: the raw count of ballots in the cluster is returned.
	 * Informally: Get the count of ballots in the cluster.
	 */
    public int getRawCount()
    {
        return rawCount;
    }

 	/**
	 * getWeightedCount()
	 * 
	 * @return double -- the weighted count of ballot papers in the cluster
	 * 
	 * Precondition: None
	 * Postcondition: the weighted count of ballots in the cluster is
     *                  returned.
	 * Informally: Get the weighted count of ballots in the cluster.
	 */
    public double getWeightedCount()
    {
        return weightedCount;
    }

 	/**
	 * getBundleName()
	 * 
	 * @return String -- the name of the candidate that is the recipient
     *                  of this cluster of ballots
	 * 
	 * Precondition: None
	 * Postcondition: the name of the bundle is returned.
	 * Informally: Get the cluster's candidate name.
	 */
    public String getBundleName()
    {
        return bundleName;
    }

    /**
	 * addBallotToCluster()
	 * 
	 * @param votes Ballot -- the ballot paper to add to this cluster
	 * 
	 * Precondition: The given Ballot parameter has been constructed.
	 * Postcondition: The given Ballot has been added to the Cluster of
     *                  ballot papers ordered by descending preference.
	 * Informally: Add a ballot paper to the Cluster.
	 */
    public void addBallotToCluster(Ballot votes)
    {
        //make a new node to hold this ballot
		Node newNode = new Node(votes);

		// there are two pointers that help us find were to put the new node 
		Node previous = null;
		Node current = firstBallot;
		boolean foundSpot = false;

		// walk through the link unit we can find the right place
		while (current != null && !foundSpot)
		{
			Ballot currentBallot = (Ballot) current.getData();

			if (currentBallot.getChoice() > votes.getChoice())
			{
				previous = current;
				current = current.getNext();
			}
			else
			{
				foundSpot = true;
			}
		}

		// now put the newNode in between previous and current
		if (previous == null)
		{
			newNode.setNext(firstBallot);
			firstBallot = newNode;
		}
		else 
		{
			newNode.setNext(current);
			previous.setNext(newNode);
		}

		// update the counts 
		rawCount = rawCount + 1;
		weightedCount = weightedCount + votes.getWeight();
	}

 	/**
	 * votesFor()
	 * 
	 * @param candidate String -- the candidate to count the votes of
     * @param preference int -- the preference to count the votes for
     * 
     * @return int -- the count of votes for the given candidate of the
     *                  given preference
	 * 
	 * Precondition: None
	 * Postcondition: the count of ballot where the given candidate is ranked at the given preference is returned in the cluster is returned.
	 * Informally: Count how many ballots have the given candidate at the given preference position.
	 */
    public int votesFor(String candidate, int preference)
    {
       int count = 0;

	   Node current = firstBallot;
       while (current != null)
	   {
		Ballot thisBallot = (Ballot) current.getData();
		String[] allVotes = thisBallot.getVotes();
        
		int index = preference - 1;

		//only check if this preference number actually exists on the ballot
		if (index < allVotes.length)
		{
		
		if (allVotes[index].equals(candidate))
		{
			count = count + 1;
		}
	}
		current = current.getNext();
	   }
        return count;
    }

 	/**
	 * transfer()
	 * 
     * @param residiual double -- the residual weight to be allocated to
     *                  the ballot being transferred
     * 
	 * @return Ballot -- the ballot removed from the current cluster
     *                  which is to be moved to another cluster with the
     *                  given weight
	 * 
	 * Precondition: None
	 * Postcondition: the first ballot in the cluster is removed, the
     *                  selection is updated to the next preference,
     *                  the weight is altered if the residual is not
     *                  full weight, and then the ballot is returned.
     *                  null is returned if the cluster is empty.
	 * Informally: Prepare the first ballot of the cluster to be
     *                  moved to the cluster of its next preference,
     *                  and remove it from this cluster.
	 */
    public Ballot transfer(double residual)
    {
         final double FULL_WEIGHT = -1;
		 if (isEmpty())
		 {
			return null;
		 } 
		 Ballot movingBallot = (Ballot) firstBallot.getData();
		 firstBallot = firstBallot.getNext();
		 rawCount = rawCount -1;
		 weightedCount = weightedCount - movingBallot.getWeight();

		 if (residual != FULL_WEIGHT)
		 {
			double newWeight = movingBallot.getWeight() * residual;
			movingBallot.setWeight(newWeight);
		 }
		 movingBallot.update();
         return movingBallot;
    }

	/**
	 * toString()
	 * 
	 * @return String -- printable form of the Cluster of ballots
	 * 
	 * Precondition: None
	 * Postcondition: A printable (String) form of the ballot data is
     *                  returned.  If there are no ballot papers then ""
     *                  is returned.
	 * Informally: Convert the Cluster of ballot data to a multi-line
     *                  String.
	 */
    public String toString()
    {
     String result = "";
	 Node current = firstBallot;

	 while (current != null)
	 {
		Ballot thisBallot = (Ballot) current.getData();
		result = result + thisBallot.toString() + "\n";
		current = current.getNext();

	 }
        return result;
    }
}