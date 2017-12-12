/**
* A DoublyLinkedList class, holding a list of DNodes
* A DNode stores a GameScore element
*/

public class DoublyLinkedList {
	//Instance Variables
	private DNode header;		//The header sentinel DNode
	private DNode trailer;		//The trailer sentinel DNode
	private int size;			//The number of nodes in the list

	//Constructs an initially empty list
	//Creates and links the sentinel nodes to each other
	public DoublyLinkedList() {
		header = new DNode(null, null, null);		//create header
		trailer = new DNode(null, header, null);	//trailer is preceded by header
		header.setNext(trailer);					//header is followed by trailer
		size = 0;
	}

	//ACCESS METHODS

	//Returns the size, number of nodes in the list
	public int size() {
		return size;
	}

	//Returns true if the list is empty, false otherwise
	public boolean isEmpty() {
		return size == 0;
	}

	//Returns (but doesn't remove) the first element
	//That's the element stored at the node after the header
	public String first() {
		if (isEmpty()) {
			return null;				//If the list is empty
		}
		//The first node is the node that comes after the header
		return header.getNext().getElement();
	}

	//Returns (but doesn't remove) the last element
	//That's the element stored at the node before the trailer
	public String last() {
		if (isEmpty()) {
			return null;				//If the list is empty
		}
		//The last node is the node that comes before the trailer
		return trailer.getPrev().getElement();
	}

	//UPDATE METHODS

	//Adds the newScore element to the front of the list
	public void addFirst(String n) {
		//Use our private helper method
		addBetween(n, header, header.getNext());		//Insert just after the header
	}

	//Adds the newScore element to the end of the list
	public void addLast(String n) {
		//Use our private helper method
		addBetween(n, trailer.getPrev(), trailer);	//Insert just before the trailer
	}

	//Removes and returns the first element of the list
	public String removeFirst() {
		if (isEmpty())
			return null;					//Nothing to remove or return
		else {
			//Use our private helper method
			return remove(header.getNext());
		}
	}

	//Removes and returns the last element of the list
	public String removeLast() {
		if (isEmpty())
			return null;					//Nothing to remove or return
		else {
			//Use our private helper method
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




	//---------------------  OUR PRIVATE HELPER METHODS  --------------------

	//Adds the newScore in between the given nodes
	private void addBetween(String n, DNode prevNode, DNode nextNode) {
		DNode newNode = new DNode(n, prevNode, nextNode);	//Create and link the new node
		//Update the given nodes to link to the new node
		prevNode.setNext(newNode);
		nextNode.setPrev(newNode);
		size++;
	}

	//Removes the given node from the list and returns its element
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

	//--------------------------------------------------------------------------------------------

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

	public void add(String n){ 
		DNode prev = header;
		DNode current = prev.getNext(); 
			
		while (current.getElement() != null){
			//The first half of the conditional is checking to make sure the end of the list hasn't been reached
			//The second half of the conditional is checking if the right score position has been found for sorting

			prev = current;
			current = current.getNext();		//The pointers are moved one over
		}

		if (prev == null) {
			addFirst(n);		//Adds to the beginning of the list
		}

		else if (current == null) {
			addLast(n);		//Adds to the end of the list
		}

		else {
			DNode newNode = new DNode(n, current.getNext(), current.getPrev());
			addBetween(n, prev, current);		//Positions the new node between the two surrounding nodes, bigger and smaller
		}

	}

	public DNode delete(String gameScore) { 
		DNode prev = header;
		DNode current = prev.getNext();    

		while (current.getElement() !=null && !current.getElement().equals(gameScore)) {
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

	//--------------------------------------------------------------------------------------------