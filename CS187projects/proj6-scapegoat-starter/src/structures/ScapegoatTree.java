package structures;

public class ScapegoatTree<T extends Comparable<T>> extends BinarySearchTree<T> {
  private int upperBound;


  @Override
  public void add(T t) {
   
    //upperBound better understand with removal -- that's when upperBoudn rule comes in
    upperBound++;
    if (t == null) {
      throw new NullPointerException();
    }
    root = addToSubtree(root, new BSTNode<T>(t, null, null));
    int n = this.size();
    double scapeLim = (Math.log(n) / Math.log(1.5)) ;
    // this actually will activate on the root but the helper method should handle that case
    if ( this.height() >= scapeLim){
      root = scapegoatBalance(getNode(t));
    }
  }

  private BSTNode<T> scapegoatBalance(BSTNode<T> extremNode){
    BSTNode<T> currNode = extremNode.getParent();
    BSTNode<T> prevNode = extremNode;   //the previous node will be curr Node's child on the path back from extremnode (reverse travering)
    // if the currNode is null then it is the root so just return itself
    while (currNode != null){
      double numer = subtreeSize(prevNode);
      double denom = subtreeSize(currNode);
      double scapeKey = (double)(numer/denom);
      double limit = (double) 2.0/3.0;
      if(scapeKey > limit){
        // get the scapegoat's parent and hold on to it -- was it the left or right ?
        BSTNode<T> graftLoc = currNode.getParent();
        //get the subtree
        BinarySearchTree<T> scapeTree = new BinarySearchTree<T>();
        scapeTree.setRoot(new BSTNode<T>(currNode.getData(), currNode.getLeft(), currNode.getRight()));
        // balance the subtree
        scapeTree.balance();
        // skip the grafting step if the graftLoc = null (because then it was the root -- we balanced the whole tree)
        if (graftLoc == null){
          return scapeTree.getRoot();
        }
        else{
          // graft the balanced subtree back in place
          //BSTNode<T> balRoot = scapeTree.getRoot();
          int result = scapeTree.getRoot().getData().compareTo(graftLoc.getData());
          // skip the grafting step if the 
          if (result  < 0) {
            graftLoc.setLeft(scapeTree.getRoot());
          } else{
            graftLoc.setRight(scapeTree.getRoot());
          }
          return root;
        }
      }
    else{ 
        // update to next node in the path
        prevNode = currNode;
        currNode = currNode.getParent();
      }
    }
    return extremNode;
  }

  @Override
  public boolean remove(T element) {
    if (element == null){
      throw new NullPointerException("You must pass a non-null element to remove()");
    }
    boolean result = this.contains(element);
    if (result){
      root = removeFromSubtree(root, element);
      if (this.size() <= (double)(upperBound/2) ) {
        this.balance();
      }
      return true;
    }
    return false;
  }

  public static void main(String[] args) {
    BSTInterface<String> tree = new ScapegoatTree<String>();
    /*
    You can test your Scapegoat tree here.
    for (String r : new String[] {"0", "1", "2", "3", "4"}) {
      tree.add(r);
      System.out.println(toDotFormat(tree.getRoot()));
    }
    */
  }
}
