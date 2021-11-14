import java.util.Stack;

class Tree234 {
   private Node234 root;
   
   // Initializes the tree with the root node reference set to null.
   public Tree234() {
      root = null;
   }

   // Inserts a new key into this tree, provided the tree doesn't already
   // contain the same key.
   public Node234 insert(Integer key, Node234 node, Node234 nodeParent) {
      // Special case for empty tree
      if (root == null) {
         root = new Node234(key);
         return root;
      }

      // If the node argument is null, recursively call with root
      if (node == null) {
         return insert(key, root, null);
      }

      // Check for duplicate key
      if (node.hasKey(key)) {
         // Duplicate keys are not allowed
         return null;
      }

      // Preemptively split full nodes
      if (node.C != null) {
         node = split(node, nodeParent);
      }

      if (!node.isLeaf()) {
         if (key.compareTo(node.A) < 0) {
            return insert(key, node.left, node);
         }
         else if (node.B == null || key.compareTo(node.B) < 0) {
            return insert(key, node.middle1, node);
         }
         else if (node.C == null || key.compareTo(node.C) < 0) {
            return insert(key, node.middle2, node);
         }
         else {
            return insert(key, node.right, node);
         }
      }
        
      // key can be inserted into leaf node
      node.insertKey(key);
      return node;
   }
   
   public Node234 insert(Integer key) {
      return insert(key, null, null);
   }

   // Searches this tree for the specified key. If found, the node containing
   // the key is returned. Otherwise null is returned.
   public Node234 search(Integer key) {
      return searchRecursive(key, root);
   }

   // Recursive helper method for search.
   private Node234 searchRecursive(Integer key, Node234 node) {
      if (node == null) {
         return null;
      }
            
      // Check if the node contains the key
      if (node.hasKey(key)) {
         return node;
      }
        
      // Recursively search the appropriate subtree
      if (key.compareTo(node.A) < 0) {
         return searchRecursive(key, node.left);
      }
      else if (node.B == null || key.compareTo(node.B) < 0) {
         return searchRecursive(key, node.middle1);
      }
      else if (node.C == null || key.compareTo(node.C) < 0) {
         return searchRecursive(key, node.middle2);
      }
      return searchRecursive(key, node.right);
   }

   // Splits a full node, moving the middle key up into the parent node.
   private Node234 split(Node234 node, Node234 nodeParent) {
      Node234 splitLeft = new Node234(node.A, node.left, node.middle1);
      Node234 splitRight = new Node234(node.C, node.middle2, node.right);
      if (nodeParent != null) {
         nodeParent.insertKeyWithChildren(node.B, splitLeft, splitRight);
      }
      else {
         // Split root
         nodeParent = new Node234(node.B, splitLeft, splitRight);
         root = nodeParent;
      }
        
      return nodeParent;
   }
        
   // Fuses a parent node and two children into one node. 
   // Precondition: Each of the three nodes must have one key each.
   private Node234 fuse(Node234 parent, Node234 leftNode, Node234 rightNode) {
      if (parent == root && parent.countKeys() == 1) {
         return fuseRoot();
      }
        
      int leftNodeIndex = parent.getChildIndex(leftNode);
      Integer middleKey = parent.getKey(leftNodeIndex);
      Node234 fusedNode = new Node234(leftNode.A);
      fusedNode.B = middleKey;
      fusedNode.C = rightNode.A;
      fusedNode.left = leftNode.left;
      fusedNode.middle1 = leftNode.middle1;
      fusedNode.middle2 = rightNode.left;
      fusedNode.right = rightNode.middle1;
      int keyIndex = parent.getKeyIndex(middleKey);
      parent.removeKey(keyIndex);
      parent.setChild(fusedNode, keyIndex);
      return fusedNode;
   }

   // Fuses the tree's root node with the root's two children. 
   // Precondition: Each of the three nodes must have one key each.
   private Node234 fuseRoot() {        
      Node234 oldLeft = root.left;
      Node234 oldMiddle1 = root.middle1;
      root.B = root.A;
      root.A = oldLeft.A;
      root.C = oldMiddle1.A;
      root.left = oldLeft.left;
      root.middle1 = oldLeft.middle1;
      root.middle2 = oldMiddle1.left;
      root.right = oldMiddle1.middle1;
      return root;
   }
   
   private int getHeight(Node234 node) {
      if (node.left == null) {
         return 0;
      }
      return 1 + getHeight(node.left);
   }
   
   // Returns the height of this tree.
   public int getHeight() {
      return getHeight(root);
   }

   // Searches for, and returns, the minimum key in a subtree
   public Integer getMinKey(Node234 node) {
      Node234 current = node;
      while (current.left != null) {
         current = current.left;
      }
      return current.A;
   }

