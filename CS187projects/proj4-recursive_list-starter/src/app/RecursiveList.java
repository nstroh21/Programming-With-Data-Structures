package app;

import java.util.Iterator;

public class RecursiveList<T> implements ListInterface<T> {

  private int size;
  private Node<T> head = null;

  public RecursiveList() {
    this.head = null;
    this.size = 0;
  }

  public RecursiveList(Node<T> first) {
    this.head = first;
    this.size = 1;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void insertFirst(T elem) {  
    if (elem == null){
      throw new NullPointerException("Cannot insert null element into list");
    }
    //shouldn't need an extra case where head is null because these next lines impicitly considers that
    Node<T> newHead = new Node<T>(elem, head);
    this.head = newHead;
    size ++;
    //System.out.println("it worked?");  
  }

  @Override
  public void insertLast(T elem) {
      Node<T> newNode = new Node<T>(elem, null);
      if (elem == null) {
        throw new NullPointerException("Cannot insert null element into list");
      }
      else if (head == null){
        this.head = newNode;
        size++;
      }
      else{
        Node<T> tail = findNode(size-1, this.head);
        tail.setNext(newNode);
        size++;
      }
  
  }

  @Override
  public void insertAt(int index, T elem) {
      //throw exceptions if we have invalid input
      if (elem == null) {
        throw new NullPointerException("Cannot insert null element into list");
      }
      if (index < 0 || index > size) {
        throw new IndexOutOfBoundsException("Invalid index");
      }
      //use findNode helper method  -- consider edge case when list is empty or insertAt 0
      if (index == 0){
        Node<T> newNode = new Node<T>(elem, this.head);
        this.head = newNode;
        size++;
      }  // the following else block will also handle edge case insertAT end
      else {
        Node<T> currNode = findNode(index-1, this.head);
        Node<T> nextNode = findNode(index, this.head);
        Node<T> newNode = new Node<T>(elem, nextNode);
        currNode.setNext(newNode);
        size++;
      }
  }

  @Override
  public T removeFirst() {

    T removedItem = null;
    // throw exception if the list is already empty
    if (this.head == null) {
      throw new IllegalStateException("Cannot remove item from an empty list");
    }
    else if (size == 1) {
      removedItem = this.head.getData();
      this.head = null;
      size = 0;
    }
    else {
      Node<T> currHead = this.head;
      Node<T> newHead = currHead.getNext();
      removedItem = currHead.getData();
      this.head = newHead;
      size--;
    }
      return removedItem;
  }

  @Override
  public T removeLast() {

    T removedItem = null;
    // throw exception if the list is already empty
    if (size == 0 && this.head == null) {
      throw new IllegalStateException("Cannot remove item from an empty list");
    }
    else if (size == 1){
      removedItem = this.head.getData();
      this.head = null;
      size = 0;
    }
    else{
      Node<T> tail = findNode(size-1, this.head);
      Node<T> newTail = findNode(size-2, this.head);
      removedItem = tail.getData();
      newTail.setNext(tail.getNext());
      size--;
    }
    
    return removedItem;
  }

  @Override
  public T removeAt(int i) {
    //exception
    T removedItem = null;
    if ((i < 0) || (i>=size)) {
      throw new IndexOutOfBoundsException("Invalid index");
    }
    
    /*else if ((size == 0) && (this.head == null)) {
      //throw new IllegalStateException("Cannot remove item from an empty list");
      //instead of throwing exception return the null item
      return removedItem;
    }*/
    else{ 
      if (i == 0){
        Node<T> curr = this.head;
        removedItem = curr.getData();
        this.head = curr.getNext();
        size--;
      }
      else{
        //use findNode helper here -- we need i-1, i
        Node<T> last = findNode(i-1, this.head);
        Node<T> curr = findNode(i, this.head);
        removedItem = curr.getData();
        last.setNext(curr.getNext());
        size--;
      }
      return removedItem;
    }
  }

  @Override
  // use the helper findNode
  public T getFirst() {
    //exception if list is empty
    if (size == 0 && this.head == null) {
      throw new IllegalStateException("Cannot get first item from empty list");
    }
    T item = this.head.getData();
    return item;
  }

  @Override
  //use the fact that head node is a class attribute
  public T getLast() {
    if (size == 0 && this.head == null) {
      throw new IllegalStateException("Cannot get last item from empty list");
    }
    Node<T> tail = findNode(size-1, this.head);
    T item = tail.getData();
    return item;
  }

  @Override
  public T get(int i) {

    T item = null;
    if (i < 0 || i >= this.size) {
      throw new IndexOutOfBoundsException("Invalid input: no item found at that index");
    }
    else{
      item = findNode(i, this.head).getData();
    }      
    return item;
  }

  @Override
  public void remove(T elem) {
      // null imput exception
      if (elem == null) {
        throw new NullPointerException("The input item was not found in this list");
      }
      // use indexOf method to get the index and work from there
      int index = indexOf(elem);
      if (index == -1) {
        throw new ItemNotFoundException("The input item was not found in this list");
      }
      else if (index == 0) {
         Node<T> removedItem = this.head;
         this.head = removedItem.getNext();
         size --;
      } 
      else{
        Node<T> removeNode = findNode(index, this.head);
        Node<T> beforeNode = findNode(index-1, this.head);
        beforeNode.setNext(removeNode.getNext());
        size--;
      }

  }


  @Override
  public int indexOf(T elem) {
    int index = -1;
    Node <T> curr = this.head;
    if (elem == null) {
      throw new NullPointerException("Search element cannot be null");
    }
    else if (curr == null) {
      index = -1;
    }
    else{
      index = indexOfHelper(elem, index+1, curr);
      //System.out.println(index);
    }
    return index;
  }

  // helper for the indexOf() method
  private int indexOfHelper (T elem, int i, Node<T> node) {
    
    if (node == null) {
      i = -1;
    }
    else if ((node != null) && (node.getData() != elem) ) {
      i++;
      return indexOfHelper(elem, i, node.getNext());
      //when I didn't put return statement here this acted SUPER funky with return
      // it wasn't returning back up the stack i guess
    }
    //System.out.println(index);
    return i;
  }
  
  //helper for the get method and potentially elsewhere ?
  private final Node<T> findNode(int distance, Node<T> curr) {
    if (distance < 0){
      throw new IllegalStateException("distance must be 0 or positive");
    }
    if (distance == 0) {
      return curr;
    }
    else {
      return findNode(distance - 1, curr.getNext());
    }
  }

  @Override
  public boolean isEmpty() {
    boolean empty = false;
    if (this.head == null) {
      empty = true;
    }

    return empty;
  }


  public Iterator<T> iterator() {
    Iterator<T> iter = new LinkedNodeIterator<T>(this.head);
    return iter;
  }
}
