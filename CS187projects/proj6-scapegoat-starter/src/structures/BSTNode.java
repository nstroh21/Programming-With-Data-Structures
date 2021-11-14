package structures;

/**
 * A node in a BST.
 *
 * <p>Note that BSTNode MUST implement BSTNodeInterface; removing this will resulit in your program
 * failing to compile for the autograder.
 *
 * @author liberato
 * @param <T>
 */
public class BSTNode<T extends Comparable<T>> implements BSTNodeInterface<T> {
  private T data;
  private BSTNode<T> left;
  private BSTNode<T> right;
  private BSTNode<T> parent;

  public BSTNode(T data, BSTNode<T> left, BSTNode<T> right) {
    this.data = data;
    this.left = left;
    this.right = right;
    this.parent = null;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public BSTNode<T> getLeft() {
    return left;
  }

  public void setLeft(BSTNode<T> left) {
    //implement parent pointer:
    this.left = left;
    if (left != null){
      left.parent = this;
    }
  }

  public BSTNode<T> getRight() {
    return right;
  }

  public void setRight(BSTNode<T> right) {
     //implement parent pointer:
    this.right =  right;
    if (right != null){
      right.parent = this;
    }
  }

  public BSTNode<T> getParent(){
    return parent;
  }
  public void setParent(BSTNode<T> parent){
    this.parent = parent;
  }

}
