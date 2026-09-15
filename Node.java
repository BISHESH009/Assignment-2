/**
 * Node.java
 * 
 * KIT107 Assignment 2 -- Linked List Node Implementation
 * 
 * @author Julian Dermoudy
 * @version	20/7/2026
 * 
 * FILE IS COMPLETE
 */


public class Node implements NodeInterface
{
	// instance variables
	protected Object data;	// data field
	protected Node next;	// next field

	/**
	 * Constructor
	 * 
	 * @param o Object -- object to be stored in node
	 * 
	 * Precondition: None
	 * Postcondition: The new instance will have its data field assigned the
	 * 					given value and its next field assigned null.
	 * Informally: Initialise a node.
	 */
	public Node(Object o)
	{
		data = o;
		next = null;
	}

	/**
	 * setData()
	 * 
	 * @param o Object -- object to be stored in data field of node
	 * 
	 * Precondition: None
	 * Postcondition: the current object's data field is assigned the given
	 * 					value.
	 * Informally: Set the data field.
	 */
	public void setData(Object o)
	{
		data = o;
	}
	
	/**
	 * setNext()
	 * 
	 * @param n Node -- node to be stored in next field of node
	 * 
	 * Precondition: None
	 * Postcondition: the current object's next field is assigned the given
	 * 					value.
	 * Informally: Set the next field.
	 */
	public void setNext(Node n)
	{
		next = n;
	}

	/**
	 * getData()
	 * 
	 * @return Object -- the value in the data field
	 * 
	 * Precondition: None
	 * Postcondition: the current object's data field is returned.
	 * Informally: Get the data field.
	 */
	public Object getData()
	{
		return data;
	}

	/**
	 * getNext()
	 * 
	 * @return Node -- the value in the next field
	 * 
	 * Precondition: None
	 * Postcondition: the current object's next field is returned.
	 * Informally: Get the next field.
	 */
	public Node getNext()
	{
		return next;
	}
}
