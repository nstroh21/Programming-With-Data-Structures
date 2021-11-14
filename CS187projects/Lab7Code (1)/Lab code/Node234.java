// Node234 class - represents a node in a 2-3-4 tree
class Node234 {
   public Integer A, B, C;
   public Node234 left, middle1, middle2, right;
   
   public Node234(int keyA, Node234 leftChild, Node234 middle1Child) {
      A = keyA;
      B = null;
      C = null;
      left = leftChild;
      middle1 = middle1Child;
      middle2 = null;
      right = null;
   }
   
   public Node234(int keyA) {
      this(keyA, null, null);
   }

   // Appends 1 key and 1 child to this node.
   // Preconditions:
   // 1. This node has 1 or 2 keys
   // 2. key > all keys in this node
   // 3. Child subtree contains only keys > key
   public void appendKeyAndChild(Integer key, Node234 child) {
      if (B == null) {
         B = key;
         middle2 = child;
      }
      else {
         C = key;
         right = child;
      }
   }
    
   // Returns the number of keys in this node, which will be 1, 2, or 3.
   public int countKeys() {
      if (C != null) {
         return 3;
      }
      else if (B != null) {
         return 2;
      }
      return 1;
   }
    
   // Returns the left, middle1, middle2, or right child if the childIndex
   // argument is 0, 1, 2, or 3, respectively.
   // Returns null if the childIndex argument is < 0 or > 3.
   public Node234 getChild(int childIndex) {
      if (childIndex == 0) {
         return left;
      }
      else if (childIndex == 1) {
         return middle1;
      }
      else if (childIndex == 2) {
         return middle2;
      }
      else if (childIndex == 3) {
         return right;
      }
      return null;
   }

   // Returns 0, 1, 2, or 3 if the child argument is this node's left,
   // middle1, middle2, or right child, respectively.
   // Returns -1 if the child argument is not a child of this node.
   public int getChildIndex(Node234 child) {
      if (child == left) {
         return 0;
      }
      else if (child == middle1) {
         return 1;
      }
      else if (child == middle2) {
         return 2;
      }
      else if (child == right) {
         return 3;
      }
      return -1;
   }

   // Returns this node's A, B, or C key, if the keyIndex argument is
   // 0, 1, or 2, respectively.
   // Returns null if the keyIndex argument is < 0 or > 2.
   public Integer getKey(int keyIndex) {
      if (keyIndex == 0) {
         return A;
      }
      else if (keyIndex == 1) {
         return B;
      }
      else if (keyIndex == 2) {
         return C;
      }
      return null;
   }

   // Returns 0, 1, or 2, if the key argument is this node's A, B, or
   // C child, respectively.
   // Returns -1 if the key argument is not a key of this node.
   public int getKeyIndex(Integer key) {
      if (key.equals(A)) {
         return 0;
      }
      else if (key.equals(B)) {
         return 1;
      }
      else if (key.equals(C)) {
         return 2;
      }
      return -1;
   }
   
   // Returns true if this node has the specified key, false otherwise.
   public boolean hasKey(Integer key) {
      return key.equals(A) || key.equals(B) || key.equals(C);
   }

   // Inserts a new key into the proper location in this node.
   // Precondition: This node is a leaf and has 2 or fewer keys
   public void insertKey(Integer key) {
      if (key.compareTo(A) < 0) {
         C = B;
         B = A;
         A = key;
      }
      else if (B == null || key.compareTo(B) < 0) {
         C = B;
         B = key;
      }
      else {
         C = key;
      }
   }

   // Inserts a new key into the proper location in this node, and
   // sets the children on either side of the inserted key.
   // Precondition: This node has 2 or fewer keys
   public void insertKeyWithChildren(Integer key, Node234 leftChild, Node234 rightChild) {
      if (key.compareTo(A) < 0) {
         C = B;
         B = A;
         A = key;
         right = middle2;
         middle2 = middle1;
         middle1 = rightChild;
         left = leftChild;
      }
      else if (B == null || key.compareTo(B) < 0) {
         C = B;
         B = key;
         right = middle2;
         middle2 = rightChild;
         middle1 = leftChild;
      }
      else {
         C = key;
         right = rightChild;
         middle2 = leftChild;
      }
   }

   // Returns true if this node is a leaf, false otherwise.
   public boolean isLeaf() {
      return left == null;
   }

   // Returns the child of this node that would be visited next in the
   // traversal to search for the specified key
   public Node234 nextNode(Integer key) {
      if (key.compareTo(A) < 0) {
         return left;
      }
      else if (B == null || key.compareTo(B) < 0) {
         return middle1;
      }
      else if (C == null || key.compareTo(C) < 0) {
         return middle2;
      }
      return right;
   }

   // Removes key A, B, or C from this node, if keyIndex is 0, 1, or 2,
   // respectively. Other keys and children are shifted as necessary.
   public void removeKey(int keyIndex) {
      if (keyIndex == 0) {
         A = B;
         B = C;
         C = null;
         left = middle1;
         middle1 = middle2;
         middle2 = right;
         right = null;
      }
      else if (keyIndex == 1) {
         B = C;
         C = null;
         middle2 = right;
         right = null;
      }
      else if (keyIndex == 2) {
         C = null;
         right = null;
      }
   }

   // Removes and returns the rightmost child. Two possible cases exist:
   // 1. If this node has a right child, right is set to null, and the
   //    previous right value is returned.
   // 2. Else if this node has a middle2 child, middle2 is set to null, and
   //    the previous right value is returned.
   // 3. Otherwise no action is taken, and null is returned.
   // No keys are changed in any case.
   public Node234 removeRightmostChild() {
      Node234 removed = null;
      if (right != null) {
         removed = right;
         right = null;
      }
      else if (middle2 != null) {
         removed = middle2;
         middle2 = null;
      }
      return removed;
   }

   // Removes and returns the rightmost key. Three possible cases exist:
   // 1. If this node has 3 keys, C is set to null and the previous C value is returned.
   // 2. If this node has 2 keys, B is set to null and the previous B value is returned.
   // 3. Otherwise no action is taken and null is returned.
   // No children are changed in any case.
   public Integer removeRightmostKey() {
      Integer removed = null;
      if (C != null) {
         removed = C;
         C = null;
      }
      else if (B != null) {
         removed = B;
         B = null;
      }
      return removed;
   }

   // Sets the left, middle1, middle2, or right child if the childIndex
   // argument is 0, 1, 2, or 3, respectively.
   // Does nothing if the childIndex argument is < 0 or > 3.
   public void setChild(Node234 child, int childIndex) {
      if (childIndex == 0) {
         left = child;
      }
      else if (childIndex == 1) {
         middle1 = child;
      }
      else if (childIndex == 2) {
         middle2 = child;
      }
      else if (childIndex == 3) {
         right = child;
      }
   }

   // Sets this node's A, B, or C key if the keyIndex argument is 0, 1, or
   // 2, respectively.
   // Does nothing if the keyndex argument is < 0 or > 2.
   public void setKey(Integer key, int keyIndex) {
      if (keyIndex == 0) {
         A = key;
      }
      else if (keyIndex == 1) {
         B = key;
      }
      else if (keyIndex == 2) {
         C = key;
      }
   }
}