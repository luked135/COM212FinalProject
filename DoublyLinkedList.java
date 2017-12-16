/**
* A DoublyLinkedList class, holding a list of DNodes
* This is modified code from Project #1, changed to create strings instead of gamescores
*/

public class DoublyLinkedList {
	//Instance Variables
	private DNode header;		//The header sentinel DNode
	private DNode trailer;		//The trailer sentinel DNode
	private int size;			//The number of nodes in the list

	/**
	* Constructs an initially empty list
	* Creates and links the sentinel nodes to each other
	*/
	public DoublyLinkedList() {
		header = new DNode(null, null, null);		//create header
		trailer = new DNode(null, header, null);	//trailer is preceded by header
		header.setNext(trailer);					//header is followed by trailer
		size = 0;
	}

	/**
	* This method finds the number of nodes in the list
	* @return the size of the list
	*/
	public int size() {
		return size;
	}

	/**
	* Checks if the list is empty
	* @Return true if the list is empty, false otherwise
	*/
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	* Finds the element that is stored at the node after the header
	* @return the first element of the list, without removing it
	*/
	public String first() {
		if (isEmpty()) {
			return null;				//If the list is empty
		}
		//The first node is the node that comes after the header
		return header.getNext().getElement();
	}

	/**
	* Finds the last element in the list
	* @return the last element of the list, without removing it
	*/
	public String last() {
		if (isEmpty()) {
			return null;				//If the list is empty
		}
		//The last node is the node that comes before the trailer
		return trailer.getPrev().getElement();
	}

	/**
	* Adds an element to the front of the list
	* @param n The element to be added
	*/
	public void addFirst(String n) {
		addBetween(n, header, header.getNext());		//Insert just after the header
	}

	/**
	* Adds an element to the end of the list
	* @param n The element to be added
	*/
	public void addLast(String n) {
		addBetween(n, trailer.getPrev(), trailer);	//Insert just before the trailer
	}

	/**
	* Removes and returns the first element of the list
	* @return the string that is removed
	*/
	public String removeFirst() {
		if (isEmpty())
			return null;					//Nothing to remove or return
		else {
			return remove(header.getNext());
		}
	}

	/**
	* Removes and returns the last element of the list
	* @return the string that is removed
	*/
	public String removeLast() {
		if (isEmpty())
			return null;					//Nothing to remove or return
		else {
			return remove(trailer.getPrev());
		}
	}


	/**
	 * Displays the list contents
	 */
	public void display() {
		DNode current = header.getNext();		//Start with the node after header, potential first node
		//Walk down the list until you reach the trailer node
		while (current.getElement() != null) {      			// for each node,
			System.out.print(current + " ");  					// display it using the DNode.toString() method
			current = current.getNext();						// Fetch the next node in the list
		}
		System.out.println("");
	}

	/**
	* Adds the newScore in between the given nodes
	* @param n The string being added to the list
	* @param prevNode the node the new string goes after
	* @param nextNode the node the new string comes before
	*/
	private void addBetween(String n, DNode prevNode, DNode nextNode) {
		DNode newNode = new DNode(n, prevNode, nextNode);	//Create and link the new node
		//Update the given nodes to link to the new node
		prevNode.setNext(newNode);
		nextNode.setPrev(newNode);
		size++;
	}

	/**
	* Removes the given node from the list and returns its element
	* @param xNode The node being removed from the list
	* @return The removed node
	*/
	private String remove(DNode xNode) {
		//Get the nodes on either side of xNode
		DNode prevNode = xNode.getPrev();
		DNode nextNode = xNode.getNext();
		//Link prevNode and nextNode to each other, essentially removing xNode from the list
		prevNode.setNext(nextNode);
		nextNode.setPrev(prevNode);
		size--;
		return xNode.getElement();
	}

	/**
	* Finds a given node in the list
	* Searches for a string that the node contains
	* @param target The string being searched for
	* @return The node containing the string if it is found, null if no matching string is found
	*/
	public DNode find(String target) {
		DNode prev = header;
		DNode current = prev.getNext();
		
		while (current !=trailer) {		//Keeps checking until it reaches the end of the list
			if (current.getElement().equals(target)) {		//Checks if the two values are equal
				return current;
			}
			else { 
				current = current.getNext();		//Moves one item over to keep checking
			}
		}
		return null;		//This is outside the while loop, meaning the whole list has been checked without finding target

	}

	/**
	* Adds a new node containing a string to the end of the list
	* @param s The string that is to be contained within the new node
	*/
	public void add(String s){ 
		DNode prev = header;
		DNode current = prev.getNext(); 
			
		while (current.getElement() != null){
			//The first half of the conditional is checking to make sure the end of the list hasn't been reached
			//The second half of the conditional is checking if the right score position has been found for sorting

			prev = current;
			current = current.getNext();		//The pointers are moved one over
		}

		if (prev == null) {
			addFirst(s);		//Adds to the beginning of the list
		}

		else if (current == null) {
			addLast(s);		//Adds to the end of the list
		}

		else {
			DNode newNode = new DNode(s, current.getNext(), current.getPrev());
			addBetween(s, prev, current);		//Positions the new node between the two surrounding nodes, bigger and smaller
		}

	}

	/**
	* Deletes and returns a node containing a given string
	* @param s The string the target node should contain
	* @return The removed node if a match is found, null otherwise
	*/
	public DNode delete(String s) { 
		DNode prev = header;
		DNode current = prev.getNext();    

		while (current.getElement() !=null && !current.getElement().equals(s)) {
			prev = current;
			current = current.getNext();		//Keeps checking for equivalance, then moving to the next nodes
		}

		if (current == trailer) {
			return null;		//This occurs if the list is checked without finding gameScore, i.e. no result found
		}
		
		else if (prev == header) {
			removeFirst();
			return current;		//This occurs if the first item needs to be removed
		}

		else {
			remove(current);
			return current;
		}
	}
}
