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
  }
}
```

# Union Find Disjoint Sets
A forest of nodes (in trees) to represent if they are connected

```
class UFDS {
  int[] p;
  int[] rank;
  int[] size;
  int sets;

  constructor(len) {
    p = new int[len];
    rank = new int[len];
    size = new int[len];
    sets = len;
    
    // init each of p to itself (its own root)
    // init each of rank to 0
    // init each of size to 1
    for (let i=0; i<len; i++) {
      p[i] = i;
      rank[i] = 0;
      size[i] = 1;
    }
  }

  int find(i) {
    if (p[i] == i) {
      // if we found root, return root
      return i;
    } else {
      // else we dig for root
      ret = find(p[i]);
      
      // path compression to make future searches o(1)
      p[i] = ret;

      // finally return root
      return ret;
    }
  }

  void union(i, j) {
    int seti = find(i);
    int setj = find(j);
    if (seti !== setj) {
      // only union if they are not already in the same set
      // decrease total set count
      sets--;

      if (rank[seti] > rank[setj]) {
        // make i's root the main root
        p[setj] = p[seti];
        // update i's root's size
        size[seti] = size[seti] + size[setj];
      } else {
        // make j's root the main root
        p[seti] = p[setj];
        // update j's root's size
        size[setj] =  size[seti] + size[setj];
      }
    }
  }

  num = () => num;
  size = (i) => size[find(i)];
}
```

# Breadth First Search / Depth First Search
BFS and DFS, running through an Adjacency List

```
class BFSDFS {
  int[][] adjlist; // in java, use ArrayList<ArrayList<Integer>>

  constructor() {
    populate_adjlist(); // not implementing this
  }

  int[] bfs(x) {
    int[] queue = []; // its a queue
    int[] prev = new int[adjlist.length].map(x => -1); // int of -1

    // init with x
    queue.push(x);

    // run through the queue till its empty
    while (queue.length > 0) {
      let curr = queue.shift(); // take from front, to behave like queue

        // process a neighbour if it hasn't been visited before
        adjlist[curr].forEach(item => {
          if (prev[item] == -1) {
            prev[item] = curr;
            queue.push(curr);
          }
        });
      }
    }

    return prev;
  }

  int[] dfs(x) {
    int[] prev = new int[adjlist.length].map(x => -1);

    // we use a recursive helper to do the actual dfs
    prev[x] = x;
    dfsrec(prev, x);
  }

  void dfsrec(prev, x) {
    adjlist[x].forEach(y => {
      if (prev[y] == -1) {
        prev[y] = x;
        dfsrec(prev, x, y);
      }
    });
  }
}
```


* DFS can be used in a post order way toposort.
* at the last line of dfsrec(), put `toposort.append(x);`
* this will give us a reverse of the toposort 


# Minimum Spanning Trees (MST)
Can be done with Prim's and Kruskal's

```
class MST {
  {weight, dest}[][] adjlist; // in java, it's ArrayList<ArrayList<IntegerPair>>
  arrlist<weight, dest, start> edgelist; // the edgelist
  {weght, dest}[][] mst; // let this be a adjmatrix


  constructor() {
    populate(adjlist); // not implementing this
    populate(edgelist);
  }

  prims(x) {
    // calling prims will generate mst, an MST of adjlist
    // x is the starting point

    bool[] taken = new bool[adjlist.length].map(x => false);
    PriorityQueue<{weight, dest, start}> pq = new PriorityQueue();

    adjlist[x].map(y => pq.add({y.weight, y.dest, x}));

    while (!pq.length > 0) {
      let z = pq.pop();
      if (taken[z.dest] === -1) {
        taken[z.dest] = false;
        mst[z.start][z.dest] = z.weight;
        adjlist[z.dest].map(y => {
          if (!taken[y.dest]) {
            pq.add(y.weight, y.dest, z);
          }
        })
      }
    }
  }

  kruskals() {
    edgelist.sort(); // in java, it's Collections.sort();
    UFDS ufds = new UFDS();
    edgelist.forEach(edge => {
      if (ufds.find(edge.dest) !== ufds.find(edge.start)) {
        mst[edge.start][edge.end] = edge.weight;
        ufds.union(edge.start, edge.end);
      }
    })
  }
}
```

# Shortest Path Algorithms
BFS, Bellman-Ford, Djikstra, APSP

```
class ShortestPath {
  AdjList adjlist; // like the prior;
  int[][] adjmatrix;
  int V;
  int[] D, p;

  constructor() {
    populateAllTheStuffHehe();
  }

  initSSSP(s) {
    D = new int[V].map(x => INFINITY);
    p = new int[V].map(x => -1);
    D[s] = 0;
  }

  relax(u, v, w_u_v) {
    if D[v] > D[u] + w_u_v {
      D[v] = D[u] + w_u_v;
      p[v] = u;
    }
  }

  modifiedbfs(x) {
    // only for constant weight graphs
    initSSSP(x);
    int[] queue = [];
    queue.push(x);

    while (queue.length > 0) {
      item = queue.shift();
      adjlist[item].forEach(y => {
        if D[y] = INIFINITY {
          D[y] = D[item] + 1;
          p[y] = item;
          queue.push(y);
        }
      })
    } 
  }

  bellmanford(x) {
    initSSSP(x);
    for (let i=0; i<V-1; i++) { // if its a dag, then we can just 1 pass
    // with edges in topological order
      adjlist.forEach(v,idx => {
        v.forEach(edge => {
          relax(idx, edge.dest, edge.weight);
        })
      })
    }
  }

  djikstra(x) {
    PriorityQueue<{weight, dest}> = new PriorityQueue(); // this pq needs a decreasekey methodd, which java does not implement
    initSSSP(x); // we won't use p here
    for (let i=0; i<V; i++) {
      if (i == x) pq.push(0, i);
      else pq.push(INFINITY, i);
    }

    while (!PQ.isempty()) {
      item = PQ.poll();
      D[item.dest] = item.weight;
      adjlist[item].forEach(y => {
        if (pq[y.dest].weight > item.weight + y.weight) {
          pq.decreaseKey(y.dest, item.weight + y.weight);
          p[y.dest] = item.dest;
        }
      });
    }
  }

  moddjikstra(x) {
    
  }
}
```