/**
 * Collection.java
 * 
 * KIT107 Assignment 2 -- Collection Implementation
 * 
 * @author Bishesh kc 778434
 * @version	14/09/2026
 * 
 * purpose: Stores all candidate clusters and runs the hare-Clark election, electing candidate each round
 */


public class Collection implements CollectionInterface
{
    // final instance variables
    final protected int MAX_CANDIDATES = 10;    // the maximum number of candidates in the collection
    final protected int NUMBER_TO_BE_ELECTED;   // the number of positions to be filled

    // would-be-final instance variables, if enforceable
    protected int quota;                // how many votes are required to become elected
    protected String electorateName;    // the name of the electorate

    // non-final instance variables
    protected int numCandidates;    // the number of candidates still in the election
    protected int numBallots;       // how many ballot papers were submitted this election
    protected int numElected;       // the number of confirmed elections so far
    protected Node firstCandidate; // reference to first candidate cluster; new candidates are appended to th end of the list
	/**
	 * Constructor
	 * 
     * @param numReps int -- the number of representatives to be elected
     * 
	 * Precondition: numReps does not exceed MAX_CANDIDATES
	 * Postcondition: The new instance will have its instance variable(s)
     *                  initialised.
	 * Informally: Initialise the Collection of ballot clusters ('candidates').
	 */
    public Collection(int numReps)
    {
        NUMBER_TO_BE_ELECTED = numReps;
        quota = 0;
        electorateName = "";

        numCandidates = 0;
        numBallots = 0;
        numElected = 0;

        firstCandidate = null;
    }

	/**
	 * isEmpty()
	 * 
	 * @return boolean -- whether the collection is empty
	 * 
	 * Precondition: None
	 * Postcondition: True is returned if the Collection is empty; false is
     *                  returned otherwise.
	 * Informally: Check whether the Collection is empty.
	 */
    public boolean isEmpty()
    {
       if (firstCandidate == null)
       {
        return true;
       }
        else
        {
        return false;
        }
    }

    /**
     * 
     * getElectorateName()
     * @return String -- the name of the electorate
     * 
     * Precondition: None
     * Postcondition: the name of the electorate is returned if there have
     *                  been votes added, "" is returned otherwise
     * Informally: Get the name of the electorate
     */
    public String getElectorateName()
    {
        String result;  // value to be returned
        
        result = ""; // assume the electorate is yet to be initialised

        if (!isEmpty())
        {
            // (some) votes already stored, so name already defined
            result = electorateName;
        }

        // yield name (if known)
        return result;
    }

    /**
	 * addBallotToCollection()
	 * 
	 * @param votes Ballot -- the ballot paper to add to this collection
	 * 
	 * Precondition: The given ballot parameter has been constructed
	 * Postcondition: The given Ballot has been added to the Collection and,
     *                  in particular to the appropriate cluster of ballots
     *                  based on the selected choice.  Additionally, if this
     *                  Ballot is the first one to be added, then the names
     *                  of the candidates are set (i.e. the clusters are
     *                  initialised) and the name of the electorate is
     *                  stored.
	 * Informally: Add a ballot to the appropriate 'candidate' in the
     *                  Collection, updating the electorate and candidate 
     *                  details.
	 */
    public void addBallotToCollection(Ballot votes)
    {
      // if this is the first ballot, then we have to set up all the clusters
      if (isEmpty())
      {
        electorateName = votes.getElectorate();

        String[] names = votes.getVotes();

         for (int i = 0; i < names.length; i++)
         {
            Cluster newCluster = new Cluster(names[i]);
            Node newNode = new Node(newCluster);

            if (firstCandidate == null)
            {
                firstCandidate = newNode;
            }
            else
            {
                Node last = firstCandidate;
                while (last.getNext() != null)
                {
                    last = last.getNext();
                }
                last.setNext(newNode);
                
            }
            numCandidates = numCandidates +1;
        }
    }


        // now find the cluster that matches this ballot's current choice
        String wantedName = votes.getSelection();
        Node current = firstCandidate;
        boolean found = false;

        for (int i = 0; i < numCandidates && !found; i++)
        {
        
           Cluster thisCluster = (Cluster) current.getData();

           if (thisCluster.getBundleName().equals(wantedName))
           {
            thisCluster.addBallotToCluster(votes);
            numBallots = numBallots +1;
            found = true;
           }
           else 
           {

           current = current.getNext();
           }
        }
    }