   // Finds and replaces one key with another. The replacement key must
   // be known to be a key that can be used as a replacement without violating
   // any of the 2-3-4 tree rules.
   private boolean keySwap(Node234 node, Integer existing, Integer replacement) {
      if (node == null) {
         return false;
      }

      int keyIndex = node.getKeyIndex(existing);
      if (keyIndex == -1) {
         Node234 next = node.nextNode(existing);
         return keySwap(next, existing, replacement);
      }

      node.setKey(replacement, keyIndex);
      return true;
   }
   
   // Returns the number of keys in this tree.
   public int length() {
      int count = 0;
      Stack<Node234> nodes = new Stack<Node234>();
      nodes.push(root);
      
      while (!nodes.empty()) {
         Node234 node = nodes.pop();
         if (node != null) {
            // Add the number of keys in the node to the count
            count = count + node.countKeys();
                
            // Push children
            nodes.push(node.left);
            nodes.push(node.middle1);
            nodes.push(node.middle2);
            nodes.push(node.right);
         }
      }
      return count;
   }

   // Rotates or fuses to add 1 or 2 additional keys to a node with 1 key.
   private Node234 merge(Node234 node, Node234 nodeParent) {
      // Get references to node's siblings
      int nodeIndex = nodeParent.getChildIndex(node);
      Node234 leftSibling = nodeParent.getChild(nodeIndex - 1);
      Node234 rightSibling = nodeParent.getChild(nodeIndex + 1);
    
      // Check siblings for a key that can be transferred
      if (leftSibling != null && leftSibling.countKeys() >= 2) {
         rotateRight(leftSibling, nodeParent);
      }
      else if (rightSibling != null && rightSibling.countKeys() >= 2) {
         rotateLeft(rightSibling, nodeParent);
      }
      else { // fuse
         if (leftSibling == null) {
            node = fuse(nodeParent, node, rightSibling);
         }
         else {
            node = fuse(nodeParent, leftSibling, node);
         }
      }

      return node;
   }

   // Finds and removes the specified key from this tree.
   public boolean remove(Integer key) {
      // Special case for tree with 1 key
      if (root.isLeaf() && root.countKeys() == 1) {
         if (root.A == key) {
            root = null;
            return true;
         }
         return false;
      }

      Node234 currentParent = null;
      Node234 current = root;
      while (current != null) {
         // Merge any non-root node with 1 key
         if (current.countKeys() == 1 && current != root) {
            current = merge(current, currentParent);
         }

         // Check if current node contains key
         int keyIndex = current.getKeyIndex(key);
         if (keyIndex != -1) {
            if (current.isLeaf()) {
               current.removeKey(keyIndex);
               return true;
            }

            // The node contains the key and is not a leaf, so the key is
            // replaced with the successor
            Node234 tmpChild = current.getChild(keyIndex + 1);
            Integer tmpKey = getMinKey(tmpChild);
            remove(tmpKey);
            keySwap(root, key, tmpKey);
            return true;
         }

         // Current node does not contain key, so continue down tree
         currentParent = current;
         current = current.nextNode(key);
      }
                
      // key not found
      return false;
   }

   private void rotateLeft(Node234 node, Node234 nodeParent) {
      // Get the node's left sibling
      int nodeIndex = nodeParent.getChildIndex(node);
      Node234 leftSibling = nodeParent.getChild(nodeIndex - 1);

      // Get the key from the parent that will be copied into the left sibling
      Integer keyForLeftSibling = nodeParent.getKey(nodeIndex - 1);

      // Append the key to the left sibling
      leftSibling.appendKeyAndChild(keyForLeftSibling, node.left);

      // Replace the parent's key that was appended to the left sibling
      nodeParent.setKey(node.A, nodeIndex - 1);

      // Remove key A and left child from node
      node.removeKey(0);
   }
    
   private void rotateRight(Node234 node, Node234 nodeParent) {
      // Get the node's right sibling
      int nodeIndex = nodeParent.getChildIndex(node);
      Node234 rightSibling = nodeParent.getChild(nodeIndex + 1);
        
      // Get the key from the parent that will be copied into the right sibling
      Integer keyForRightSibling = nodeParent.getKey(nodeIndex);
        
      // Shift key and child references in right sibling
      rightSibling.C = rightSibling.B;
      rightSibling.B = rightSibling.A;
      rightSibling.right = rightSibling.middle2;
      rightSibling.middle2 = rightSibling.middle1;
      rightSibling.middle1 = rightSibling.left;
        
      // Set key A and the left child of rightSibling
      rightSibling.A = keyForRightSibling;
      rightSibling.left = node.removeRightmostChild();
        
      // Replace the parent's key that was prepended to the right sibling
      nodeParent.setKey(node.removeRightmostKey(), nodeIndex);
   }
}