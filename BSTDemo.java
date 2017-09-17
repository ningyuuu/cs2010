import java.util.*;
import java.util.NoSuchElementException; // we will use this to illustrate Java Error Handling mechanism



// Every vertex in this BST is a Java Class
class BSTVertex {
  BSTVertex(int v) { key = v; parent = left = right = null; height = 0; }
  // all these attributes remain public to slightly simplify the code
  public BSTVertex parent, left, right;
  public int key;
  public int height; // will be used in AVL lecture
}


// This is just a sample implementation
// There are other ways to implement BST concepts...
class BST {
  protected BSTVertex root;

  public BST() { root = null; }

  // method called to search for a value v 
  public int search(int v) {
    BSTVertex res = search(root, v);
    return res == null ? -1 : res.key;
  }

  // overloaded recursive method to perform search
  protected BSTVertex search(BSTVertex T, int v) {
         if (T == null)  return null;                     // not found
    else if (T.key == v) return T;                        // found
    else if (T.key < v)  return search(T.right, v);       // search to the right
    else                 return search(T.left, v);        // search to the left
  }

  // method called to insert a new key with value v into BST
  public void insert(int v) { root = insert(root, v); }

  // overloaded recursive method to perform insertion of new vertex into BST
  protected BSTVertex insert(BSTVertex T, int v) {
    if (T == null) return new BSTVertex(v);          // insertion point is found

    if (T.key < v) {                                      // search to the right
      T.right = insert(T.right, v);
      T.right.parent = T;
    }
    else {                                                 // search to the left
      T.left = insert(T.left, v);
      T.left.parent = T;
    }

    return T;                                          // return the updated BST
  }

  // public method called to perform inorder traversal
  public void inorder() { 
    inorder(root);
    System.out.println();
  }

  // overloaded method to perform inorder traversal
  protected void inorder(BSTVertex T) {
    if (T == null) return;
    inorder(T.left);                               // recursively go to the left
    System.out.printf(" %d", T.key);                      // visit this BST node
    inorder(T.right);                             // recursively go to the right
  }

  // public method called to find Minimum key value in BST
  public int findMin() { return findMin(root); }

  // overloadded recursive method to perform findMin
  protected int findMin(BSTVertex T) {
         if (T == null)      throw new NoSuchElementException("BST is empty, no minimum");
    else if (T.left == null) return T.key;                    // this is the min
    else                     return findMin(T.left);           // go to the left
  }

  // public method called to find Maximum key value in BST
  public int findMax() { return findMax(root); }

  // overloadded recursive method to perform findMax
  protected int findMax(BSTVertex T) {
         if (T == null)       throw new NoSuchElementException("BST is empty, no maximum");
    else if (T.right == null) return T.key;                   // this is the max
    else                      return findMax(T.right);        // go to the right
  }

  // public method to find successor to given value v in BST
  public int successor(int v) { 
    BSTVertex vPos = search(root, v);
    return vPos == null ? -1 : successor(vPos);
  }

  // overloaded recursive method to find successor to for a given vertex T in BST
  protected int successor(BSTVertex T) {
    if (T.right != null)                       // this subtree has right subtree
      return findMin(T.right);  // the successor is the minimum of right subtree
    else {
      BSTVertex par = T.parent;
      BSTVertex cur = T;
      // if par(ent) is not root and cur(rent) is its right children
      while ((par != null) && (cur == par.right)) {
        cur = par;                                         // continue moving up
        par = cur.parent;
      }
      return par == null ? -1 : par.key;           // this is the successor of T
    }
  }

  // public method to find predecessor to given value v in BST
  public int predecessor(int v) { 
    BSTVertex vPos = search(root, v);
    return vPos == null ? -1 : predecessor(vPos);
  }

  // overloaded recursive method to find predecessor to for a given vertex T in BST
  protected int predecessor(BSTVertex T) {
    if (T.left != null)                         // this subtree has left subtree
      return findMax(T.left);  // the predecessor is the maximum of left subtree
    else {
      BSTVertex par = T.parent;
      BSTVertex cur = T;
      // if par(ent) is not root and cur(rent) is its left children
      while ((par != null) && (cur == par.left)) { 
        cur = par;                                         // continue moving up
        par = cur.parent;
      }
      return par == null ? -1 : par.key;           // this is the successor of T
    }
  }

  // public method to delete a vertex containing key with value v from BST
  public void delete(int v) { root = delete(root, v); }

  // overloaded recursive method to perform deletion 
  protected BSTVertex delete(BSTVertex T, int v) {
    if (T == null)  return T;              // cannot find the item to be deleted

    if (T.key < v)                                    // search to the right
      T.right = delete(T.right, v);
    else if (T.key > v)                               // search to the left
      T.left = delete(T.left, v);
    else {                                            // this is the node to be deleted
      if (T.left == null && T.right == null)                   // this is a leaf
        T = null;                                      // simply erase this node
      else if (T.left == null && T.right != null) {   // only one child at right        
        T.right.parent = T.parent;
        T = T.right;                                                 // bypass T        
      }
      else if (T.left != null && T.right == null) {    // only one child at left        
        T.left.parent = T.parent;
        T = T.left;                                                  // bypass T        
      }
      else {                                 // has two children, find successor
        int successorV = successor(v);
        T.key = successorV;         // replace this key with the successor's key
        T.right = delete(T.right, successorV);      // delete the old successorV
      }
    }
    
    return T;                                          // return the updated BST
  }

  // will be used in AVL lecture
  protected int getHeight(BSTVertex T) {
    if (T == null) return -1;
    else return Math.max(getHeight(T.left), getHeight(T.right)) + 1;
  }

  public int getHeight() { return getHeight(root); }
}



class BSTDemo {
  public static void main(String[] args) throws Exception {
    BST T = new BST();                                           // an empty BST

    try {
      System.out.println(T.findMin());                       // Exception occurs
      System.out.println(T.findMax());                   // will not be executed
    }
    catch (NoSuchElementException e) {
      System.out.println(e);
    }

    // Sample BST as shown in Lecture
    T.insert(15);
    T.insert(23);
    T.insert(6);
    T.insert(71);
    T.insert(50);
    T.insert(4);
    T.insert(7);
    T.insert(5);

    System.out.println(T.search(71));                               // found, 71
    System.out.println(T.search(7));                                 // found, 7
    System.out.println(T.search(22));                           // not found, -1

    try {
      System.out.println(T.findMin());                                      // 4
      System.out.println(T.findMax());                                     // 71
    }
    catch (NoSuchElementException e) {
      System.out.println(e);                             // will not be executed
    }

    System.out.println(T.successor(23));                                   // 50
    System.out.println(T.successor(7));                                    // 15
    System.out.println(T.successor(71));                                   // -1
    System.out.println(T.predecessor(23));                                 // 15
    System.out.println(T.predecessor(7));                                   // 6
    System.out.println(T.predecessor(71));                                 // 50

    T.inorder();                          // The BST: 4, 5, 6, 7, 15, 23, 50, 71

    System.out.println("Deleting 5");
    T.delete(5);
    System.out.println("Deleting 71");
    T.delete(71);
    System.out.println("Deleting 15");
    T.delete(15);

    T.inorder();                                     // The BST: 4, 6, 7, 23, 50
  }
}
