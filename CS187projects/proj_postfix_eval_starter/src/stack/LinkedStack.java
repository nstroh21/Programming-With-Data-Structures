package stack;

/**
 * A {@link LinkedStack} is a stack that is implemented using a Linked List structure to allow for
 * unbounded size.
 *
 * @param <T> the elements stored in the stack
 */
public class LinkedStack<T> implements StackInterface<T> {

  private LLNode<T> head = null;

  /** {@inheritDoc} */
  @Override
  public T pop() throws StackUnderflowException {
    if (this.isEmpty() == true) {
      throw new StackUnderflowException("The stack is empty");
    }
    else{
      LLNode<T> next = this.head.getNext();
      LLNode<T> curr = this.head;
      this.head = next;
      return curr.getData();
    }
  }

  /** {@inheritDoc} */
  @Override
  public T top() throws StackUnderflowException {
    if (this.isEmpty() == true) {
      throw new StackUnderflowException("The stack is empty");
    }
    else{ 
      return this.head.getData();
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean isEmpty() {
   return (this.head == null);
    /*if (this.head == null) {
      return true;
    }
    else{
      return false;
    }*/
  }

  /** {@inheritDoc} */
  @Override
  public int size() {
    int count = 0;
    LLNode<T> curr = this.head;
    while (curr != null){
      count++;
      curr = curr.getNext();
    }
    return count;
  }

  /** {@inheritDoc} */
  @Override
  public void push(T elem) {
    
    //create a node for our input item
    LLNode<T> pushNode = new LLNode<T>(elem);
    //edge case the list is empty
    if (this.isEmpty() == true) {
      this.head = pushNode;
    }
    else{
      pushNode.setNext(this.head);
      this.head = pushNode;
    }    
  }
}
