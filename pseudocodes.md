# Pseudocodes for CS2010
__Final Exam Revision__: This is a compiled list of pseudocodes (basically javascript with data structures) for algorithms covered in cs2010.  
Code is annotated to help with my own understanding.

## Binary Heap
A binary heap stored as an array (or linked list).

```
class BinaryHeap {
  arrlist<int> arr;
  int size;

  constructor() {
    // initializes an empty heap and sets size to 0
    A = new arrlist;
    A.add(0) // dummy
    size = 0;
  }

  int parent(x) {
    // gets the parent of that index. arr[x] < arr[parent(x)]
    return Math.floor(x/2);
  }

  int left(x) {
    // gets the left child of that index. arr[x] > arr[left(x)]
    return 2 * x;
  }

  int right(x) {
    // gets the right child of index. arr[x] > arr[right(x)]
    return 2 * x + 1;
  }

  void shiftup(x) { // O(lg n)
    // swaps a node with its parent repeatedly until
    // it is smaller than its parent
    // or if it is the root
    while (i > 1 && arr[parent(i)] < arr[i]) {
      int temp = arr[i];
      arr[i] = arr[parent(i)];
      arr[parent(i)] = temp;
      i = parent(i);
    }
  }

  void shiftdown(x) { // O(lg n)
    // continuously swaps node with the smaller of its children
    // until it is larger than both, at which loop breaks
    while (x <= size) { // basically while(true)
      // find the max value (maxV) and max id (max)
      int maxV = arr[x];
      int max = x;
      if (left(x) <= size && maxV < arr[left(x)]) {
        maxV = arr[left(x)];
        max = left(x);
      }
      if (right(x) <= size && maxV < arr[right(x)]) {
        maxV = arr[right(x)];
        max = right(x);
      }
      // if max id changed, do the swap
      if (max != x) {
        int temp = arr[x];
        arr[x] = arr[max];
        arr[x] = temp;
        x = max;
      } else {
        break; // else break loop, cos both children < x
      }
    }
  }

  void insert(val) {
    // adds a value into the heap
    // first we increase length
    size++;

    // since we use lazy deletion (as seen later on)
    // we should first see if we can assign existing
    // lazy deleted element a new value

    // new size should equal length due to dummy
    // if there isn't remaining lazy deleted items
    // in that case, we add at the back
    // size should never > arr.length tho!
    if (size >= arr.length) {
      arr.add(val);
    } else {
      // we set a lazy deleted item as the val
      arr[size] = val;
    }
  }

  void getmax() {
    // self explanatory. get max, which is root.
    return arr[1];
  }

  void extractmax() {
    // return curr root
    int ans = arr[1];

    // then move smallest to top, and shiftdown
    arr[1] = arr[size];
    size--;
    shiftdown(1);

    return ans;
  }

  buildheap(target) {
    // builds a heap from the param arr
    size = target.length;
    for (i=0; i<size; i++) {
      arr.add(target[i]);
    }
    // loop from last non-leaf to root
    for (i=parent(size); i>0; i--) {
      shiftdown(i);
    }
    // some cheem math here, but this process is unintuitively
    // 0(n lg n), which is pretty neat
  }

  getsize = () => size;
  isempty = () => size == 0;
}
```

# Balanced Binary Search Tree
AKA here comes the pain.