    /**
     * validCandidate()
     * 
     * @param name String -- the name of the candidate to search for
     * @return boolean -- whether or not the give candidate was found
     * 
     * Precondition: The given String is not null
     * Postcondition: True is returned if the name is found as the name
     *                  of one the clusters, and false otherwise.  The
     *                  comparison should be case insensitive.
     * Informally: Check whether the given candidate is still 'live' in
     *                  the election, i.e. not elected and not
     *                  eliminated
     */
    public boolean validCandidate(String name)
    {
        int i;          // index for iterating through candidates
        Node current;   // loop counter through candidates

        // find location of chosen candidate in the candidates linked-list
        i = 0;
        current = firstCandidate;
        while ((i < numCandidates) && (! name.equalsIgnoreCase(((Cluster)(current.getData())).getBundleName())))
        {
            // this candidate isn't the desired candidate, go to the next one
            i++;
            current = current.getNext();
        }

        // return whether search found the candidate or not
        return (i < numCandidates);
    }

    /**
	 * showDistribution()
	 * 
	 * Precondition: None
	 * Postcondition: The Collection is traversed cluster by cluster.  A
     *                  row comprising cluster name (candidate), a star for
     *                  each 150 votes for that candidate, and the total
     *                  number of votes in the cluster is printed.  The
     *                  message "No data!" should be printed if the Collection 
     *                  is empty.
	 * Informally: Print the horizontal histogram of ballots per candidate
	 */
    public void showDistribution()
    {
        final int VOTES_PER_STAR = 150;
        Node current;

        System.out.println("\nVote Distribution for" + getElectorateName() + ":\n");

        if (isEmpty())
        {
            System.out.println("No data!");
        }
        else
        {
             current = firstCandidate;
             
            while (current !=null)
            {
                Cluster thisCluster = (Cluster) current.getData();

                int numberOfStars = thisCluster.getRawCount() / VOTES_PER_STAR;

                System.out.print(thisCluster.getBundleName() + "\t|");
                for (int i = 0; i < numberOfStars; i++)
                {
                    System.out.print("*");
                }
                System.out.println(" " + thisCluster.getRawCount() + " (" + thisCluster.getWeightedCount() + ")");
                
                current = current.getNext();
                }
            }
        }
    

    /**
	 * showCandidateVotes()
	 * 
     * @param candidate String -- the name of the candidate for whom the
     *                  vote histogram is to be shown
     * 
	 * Precondition: The given String is not null
	 * Postcondition: The Collection is traversed cluster by cluster.  Each
     *                  cluster is searched for the given candidate and a
     *                  tally is kept of the count of #1 votes for the
     *                  candidate, #2 votes, etc.  When this counting is
     *                  concluded, a row for each preference is printed,
     *                  with a row of stars printed for each 150 votes for
     *                  that preference, and the total number of votes of
     *                  that preference is also printed.  The message 
     *                  "No data!" should be printed if the Collection is
     *                  empty.
	 * Informally: Print the horizontal histogram of ballots per preference
     *                  for the given candidate
	 */
    public void showCandidateVotes(String candidate)
    {
       final int VOTES_PER_STAR = 150;

       System.out.println("\nRaw Vote Allocation for " + candidate + ":\n");
    
      
       if (isEmpty())
       {
        System.out.println("no data!");
       }
       else
       {
         int[] tally = new int[MAX_CANDIDATES];

        Node current = firstCandidate;
        while (current != null)
        {
            Cluster thisCluster = (Cluster) current.getData();

            for (int p = 1; p <= MAX_CANDIDATES; p++)
            {
                tally[p-1] = tally[p - 1] + thisCluster.votesFor(candidate, p);
            }
            current = current.getNext();
            }

            for (int p = 0; p < MAX_CANDIDATES; p++)
            {
                if (tally[p] > 0)
                {
                    int numberOfStars = tally[p] / VOTES_PER_STAR;

                    System.out.print("preference" + ( p +1) + "\t|");
                    
                    for (int i = 0; i < numberOfStars; i++)
                    {
                        System.out.print("*");
                    }
                    System.out.println(" " + tally[p]);


                    }
                }
            }
    }    
    /**
	 * closeElection()
	 * 
	 * Precondition: None
	 * Postcondition: The quota of votes required to be elected is
     *                  calculated
	 * Informally: Determine the quota required for election now
     *                  that voting has closed
	 */
    // This method should be called once, after all ballots have been read in.
    public void closeElection()
    {
        // quota formula: total ballots divided by (seats + 1), plus 1, rounded down
        quota = numBallots / (NUMBER_TO_BE_ELECTED + 1) + 1;
    }

