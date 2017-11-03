// Copy paste this Java Template and save it as "Bleeding.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0111277M
// write your name here: Ning Yu
// write list of collaborators here:
// year 2017 hash code: HE6548iYssEFmMRgGUDG (do NOT delete this line)

class Bleeding {
  private int V; // number of vertices in the graph (number of junctions in Singapore map)
  private int Q; // number of queries
  private ArrayList <ArrayList<IntegerPair>> AdjList; // the weighted graph (the Singapore map), the length of each edge (road) is stored here too, as the weight of edge
  private int maxValue;
  private TreeSet<IntegerPair> ts;
  private PriorityQueue<IntegerPair> pq;
  private HashMap<Integer, IntegerPair> keymap;
  private int[][] memo;
  private int[] hasMemo;

  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  // --------------------------------------------



  // --------------------------------------------

  public Bleeding() {
    // Write necessary code during construction
    //
    // write your answer here
    maxValue = 999999;
  }

  void PreProcess() {
    // Write necessary code to preprocess the graph, if needed
    //
    // write your answer here
    //------------------------------------------------------------------------- 
    memo = new int[V][V];
    for (int i=0; i<V; i++) {
      for (int j=0; j<V; j++) {
        memo[i][j] = -1;
      }
    }

    hasMemo = new int[V];
    //------------------------------------------------------------------------- 
  }

  int Query(int s, int t, int k) {
    int ans = -1;

    // You have to report the shortest path from Ket Fah's current position s
    // to reach the chosen hospital t, output -1 if t is not reachable from s
    // with one catch: this path cannot use more than k vertices      
    //
    // write your answer here
    if (hasMemo[s] == 0) {
      modDjikstra(s, t, 0);
      hasMemo[s] = 1;
    }

    if (memo[s][t] < maxValue) {
      return memo[s][t];
    } else {
      return -1;
    }

    //------------------------------------------------------------------------- 
  }

  // You can add extra function if needed
  // --------------------------------------------

  // void djikstra(int src, int dest, int k) {
  //   ts = new TreeSet<IntegerPair>();
  //   keymap = new HashMap<Integer, IntegerPair>();
  //   // in this integerpair, first is weight, second is node number

  //   for (int i=0; i<V; i++) {
  //     IntegerPair ip = new IntegerPair(maxValue, i);
  //     keymap.put(i, ip);
  //     ts.add(ip);
  //   }

  //   int curr = src;
  //   updateDist(curr, 0);

  //   for (IntegerPair node: AdjList.get(curr)) {
  //     relax(curr, node.first(), node.second());
  //   }

  //   memo[src][curr] = keymap.get(src).first();
  //   ts.remove(keymap.get(curr));

  //   // injecting the neighbour nodes into the ts
  //   while (!ts.isEmpty()) {
  //     curr = ts.first().second();
  //     for (IntegerPair node: AdjList.get(curr)) {
  //       relax(curr, node.first(), node.second());
  //     }

  //     memo[src][curr] = keymap.get(curr).first();
  //     ts.remove(keymap.get(curr));
  //   }
  // }
  
  void modDjikstra(int src, int dest, int k) {
    pq = new PriorityQueue<IntegerPair>();
    IntegerPair currPair;
    // integerpair is (distance, node)

    for (int i=0; i<V; i++) {
      memo[src][i] = maxValue;
    }

    memo[src][src] = 0;

    pq.add(new IntegerPair(0, src));

    while (pq.size() > 0) {
      currPair = pq.poll();
      if (memo[src][currPair.second()] < currPair.first()) {
        continue;
      } else {
        for (IntegerPair node: AdjList.get(currPair.second())) {
          relax(currPair.second(), node.first(), node.second(), src);
        }
      }
    }
  }

  void updateDist(int node, int value, int src) {
    memo[src][node] = value;
  }

  int getWeight(int node, int src) {
    return memo[src][node];
  }

  void relax(int u, int v, int weightuv, int src) {
    if (getWeight(v, src) > getWeight(u, src) + weightuv) {
      updateDist(v, getWeight(u, src) + weightuv, src);
      pq.offer(new IntegerPair(getWeight(v, src), v));
    }
  }

  // -------------old methods--------------------
  // void updateDist(int node, int value) {
  //   IntegerPair newpair = new IntegerPair(value, node);
  //   IntegerPair oldpair = keymap.get(node);

  //   ts.remove(oldpair);
  //   ts.add(newpair);

  //   keymap.put(node, newpair);
  // }

  // int getWeight(int node) {
  //   return keymap.get(node).first();
  // }

  // void relax(int u, int v, int weightuv) {
  //   if (getWeight(v) > getWeight(u) + weightuv) {
  //     updateDist(v, getWeight(u) + weightuv);
  //   }
  // }

  // --------------------------------------------

  void run() throws Exception {
    // you can alter this method if you need to do so
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) { // TC is the number of problems
      V = sc.nextInt(); // V is the number of vertices

      // clear the graph and read in a new graph as Adjacency List
      AdjList = new ArrayList < ArrayList < IntegerPair > >();
      for (int i = 0; i < V; i++) {
        AdjList.add(new ArrayList < IntegerPair >());

        int k = sc.nextInt();
        while (k-- > 0) {
          int j = sc.nextInt(), w = sc.nextInt();
          AdjList.get(i).add(new IntegerPair(j, w)); // edge (road) weight (in minutes) is stored here
        }
      }

      PreProcess(); // optional

      Q = sc.nextInt(); // Q is the number of queries
      while (Q-- > 0)
        pr.println(Query(sc.nextInt(), sc.nextInt(), sc.nextInt()));

      if (TC > 0)
        pr.println();
    }

    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    Bleeding ps5 = new Bleeding();
    ps5.run();
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