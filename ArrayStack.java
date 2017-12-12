 /**
  * Implementation of a generic stack ADT using a fixed-length array.
  * Stack: a collection of objects that are inserted
  * and removed according to the last-in first-out (LIFO) principle.
  */

public class ArrayStack<E> {
  protected int capacity; 	                 // The actual capacity of the stack array
  public static final int CAPACITY = 1000;	 // default array capacity
  protected E S[];		                        // Generic array used to implement the stack 
  protected int top = -1;	                    // index for the top of the stack


  public ArrayStack() {
    this(CAPACITY); // default capacity 
  }

  public ArrayStack(int cap) {
    capacity = cap;
    S = (E[]) new Object[capacity]; // compiler may give warning, but this is ok
  }

  /**
  * Return the number of elements in the stack.
  * @return number of elements in the stack. 
  */
  public int size() { 
    return (top + 1);
  }

  /** 
  * Return whether the stack is empty.
  * @return true if the stack is empty, false otherwise. 
  */
  public boolean isEmpty() {
    return (top < 0);
  }

  /**
  * Insert an element at the top of the stack.
  * @param element to be inserted.
  */
  public void push(E element) {
    if (size() < capacity)  //Make sure the stack is not full
      S[++top] = element;
  }

  /** 
  * Inspect the element at the top of the stack.
  * @return top element in the stack, null if empty.  
  */
  public E top() {
    if (isEmpty())
      return null;
    return S[top];
  }

  public E peek(int n) {
    return S[n];
  }

  /** 
  * Remove the top element from the stack.
  * @return element removed, null if empty.
  */
  public E pop() {
    if (isEmpty())
      return null;
    E element = S[top];
    S[top--] = null;      // dereference S[top] for garbage collection.
    return element;
  }
  
  /**
  * Returns a string representation of the stack contents
  */
  public String toString() {
    String s;
    s = "[";
    if (size() > 0) s+= S[0];
    if (size() > 1)
      for (int i = 1; i <= size()-1; i++) {
	       s += ", " + S[i];
      }
    return s + "]";
  }


 /**
  * Test the class by performing a series of operations
  */
  public static void main(String[] args) {
    int i;
    ArrayStack<Integer> A = new ArrayStack<>(15);  //A stack of size 15 of integers
    A.push(7);
    A.push(6);
    i = A.pop();
    A.push(9);
    i = A.pop();
    System.out.println(A);

    ArrayStack<Character> B = new ArrayStack<>();    //A stack of size 1000 of characters
    char s;
    B.push('B');
    B.push('>');
    s = B.pop();
    B.push('5');
    System.out.println(B);
  }
}