```
// in reality, this can be used to as a map
// search for a value based on a key
// but here we only have a key

// for PS2, we should also implement size() and .size
// this is for ranking and more importantly, DEBUGGING

class BSTVertex {
  int key, hieght;
  BSTVertex parent, left, right;

  constructor(v) {
    key = v;
    parent = null;
    left = null;
    right = null;
    height = 0;
  }
}
class BBST {
  BSTVertex root;

  constructor() {
    root = null;
  }

  int search(v) {
    // searches for the value v
    // calls its own overloaded method
    // that has the current subtree to search from
    BSTVertex res = search(root, v); 
    return res == null ? -1 : res.key;
  }

  BSTVertex search(BSTVertex T, v) {
    // this method recursively searches for V
    // by navigating to its left or rigt
    // like a binary search algo

    // if it is a null, our search failed
    if (T == null) {
      return null;
    }

    if (T.key == v) {
      return T;
    }

    if (T.key < v) {
      // if curr tree is smaller than v
      // then its target is definitely in its right subtree
      return search(T.right, v);
    }

    else { //T.key > v
      // target in left subtree
      return search(T.left, v);
    }
  }

  void insert(v) {
    root = insert(root, v);
    // seems familiar? same overloading approach,
    // recursively find the right place to insert
    // then attaches entire tree back to root
  }

  void insert(BSTVertex T, v) {
    // if tree is empty, we have our root
    if (T == null) return new BSTVertex(v);

    if (T.key < v) {
      // key < v means we insert on the right
      T.right = insert(T.right, v);
      T.right.parent = T;
      // actually, there is a way to implement this
      // without this parent link
    }

    else {
      // key > v, we insert left
      T.left = insert(T.left, v);
      T.left.parent = T;
    }

    // bbst: update height then rebalance
    T.height = Math.max(T.left.height, T.right.height) + 1;
    T = rebalance(T);

    // finally we return T
    return T;
  }

  void inorder() {
    inorder(root);
  }

  void inorder(T) {
    if (T == null) {
      // done w this subtree
      return;
    }
    // left
    inorder(left);
    //itself
    print(T.key);
    // right
    inorder(right);

    // for pre and post order, just shift the way
    // you print yourself to before/after children
  }

  int min() {
    return min(root);
  }

  int min(T) {
    // recursively go left until cannot, then print itself
    if (T == null) {
      print('tree is empty');
      return -9999; // return impossible or whatever you specify
    }

    // if it has no left, it's the smallest
    if (T.left == null) {
      return T.key;
    }

    // if it has a left, do dig left
    return min(T.left);
  }

  int max() {
    // same as min but other way
  }

  int successor(v) {
    // first, we get the node
    BSTVertex V = search(root, v);
    return V == null ? -9999 : successor(V);
  }

  int successor(T) {
    if (T.right != null) {
      // if it has a right
      // then the smallest element of its right
      // if its next biggest element
      return min(T.right);
    }

    BSTVertex parent = T.parent;
    BSTVertex curr = T;

    // otherwise, since its the largest of its own sub tree,
    // its successor is the root of the first subtree that it appears on the LEFT in
    // because the in the smallest sub tree its on the left,
    // it must be the largest left member
    // so the root becomes is the successor
    while (parent != null && curr == par.right) {
      curr = parent;
      parent = parent.parent;
    }

    // since par.right isn't curr anymore,
    // par.left must be curr
    if (parent != null && curr == par.left) {
      return par.key;
    } 

    // if neither, it means it is the root
    // iff it has no successor
    return -9999;
  }

  int predecessor(v) {
    BSTVertex V = search(root, v);
    return V == null? - 9999 : predecesso(V);
  }

  int predecessor(T) {
    // if it has a left, then predecessor is max of left
    if (T.left != null) {
      return max(T.left);
    }

    // otherwise, its predecessor is the root of smallest sub tree
    // whose right sub tree contains T
    // finding a sub tree that has T on the right
    BSTVertex parent = T.parent;
    BSTVertex curr = T;

    while (parent != null && curr == parent.left) {
      curr = parent;
      parent = parent.parent;
    }

    // now that its parent doesn't have it as a left child
    // it is either on the parents right, or we have reached the top
    return par == null? -9999 : par.key;
  }

  void delete(v) {
    root = delete(root, v);
  }

  BSTVertex delete(T, v) {
    // if empty, delete failed, return root unchanged
    if (T == null) return T;

    if (T.key < v) {
      // v is on the right or not found, so
      T.right = delete(T.right, v);
    }

    if (T.key > v) {
      T.left = delete(T.left, v);
    } else {
      // else, T.key == v
      // we need to do the adjustments for its children after deleting
      
      // if it has no children, great, just delete
      if (T.left == null && T.right == null) {
        T = null;
      } else if (T.left == null && T.right != null) {
        // it has a right
        T.right.parent = T.parent;
        T = T.right;
      } else if (T.left exists but not right) {
        repeat ^ for left
      } else {
        // else, we replace V with its successor
        // then delete it from its right subtree
        // since we know it has a successor in its right subtree
        int successorV = successor(v);
        T.key = successorV;
        T.right = delete(T.right, successorV);
      }
    }

    // bbst: update height + rebalance
    // in reality, abstract height to method to account for null
    T.height = Math.max(T.left.height, T.right.height) + 1;
    T = rebalance(T);

    return T;
  }

  // to maintain balance, we require height to be within
  // a difference of 1 between left and right subtrees
  BSTVertex rebalance(T) {
    // it will do all the necessary rotations to balance
    // then return the new root of the balanced tree/subtree

    // first, we get the balance factor of the node
    int bf = getbf(T);
    if (bf >= 2) {
      // too much shit on the left, we'll need to rotate right
      if (getbf(T.left) == -1) {
        // this is a left-right case, which means its left child
        // is already heavy on the right. since the right
        // load will shift over, we want to keep the heavy 
        // stuff on the left -> we rotate left first
        T.left = rotateLeft(T.left);

        // now it is definitely left-left
      }
      // now we rotate right
      T = rotateRight(T);

    } else if (bf <= -2) {
      // too much on the right, we need to hit left
      // but first, check for rightleft case
      if (getbf(T.right) == 1) {
        T.right = rotateRight(T.right);
      }
      // then fix from rightright to balance
      T = rotateLeft(T);
    }
  }

  int getbf(T) {
    if (T == null) {
      return 0;
    }

    int lefth = T.left == null ? -1 : T.left.height;
    int righth = T.right == null ? -1 : T.right.height;
    return lefth - righth;
  }

  BSTVertex rotateLeft(T) {
    // i'm literally following the slides here
    BSTVertex W = T.right; // my new root
    W.parent = T.parent;
    T.parent = W;
    T.right = W.left; //taking over its left subtree
    if (W.left != null) {
      W.left.parent = T;
    }
    W.left = T;
    
    // update height
    // in reality, abstract a method to take care of nullpointer
    T.height = Math.max(T.left.height, T.right.height) + 1;
    W.height = Math.max(W.left.height, W.right.height) + 1;
    
    // attach new root
    return W;
  }

  BSTVertex rotateRight(T) {
    // literally the same as rotateLeft but in mirror mode
  }


}
```