    /**
	 * maxVotes()
	 * 
     * @return double [] -- the count of votes
     * 
	 * Precondition: The Collection is validly defined
	 * Postcondition: The Collection is traversed cluster by cluster.
     *                  The weighted number of votes for the 'current'
     *                  candidate for each cluster is obtained and the
     *                  maximum of these is returned in element 1 of the
     *                  result array.  Element 0 holds the index of the
     *                  cluster from which the maximum comes.
	 * Informally: Find the maximum (weighted) count of votes received
     *                  by all the candidates still in the election to
     *                  determine whether a candidate is elected.
	 */
    protected double []maxVotes()
    {
        // final local variables
        final int UNDEFINED = -1;   // a sentinel to indicate that there is no maximum yet
        final int CANDIDATE = 0;    // the candidate index is element 0
        final int VALUE = 1;        // the vote weight value is element 1

        // non-final local variables
        double []result = {UNDEFINED, UNDEFINED};   // the array holding the pair of candidate index and vote weight
        double max = 0;                             // the maximum weight found
        int maxCandidate = 0;                       // the candidate cluster corresponding to the maximum vote weight
        Node curr;                                  // current node in linked list
        
        // search all the current clusters for the weight of the candidate with the most votes
        curr = firstCandidate;
        for (int i = 0; i < numCandidates; i++)
        {
            if (((Cluster)(curr.getData())).getWeightedCount() > max)
            {
                // found a new maximum so remember it and remember who
                max = ((Cluster)(curr.getData())).getWeightedCount();
                maxCandidate = i;
            }

            curr = curr.getNext();
        }

        // store answer in the result and return it
        result[CANDIDATE] = maxCandidate;
        result[VALUE] = max;

        return result;
    }

    /**
	 * minVotes()
	 * 
     * @return double [] -- the count of votes
     * 
	 * Precondition: The Collection is validly defined
	 * Postcondition: The Collection is traversed cluster by cluster.
     *                  The weighted number of votes for the 'current'
     *                  candidate for each cluster is obtained and the
     *                  minimum of these is returned in element 1 of the
     *                  result array.  Element 0 holds the index of the
     *                  cluster from which the minimum comes.
	 * Informally: Find the minimum (weighted) count of votes received
     *                  by all the candidates still in the election to
     *                  determine whether a candidate is eliminated.
	 */
    protected double []minVotes()
    {
        // final local variables
        final int UNDEFINED = -1;   // a sentinel to indicate that there is no minimum yet
        final int CANDIDATE = 0;    // the candidate index is element 0
        final int VALUE = 1;        // the vote weight value is element 1

        // non-final local variables
        double []result = {UNDEFINED, UNDEFINED};   // the array holding the pair of candidate index and vote weight
        double min = Integer.MAX_VALUE;             // the minimum weight found
        int minCandidate = 0;                       // the candidate cluster corresponding to the minimum vote weight
        Node curr;                                  // current node in linked list
        
        // search all the current clusters for the weight of the candidate with the least votes
        curr = firstCandidate;
        for (int i = 0; i < numCandidates; i++)
        {
            if (((Cluster)(curr.getData())).getWeightedCount() < min)
            {
                // found a new minimum so remember it and remember who
                min = ((Cluster)(curr.getData())).getWeightedCount();
                minCandidate = i;
            }

            curr = curr.getNext();
        }

        // store answer in the result and return it
        result[CANDIDATE] = minCandidate;
        result[VALUE] = min;

        return result;
    }

