package priorityqueue;

import java.util.Comparator;

public class Heap<T> implements PriorityQueueADT<T> {

  private int numElements;
  private T[] heap;
  private boolean isMaxHeap;
  private Comparator<T> comparator;
  private final static int INIT_SIZE = 5;

  /**
   * Constructor for the heap.
   * @param comparator comparator object to define a sorting order for the heap elements.
   * @param isMaxHeap Flag to set if the heap should be a max heap or a min heap.
   */
  public Heap(Comparator<T> comparator, boolean isMaxHeap) {
      heap = (T[]) new Object[INIT_SIZE]; 
      this.comparator = comparator;
      this.isMaxHeap = isMaxHeap;
      numElements = 0;
  }
  /**
   * This results in the entry at the specified index "bubbling up" to a location
   * such that the property of the heap are maintained. This method should run in
   * O(log(size)) time.
   * Note: When enqueue is called, an entry is placed at the next available index in 
   * the array and then this method is called on that index. 
   *
   * @param index the index to bubble up
   */
  public void bubbleUp(int index) {
    int parentIndex = (index-1)/2; 
    //check that its not root before proceeding -- if it is root/top-of-heap then do nothing
    if (parentIndex >= 0){
      int result = compare(heap[index], heap[parentIndex]);
      if (result > 0){
        //swaps nodes
        T store = heap[parentIndex];
        heap[parentIndex] = heap[index];
        heap[index] = store;
        //repeat
        bubbleUp(parentIndex);
      }
    } 
    // actualy look at the compare method  --> we don't need to consider the 2 different cases
    /*else{
      int parentIndex = (index-1)/2; 
      if (comparator.compare(heap[parentIndex], heap[index]) < 0){
          //swaps nodes
          T store = heap[parentIndex];
          heap[parentIndex] = heap[index];
          heap[index] = store;
          bubbleUp(parentIndex);
          //recurse again
          bubbleUp(parentIndex);
      }
    }*/
  } 

  /**
   * This method results in the entry at the specified index "bubbling down" to a
   * location such that the property of the heap are maintained. This method
   * should run in O(log(size)) time.
   * Note: When remove is called, if there are elements remaining in this
   *  the bottom most element of the heap is placed at
   * the 0th index and bubbleDown(0) is called.
   * 
   * @param index
   */
  public void bubbleDown(int index) { 
    int childIndex = 2*index + 1;
    T value = heap[index];
    // only execute if childNode exists
    if (childIndex <= this.size()){
      // if max heap push down when child >= parent else opposite condition
      T maxValue = heap[index];
      int maxIndex = index;
      //check for the larger of the two child nodes if there are 2 nodes
      for (int i = 0; i < 2 && childIndex + i < this.size(); i++){
        if (compare(heap[childIndex+i], maxValue) > 0){
          maxValue = heap[childIndex+i];
          maxIndex = childIndex + i;
        }
      } 
      if (maxValue != value){
        T store = heap[maxIndex];
        heap[maxIndex] = heap[index];
        heap[index] = store;
        // recurse on down with index updated to the maxIndex
        bubbleDown(maxIndex);
      }
    }
  }
  //helper methods
  private T getParent(int index){
    return heap[((index-1)/2)] ;
  }
  private T getLeftChild(int index){
    return heap[(2*index) + 1] ;
  }
  private T getRightChild(int index){
    return heap[(2*index) + 2] ;
  }
  private void swap(int index1, int index2){
    T storeIt = heap[index1];
    heap[index1] = heap[index2];
    heap[index2] = storeIt;
  }

  /**
   * Test for if the queue is empty.
   * @return true if queue is empty, false otherwise.
   */
  public boolean isEmpty() {
    boolean isEmpty = false;
      if(heap[0] == null){
        isEmpty = true;
      }
    return isEmpty;
  }

  /**
   * Number of data elements in the queue.
   * @return the size
   */
  public int size(){
    int size = numElements;
    return size;
  }

  /**
   * Compare method to implement max/min heap behavior.  It calls the comparae method from the 
   * comparator object and multiply its output by 1 and -1 if max and min heap respectively.
   * TODO: implement the heap compare method
   * @param element1 first element to be compared
   * @param element2 second element to be compared
   * @return positive int if {@code element1 > element2}, 0 if {@code element1 == element2}, negative int otherwise
   */
  public int compare(T element1 , T element2) {
    int result = 0;
    int compareSign =  -1;
    if (isMaxHeap) {
      compareSign = 1;
    }
    result = comparator.compare(element1, element2);
    return compareSign *result;
  }

  /**
   * Return the element with highest (or lowest if min heap) priority in the heap 
   * without removing the element.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T peek() throws QueueUnderflowException {
    if (heap[0] == null){
      throw new QueueUnderflowException("Priority Queue is empty");
    } else{
      return heap[0];
    }
  }  

  /**
   * Removes and returns the element with highest (or lowest if min heap) priority in the heap.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T dequeue() throws QueueUnderflowException{
    if (heap[0] == null){
      throw new QueueUnderflowException("Priority Queue is empty");
    }
    T data = heap[0];
    // to dequeue first swap the heap[0] with the last element in the heap
    swap(0, (numElements-1));
    //then erase the element by setting it null and shrinking the array (decrement numelements)
    heap[numElements -1] = null;
    numElements -= 1;
    // then bubble down the top guy to its proper spot
    bubbleDown(0);
    return data;
  }

  /**
   * Enqueue the element.
   * @param the new element
   */
  public void enqueue(T newElement) {
    if(this.size() + 1 > heap.length){
      expandCapacity();
    }
    // add the element to the end of the array, increment numelements
    heap[numElements] = newElement;
    //bubble the element up to its proper place in heap
    bubbleUp(numElements);
    numElements += 1;
  }

  private void expandCapacity(){
    int newSize = 2*heap.length;
    T[] newHeap = (T[]) new Object[newSize];
    for (int i = 0; i < this.size(); i++){
      newHeap[i] = heap[i];
    }
  heap = newHeap;
  }
}