// Copy paste this Java Template and save it as "GettingFromHereToThere.java"
import java.util.*;
import java.io.*;

// write your matric number here:
// write your name here:
// write list of collaborators here:
// year 2017 hash code: 1T5wnBBL7ix0uP44Ogtn (do NOT delete this line)

class GettingFromHereToThere {
  private int V; // number of vertices in the graph (number of rooms in the building)
  private ArrayList<ArrayList<IntegerPair>> AdjList; // the weighted graph (the building), effort rating of each corridor is stored here too
  private HashMap<Integer, HashMap<Integer, Integer>> memoHash;

  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  // --------------------------------------------



  // --------------------------------------------

  public GettingFromHereToThere() {
    // Write necessary codes during construction;
    //
    // write your answer here
    memoHash = new HashMap<Integer, HashMap<Integer, Integer>>();
  }

  void PreProcess() {
    // write your answer here
    // you can leave this method blank if you do not need it

    prims();
  }

  int Query(int source, int destination) {
    int ans = 0;

    // You have to report the weight of a corridor (an edge)
    // which has the highest effort rating in the easiest path from source to destination for the wheelchair bound
    //
    // write your answer here

    // assume tree => no cycles
    // applying a DFS to find the right path

    // for 4D: a memoization key
    int max = Math.max(source, destination);
    int min = Math.min(source, destination);

    if (!memoHash.containsKey(max)) {
      memoHash.put(max, new HashMap<Integer, Integer>());
    }

    if (!memoHash.get(max).containsKey(min)) {
      System.out.println("HELLO");
      int[] visited = new int[V];
      // System.out.println("V:" + visited.length);
      for (int i=0; i<visited.length; i++) {
        visited[i] = -1;
      }

      DFS(visited, source, destination);
      memoHash.get(max).put(min, maxWeightInPath(source, destination, visited));
    }

    return memoHash.get(max).get(min);
  }

  // You can add extra function if needed
  // --------------------------------------------


  // prims to process circular graph into a MST
  void prims() {
    boolean[] taken = new boolean[V]; // default false
    // System.out.println("taken size: " + taken.length + " " + V);
    PriorityQueue<IntegerTriple> pq = new PriorityQueue<IntegerTriple>(); // weight, start, end

    // init a new adjList
    ArrayList<ArrayList<IntegerPair>> newAdjList = new ArrayList<ArrayList<IntegerPair>>();
    for (int i=0; i<V; i++) {
      newAdjList.add(new ArrayList<IntegerPair>());
    }

    process(taken, pq, 0);

    while (!pq.isEmpty()) {
      IntegerTriple front = pq.poll();

      // System.out.println("Front: " + front.second() + " to " + front.third());
      if (!taken[front.third()]) {
        newAdjList.get(front.second()).add(new IntegerPair(front.third(), front.first()));
        newAdjList.get(front.third()).add(new IntegerPair(front.second(), front.first()));
        // System.out.println("Linking " + front.second() + " and " + front.third());
        process(taken, pq, front.third());
      }
    }

    AdjList = newAdjList;
  }

  void process(boolean[] taken, PriorityQueue<IntegerTriple> pq, int idx) {
    // System.out.println(idx);
    // System.out.println(taken.length);

    taken[idx] = true;

    for (IntegerPair e: AdjList.get(idx)) {
      // System.out.println("processing: " + e.first());
      if (!taken[e.first()]) {
        pq.offer(new IntegerTriple(e.second(), idx, e.first())); // to compare weight first
      }
    }
  }

  // recursive DFS traversal to find way to dest
  void DFS(int[] visited, int v, int dest) {
    ArrayList<IntegerPair> edges = AdjList.get(v);
    for (IntegerPair edge: edges) {
      if (visited[edge.first()] == -1) {
        visited[edge.first()] = v; // it points to its previous node
        if (edge.first() == dest) break;
        DFS(visited, edge.first(), dest);
      }
    }
  }

  // traverse back to origin to find max weight
  int maxWeightInPath(int source, int dest, int[] visited) {
    int curr = dest;
    int maxWeight = -1;
    int next = -1;
    int weightToNext = -1;
    // System.out.println("Visited: " + Arrays.toString(visited));
    while(curr != source) {
      // System.out.println("curr: " + curr);
      next = visited[curr];
      // System.out.println(next);

      for (IntegerPair pair: AdjList.get(curr)) {
        if (pair.first() == next) {
          weightToNext = pair.second();
        }
      }

      if (weightToNext > maxWeight) {
        maxWeight = weightToNext;
      }

      curr = next;
    }

    return maxWeight;
  }

  // --------------------------------------------

  void run() throws Exception {
    // do not alter this method
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      V = sc.nextInt();

      // clear the graph and read in a new graph as Adjacency List
      AdjList = new ArrayList<ArrayList<IntegerPair>>();
      for (int i = 0; i < V; i++) {
        AdjList.add(new ArrayList < IntegerPair >());

        int k = sc.nextInt();
        while (k-- > 0) {
          int j = sc.nextInt(), w = sc.nextInt();
          AdjList.get(i).add(new IntegerPair(j, w)); // edge (corridor) weight (effort rating) is stored here
        }
      }

      PreProcess(); // you may want to use this function or leave it empty if you do not need it

      int Q = sc.nextInt();
      while (Q-- > 0)
        pr.println(Query(sc.nextInt(), sc.nextInt()));
      pr.println(); // separate the answer between two different graphs
    }

    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    GettingFromHereToThere ps4 = new GettingFromHereToThere();
    ps4.run();
  }
}



class IntegerScanner { // coded by Ian Leow, using any other I/O method is not recommended
  BufferedInputStream bis;
  IntegerScanner(InputStream is) {
    bis = new BufferedInputStream(is, 1000000);
  }
  
  public int nextInt() {
    int result = 0;
    try {
      int cur = bis.read();
      if (cur == -1)
        return -1;

      while ((cur < 48 || cur > 57) && cur != 45) {
        cur = bis.read();
      }

      boolean negate = false;
      if (cur == 45) {
        negate = true;
        cur = bis.read();
      }

      while (cur >= 48 && cur <= 57) {
        result = result * 10 + (cur - 48);
        cur = bis.read();
      }

      if (negate) {
        return -result;
      }
      return result;
    }
    catch (IOException ioe) {
      return -1;
    }
  }
}



class IntegerPair implements Comparable < IntegerPair > {
  Integer _first, _second;

  public IntegerPair(Integer f, Integer s) {
    _first = f;
    _second = s;
  }

  public int compareTo(IntegerPair o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else
      return this.second() - o.second();
  }

  Integer first() { return _first; }
  Integer second() { return _second; }
}



class IntegerTriple implements Comparable < IntegerTriple > {
  Integer _first, _second, _third;

  public IntegerTriple(Integer f, Integer s, Integer t) {
    _first = f;
    _second = s;
    _third = t;
  }

  public int compareTo(IntegerTriple o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else if (!this.second().equals(o.second()))
      return this.second() - o.second();
    else
      return this.third() - o.third();
  }

  Integer first() { return _first; }
  Integer second() { return _second; }
  Integer third() { return _third; }
}