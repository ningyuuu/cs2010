// Copy paste this Java Template and save it as "HospitalRenovation.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0111277M
// write your name here: Ning Yu
// write list of collaborators here:
// year 2017 hash code: AlaYnzmQ65P4x2Uc559u (do NOT delete this line)

class HospitalRenovation {
  private int V; // number of vertices in the graph (number of rooms in the hospital)
  private ArrayList<ArrayList<Integer>> AdjList; // the graph (the hospital)
  private int[] RatingScore; // the weight of each vertex (rating score of each room)

  // if needed, declare a private data structure here that
  // is accessible to all methods in this class

  public HospitalRenovation() {
    // Write necessary code during construction
    //
    // write your answer here
  }

  int Query() {
    int ans = 0;

    // You have to report the rating score of the critical room (vertex)
    // with the lowest rating score in this hospital
    //
    // or report -1 if that hospital has no critical room
    //
    // write your answer here



    return getLowestRating(getCriticalNodes());
  }

  // You can add extra function if needed
  // --------------------------------------------

  int dfsNumNodes(int ignored) {
    // traverse though the graph with dfs, but ignore one node (assume its removed)
    // O(V+E)
    int[] visited = new int[V];

    if (ignored == 0) {
      if (V == 1) {
        return V;
      } 
      DFSrec(visited, ignored, 1);
    } else {
      DFSrec(visited, ignored, 0);
    }

    // System.out.println(visited);
    return Arrays.stream(visited).sum();
  }

  void DFSrec(int[] visited, int ignored, int u) {
    visited[u] = 1;
    ArrayList<Integer> edges = AdjList.get(u);
    for (int i: edges) {
      if (i != ignored && visited[i] == 0) {
        DFSrec(visited, ignored, i);
      }
    }
  }

  ArrayList<Integer> getCriticalNodes() {
    // for each node, remove it, and traverse + test if it's length = length - 1
    ArrayList<Integer> crits = new ArrayList<Integer>();

    for (int i=0; i<V; i++) {
      if (dfsNumNodes(i) < V-1) {
        crits.add(i);
      }
    }

    return crits;
  }

  int getLowestRating(ArrayList<Integer> nodes) {

    if (nodes.size() == 0) return -1;

    int smallest = nodes.get(0);
    int rating = RatingScore[smallest];

    for (int node: nodes) {
      if (RatingScore[node] < rating) {
        smallest = node;
        rating = RatingScore[node];
      }
    }

    return rating;
  }



  // --------------------------------------------

  void run() throws Exception {
    // for this PS3, you can alter this method as you see fit

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int TC = Integer.parseInt(br.readLine()); // there will be several test cases
    while (TC-- > 0) {
      br.readLine(); // ignore dummy blank line
      V = Integer.parseInt(br.readLine());

      StringTokenizer st = new StringTokenizer(br.readLine());
      // read rating scores, A (index 0), B (index 1), C (index 2), ..., until the V-th index
      RatingScore = new int[V];
      for (int i = 0; i < V; i++)
        RatingScore[i] = Integer.parseInt(st.nextToken());

      // clear the graph and read in a new graph as Adjacency Matrix
      AdjList = new ArrayList<ArrayList<Integer>>();
      for (int i = 0; i < V; i++) {
        ArrayList<Integer> edgesList = new ArrayList<Integer>();
        st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());
        while (k-- > 0) {
          int j = Integer.parseInt(st.nextToken());
          // add the edge to the inner arraylist
          edgesList.add(j);
          // AdjList[i][j] = 1; // edge weight is always 1 (the weight is on vertices now)
        }
        AdjList.add(edgesList);
      }

      pr.println(Query());
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    HospitalRenovation ps3 = new HospitalRenovation();
    ps3.run();
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