package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

import javax.swing.RootPaneContainer;


public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T> {
  protected BSTNode<T> root;

  public boolean isEmpty() {
    return root == null;
  }

  protected void setRoot(BSTNode<T> newRoot){
      this.root = newRoot;
  }

  public int size() {
    return subtreeSize(root);
  }

  protected int subtreeSize(BSTNode<T> node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + subtreeSize(node.getLeft()) + subtreeSize(node.getRight());
    }
  }

  public boolean contains(T t) {
    if (t == null) {
      throw new NullPointerException("Must pass a non-null element to contains()");
    }
    BSTNode<T> currNode = root;
    while (currNode != null){
      if (currNode.getData().equals(t) ){
        return true;
      }
      else if(t.compareTo(currNode.getData()) < 0){
        currNode = currNode.getLeft();
      }
      else {
        currNode = currNode.getRight();
      }
    }
    return false;
  }

  public boolean remove(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    boolean result = contains(t);
    if (result) {
      root = removeFromSubtree(root, t);
    }
    return result;
  }

  protected BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
    // node must not be null
    int result = t.compareTo(node.getData());
    if (result < 0) {
      node.setLeft(removeFromSubtree(node.getLeft(), t));
      return node;
    } else if (result > 0) {
      node.setRight(removeFromSubtree(node.getRight(), t));
      return node;
    } else { // result == 0
      if (node.getLeft() == null) {
        return node.getRight();
      } else if (node.getRight() == null) {
        return node.getLeft();
      } else { // neither child is null
        T predecessorValue = getHighestValue(node.getLeft());
        node.setLeft(removeRightmost(node.getLeft()));
        node.setData(predecessorValue);
        return node;
      }
    }
  }

  private T getHighestValue(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getData();
    } else {
      return getHighestValue(node.getRight());
    }
  }

  private BSTNode<T> removeRightmost(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getLeft();
    } else {
      node.setRight(removeRightmost(node.getRight()));
      return node;
    }
  }

  public T get(T t) {
    if (t == null) {
      throw new NullPointerException("Must pass a non-null element to get()");
    }  else {
          BSTNode<T> getNode = getNode(t);
          if (getNode == null){
            return null;
          }
          return getNode.getData();
       }
    }

  protected BSTNode<T> getNode(T t) {
    BSTNode<T> currNode = root;
    while (currNode != null){
      if (currNode.getData().equals(t) ){
        return currNode;
      }
      else if(t.compareTo(currNode.getData()) < 0){
        currNode = currNode.getLeft();
      }
      else {
        currNode = currNode.getRight();
      }
    }
    return null;
  }


  public void add(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    root = addToSubtree(root, new BSTNode<T>(t, null, null));
  }

  protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
    if (node == null) {
      return toAdd;
    }
    int result = toAdd.getData().compareTo(node.getData());
    if (result <= 0) {
      node.setLeft(addToSubtree(node.getLeft(), toAdd));
    } else {
      node.setRight(addToSubtree(node.getRight(), toAdd));
    }
    return node;
  }

  @Override
  public T getMinimum() {
    if (root == null){
      return null;
    }
    else{ return getLeftmost(root).getData(); }
  }

  @Override
  public T getMaximum() {
    if (root == null){
      return null;
    }
    else{ 
      return getRightmost(root).getData(); 
    }
  }
  // helper methods -- getRightmost and getLeftmost
  public BSTNode<T> getLeftmost(BSTNode<T> start){
    if (start == null){
      return null;
    }
    BSTNode<T> holder = null;
    while (start != null){
      holder = start;
      start = start.getLeft();
    }
    return holder;
  }

  public BSTNode<T> getRightmost(BSTNode<T> start){
    if (start == null){
      return null;
    }
    BSTNode<T> holder = null;
    while (start != null){
      holder = start;
      start = start.getRight();
    }
    return holder;
  }


  @Override
  public int height() {
    if (root == null){
      return -1;
    }
    else{ 
     return heightHelper(root);
    }
  }

  private int heightHelper(BSTNode<T> node) {
    if (node == null){
      return -1;
    }
    else if (node.getLeft() == null && node.getRight() == null) {
      return 0;
    }
    else {
      return Math.max( 1 + heightHelper(node.getLeft()) , 1 + heightHelper(node.getRight()) ); 
    }

  }

  public Iterator<T> preorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    preorderTraverse(queue, root);
    return queue.iterator();
  }

  private void preorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      queue.add(node.getData());
      preorderTraverse(queue, node.getLeft());
      preorderTraverse(queue, node.getRight());
    }
  }

  public Iterator<T> inorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, root);
    return queue.iterator();
  }


  private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      inorderTraverse(queue, node.getLeft());
      queue.add(node.getData());
      inorderTraverse(queue, node.getRight());
    }
  }

  public Iterator<T> postorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    postorderTraverse(queue, root);
    return queue.iterator();
  }

  private void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      postorderTraverse(queue, node.getLeft());
      postorderTraverse(queue, node.getRight());
      queue.add(node.getData());
    }
  }

  @Override
  public boolean equals(BSTInterface<T> other) {
    //BinarySearchTree<T> other2 = (BinarySearchTree<T>) other;
    if (other == null){
      throw new NullPointerException("Must pass a non-null value to equals()");
    }
    if (root == null){
      if (other.getRoot() != null){
        return false;
      }
      return true;
    }
    else if ( other.getRoot() == null){
      return false;
    }
    else if (root.getData().equals(other.getRoot().getData())){
      return equalsHelper(root, other.getRoot());
      } 
    else return false;
  }
  
  public boolean equalsHelper(BSTNode<T> thisNode, BSTNode<T> otherNode) {
    if (thisNode == null){
      if (otherNode == null){
        return true;
      } return false;
    }
    else if (otherNode == null){
      return false;
    }
    boolean check = thisNode.getData().equals(otherNode.getData());
    if(check == false){
      return false;
    }
    else{
      boolean left = equalsHelper(thisNode.getLeft(), otherNode.getLeft());
      if (left == false) {return false;}
      boolean right = equalsHelper(thisNode.getRight(), otherNode.getRight());
      if (right == false) { return false;} 
      else { return true;}
    }
  }


  @Override
  public boolean sameValues(BSTInterface<T> other) {
    // use iterator to produce inorder for both trees and then loop through comparing values ?
     // Iterator<T> it1 = inorderIterator(other)
    if (other == null){
      throw new NullPointerException("Must pass a non-null value to sameValues()");
    }
    else if (root == null){
      if (other.getRoot() == null) {
        return true;
      }
    }
    else if (other.getRoot() == null){
      return false;
    }
     // or we would use size and the contains() method maybe

    if (this.size() != other.size()){
      return false;
    }
    Iterator<T> thisIter= this.inorderIterator();
    while(thisIter.hasNext()) {
      boolean test = other.contains(thisIter.next());
      if (test == false ){
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isBalanced() {
  
    if (root == null) {
      return true;
    }
    else{ 
      int n = this.size();
      int h = this.height(); 
      if (n < Math.pow(2,h)) {return false;}
      else if (n >= Math.pow(2,h+1)) {return false;}
      else {return true;}
    }
  }
 /*
  public boolean isBalancedHelper(BSTNode<T> node) {
    if (node == null){
      return true;
    }
    boolean left;
    boolean right;
    int balanceNum = heightHelper(node.getLeft()) - heightHelper(node.getRight()) ; 
    if (Math.abs(balanceNum) > 1) {
      return false;
    }
    else{
      left = isBalancedHelper(node.getLeft());
      right = isBalancedHelper(node.getRight());f
      if (left == true && right == true) {
        return true;
      } else return false;
    }
  } */ 

  @Override
  @SuppressWarnings("unchecked")
  public void balance() {
    Iterator<T> iter = this.inorderIterator();
    ArrayList<T> valueList = new ArrayList<T>();
    for (int i = 0; i < this.size(); i++) {
      valueList.add(iter.next());
    }
    int size = this.size();
    this.root = null;  
    balanceHelper(valueList, 0, size - 1 );
  }

  public void balanceHelper(ArrayList<T> values, int low, int high) {
    if (low == high){
      this.add(values.get(low));
    }
    else if ( (low + 1) == high){
      this.add(values.get(low));
      this.add(values.get(high));
    }
    else{
      int mid = (low + high) / 2 ;
      this.add(values.get(mid));
      balanceHelper(values, low, mid-1);
      balanceHelper(values, mid+1, high);
    }
  }


  
  @Override
  public BSTNode<T> getRoot() {
    // DO NOT MODIFY
    return root;
  }

  public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
    // header
    int count = 0;
    String dot = "digraph G { \n";
    dot += "graph [ordering=\"out\"]; \n";
    // iterative traversal
    Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
    queue.add(root);
    BSTNode<T> cursor;
    while (!queue.isEmpty()) {
      cursor = queue.remove();
      if (cursor.getLeft() != null) {
        // add edge from cursor to left child
        dot += cursor.getData().toString() + " -> " + cursor.getLeft().getData().toString() + ";\n";
        queue.add(cursor.getLeft());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
      if (cursor.getRight() != null) {
        // add edge from cursor to right child
        dot +=
            cursor.getData().toString() + " -> " + cursor.getRight().getData().toString() + ";\n";
        queue.add(cursor.getRight());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
    }
    dot += "};";
    return dot;
  }

  public static void main(String[] args) {
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      BSTInterface<String> tree = new BinarySearchTree<String>();
      for (String s : new String[] {"d", "b", "a", "c", "f", "e", "g"}) {
        tree.add(s);
      }
      Iterator<String> iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.preorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.postorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();

      System.out.println(tree.remove(r));

      iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
    }

    BSTInterface<String> tree = new BinarySearchTree<String>();
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      tree.add(r);
    }
    System.out.println(toDotFormat(tree.getRoot()));
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
    tree.balance();
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
  }
}