    /**
	 * inTheRunning()
	 * 
     * @param candidate String -- the name of the candidate for whom the
     *                  enquiry is being made
     * 
	 * Precondition: The given String is not null
	 * Postcondition: The Collection is traversed cluster by cluster.  Each
     *                  cluster is checked to see whether the given candidate
     *                  is the 'current' candidate in a cluster.  True is
     *                  returned if they are, false is returned if they are
     *                  not.
	 * Informally: Determine whether the given candidate is still in the
     *                  running for a vacancy.
	 */
    protected boolean inTheRunning(String candidate)
    {
        int i;      // loop counter of clusters
        Node curr;  // current node in linked list

        // search the bundles to see whether one of them is the given candidate
        i = 0;
        curr = firstCandidate;
        while ((i < numCandidates) && (! ((Cluster)(curr.getData())).getBundleName().equals(candidate)))
        {
            // not this one, try the next
            i++;
            curr = curr.getNext();
        }

        // return whether the candidate was found
        return (i < numCandidates);
    }

    /**
	 * transfer()
	 * 
     * @param index int -- the index of the cluster to transfer the votes
     *                  from
     * @param residual double -- the weight of each vote once transferred
     * 
	 * Precondition: index is within the Collection and residual is a non-
     *                  negative number
	 * Postcondition: All votes from the given candidate's bundle (cluster)
     *                  are updated to their next preference, re-weighted if
     *                  appropriate, deleted from the current bundle, and,
     *                  if not exhausted, added to the bundle of the next
     *                  preference's candidate
	 * Informally: Move ballots from the current eliminated/elected
     *                  candidate to the next preference for each ballot
	 */
    protected void transfer(int index, double residual)
    {
       Node previous = null;
       Node current = firstCandidate;

       for (int i = 0; i < index; i++)
       {
        previous = current;
        current = current.getNext();
       }
       Cluster clusterToEmpty = (Cluster) current.getData();

       while (! clusterToEmpty. isEmpty())
       {
         Ballot movedBallot =  clusterToEmpty.transfer(residual);

         if (! movedBallot.exhausted())
         {
            addBallotToCollection(movedBallot);
         }
    }
     
    if (previous == null)
    {
      firstCandidate = current.getNext();
    }
    else
    {
        previous.setNext(current.getNext());
    }
        numCandidates = numCandidates - 1;
}

    /**
	 * elect()
	 * 
     * @param index int -- the index of the cluster to transfer
     * 
	 * Precondition: The given int is an index to a candidate in the Collection
	 * Postcondition: The given candidate has been elected and so the
     *                  surplus (excess votes beyond the quota) is
     *                  calculated, the residual weighting of this surplus
     *                  is calculated, and the ballots moved from this
     *                  candidate to the next preference of each ballot at
     *                  the residual weighting.
	 * Informally: Elect the candidate, moving a residual proportion of
     *                  the vote to the next preference.
	 */
    protected void elect(int index)
    {
        double surplus;     // the excess votes for this candidate beyond the quota
        double residual;    // the weighting of each vote based on the surplus in proportion to the quota
        Node curr;          // current node in linked list

        // find candidate
        curr = firstCandidate;
        for (int i = 0; i < index; i++)
        {
            curr = curr.getNext();
        }

        // calculate any surplus
        surplus = ((Cluster)(curr.getData())).getWeightedCount() - quota;
        // calculate any residual value of the votes
        residual = surplus / ((Cluster)(curr.getData())).getWeightedCount();

        //System.out.println("candidate: " + index + " surplus: " + surplus + " residual: " + residual);

        // transfer the votes from this candidate to the candidate(s) of the next preference, if there are any
        transfer(index, residual);

        // increase the count of elected candidates
        numElected++;
    }

