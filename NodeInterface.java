/**
 * NodeInterface.java
 * 
 * KIT107 Assignment 2 -- Linked List Node Specification
 * 
 * @author Julian Dermoudy
 * @version	20/7/2026
 * 
 * FILE IS COMPLETE
 */


public interface NodeInterface
{
    // Constructor
	//public Node(Object o);

    // Accessor (Getter) methods
	public Object getData();
	public Node getNext();

    // Mutator (Setter) methods
	public void setData(Object o);
	public void setNext(Node n);
}