    /**
	 * eliminate()
	 * 
     * @param index int -- the index of the cluster to transfer
     * 
	 * Precondition: The given int is an index to a candidate in the Collection
	 * Postcondition: The given candidate has been eliminated and so all
     *                  the ballots are moved from this candidate to the
     *                  next preference candidate of each ballot at full
     *                  weighting.
	 * Informally: Eliminate the candidate, moving all votes to the
     *                  voter's next preference candidate at full value.
	 */
    protected void eliminate(int index)
    {
        final int FULL = -1;    // sentinel indicating that the weight is to be full and not proportional

        // move all ballots from this cluster to their next preference at full value
        transfer(index, FULL);
    }

    /**
	 * distributePreferences()
	 * 
     * @return String -- the outcome of the round of preference distribution
     * 
	 * Precondition: None
	 * Postcondition: One of three outcomes has occurred:
     *                  - if there are no candidates then the message 
     *                      "No data!" should be printed and "" returned.
     *                  - if a candidate is found to have reached/exceeded
     *                      quota then they are elected, removed from the
     *                      collection, and if there were surplus votes
     *                      then all their votes are redistributed at the
     *                      proportion of surplus/total, otherwise the
     *                      votes are discarded. A String describing the
     *                      outcome is returned.
     *                  - the candidate with the minimum number of votes
     *                      is found and removed from the election, and
     *                      all of their next preference votes are 
     *                      distributed to the corresponding candidate 
     *                      (if they're still in the election) or 
     *                      discarded if the ballot is exhausted. A
     *                      String describing the outcome is returned.
	 * Informally: Identify the next candidate elected or eliminated and 
     *                  redistribute their votes.
	 */
    public String distributePreferences()
    {
      final int CANDIDATE = 0;
      final int VALUE = 1;

      String result = "";
      if (isEmpty())
      {
          System.out.println("No data!");
      }
      else if (numElected < NUMBER_TO_BE_ELECTED)
      {
        double[] max = maxVotes();

        Node current = firstCandidate;
        for (int i = 0; i < (int) max[CANDIDATE]; i++)
        {
            current = current.getNext();
        }
        String topName = ((Cluster) current.getData()).getBundleName();

        boolean seatsMatchCandidates = (numCandidates == (NUMBER_TO_BE_ELECTED - numElected));

        if ((max[VALUE] >= quota) || seatsMatchCandidates)
        {
            result = topName + "is ELECTED with" + (int) max[VALUE] + "/" + quota + "weighted votes!";
            elect((int) max[CANDIDATE]);

        }
        else
        {
            double[] min = minVotes();

            Node current2 = firstCandidate;
            for (int i = 0; i < (int) min[CANDIDATE]; i++)
            {
                current2 = current2.getNext();
            }
            String bottomName = ((Cluster) current2.getData()).getBundleName();

            result = bottomName + "is ELIMINATED with " + (int) min[VALUE] + "/" + quota + "weighted votes!";
            eliminate((int) min[CANDIDATE]);
        }
    }

      return result;
    }

	/**
	 * toString()
	 * 
	 * @return String -- printable form of the Collection of ballots
	 * 
	 * Precondition: None
	 * Postcondition: A printable (String) form of the ballot data is
     *                  returned.  If there are no known candidates 
     *                  then "No Data!" is returned.
	 * Informally: Convert the Collection of ballot data to a multi-
     *                  line String.
	 */
    public String toString()
    {
        String result;  // result
        Node curr;      // current node in linked list

        // build String form of Collection
        if (isEmpty())
        {
            // no candidates!
            result = "No data!";
        }
        else
        {
            // there are candidates so provide election summary
            result = "Electorate: " + getElectorateName() + "\n";
            result += "Quota: " + quota + "\n";
            result += "Number of vacancies: " + NUMBER_TO_BE_ELECTED + "\n";
            result += "Number of Candidates: " + numCandidates + "\n";
            result += "Number of Ballots: " + numBallots + "\n";
            if (numBallots > 0)
            {
                // there are ballots so provide raw count of votes for all candidates
                result += "\nCandidates:\n";
                curr = firstCandidate;
                for (int i = 0; i < numCandidates; i++)
                {
                    // add printable form of candidate name and votes to the result
                    result += "\t" + ((Cluster)(curr.getData())).getBundleName() + " (" + ((Cluster)(curr.getData())).getRawCount() + ")\n";
                    curr = curr.getNext();
                }
            }
        }

        return result;
    }
}