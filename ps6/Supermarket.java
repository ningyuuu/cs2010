// Copy paste this Java Template and save it as "Supermarket.java"
import java.util.*;
import java.io.*;

// write your matric number here:
// write your name here:
// write list of collaborators here:
// year 2017 hash code: I35b3wcHtTXKryJZ4S0R (do NOT delete this line)

class Supermarket {
    private int N; // number of items in the supermarket. V = N+1 
    private int K; // the number of items that Ketfah has to buy
    private int[] shoppingList; // indices of items that Ketfah has to buy
    private int[][] T; // the complete weighted graph that measures the direct wheeling time to go from one point to another point in seconds

    // if needed, declare a private data structure here that
    // is accessible to all methods in this class
    // --------------------------------------------
    private int minCost;
    private ArrayList<Integer> path;
    private boolean[] visited;
    private int[][] Tshrink; // apsp shrink

    public Supermarket() {
    // Write necessary code during construction
    //
    // write your answer here



    }

    int Query() {
        // You have to report the quickest shopping time that is measured
        // since Ketfah enters the supermarket (vertex 0),
        // completes the task of buying K items in that supermarket,
        // then reaches the cashier of the supermarket (back to vertex 0).
        //
        // write your answer here
        minCost = Integer.MAX_VALUE;
        path = new ArrayList<Integer>();
        visited = new boolean[K+1];
        Arrays.fill(visited, false);

        floydWarshall();
        shrinkArr();

        backtracking(0);
        return minCost;
    }

  // You can add extra function if needed
  // --------------------------------------------

    private void backtracking(int u) {
        path.add(u);
        visited[u] = true;

        boolean all_visited = true;
        for (int v=0; v<=K; v++) {
            if (!visited[v]) {
                all_visited = false;
            }
        }

        if (all_visited) {
            int cost = 0;
            // System.out.println("size: " + path.size() + " in " + N);
            for (int v=1; v<path.size(); v++) {
                // System.out.println("size " + path.size() + " K " + K);
                // System.out.println("v " + path.get(v) + " v-1 " + path.get(v-1));
                cost += Tshrink[path.get(v-1)][path.get(v)];
            }
            cost += Tshrink[0][path.get(path.size()-1)];

            minCost = Math.min(minCost, cost);
            // System.out.println(minCost);
        } else {
            for (int v=0; v<=K; v++) {
                if (!visited[v]) {
                    backtracking(v);
                }
            }
        }

        visited[u] = false;
        path.remove(path.size() - 1);
    }

    private void floydWarshall() {
        for (int k=0; k<=N; k++) {
            for (int i=0; i<=N; i++) {
                for (int j=0; j<=N; j++) {
                    T[i][j] = Math.min(T[i][j], T[i][k]+T[k][j]);
                }
            }
        }
    }

    private void shrinkArr() {
        Tshrink = new int[K+1][K+1];
        for (int i=0; i<shoppingList.length; i++) {
            Tshrink[0][i+1] = T[0][shoppingList[i]];
            Tshrink[i+1][0] = T[shoppingList[i]][0];
            for (int j=0; j<shoppingList.length; j++) {
                Tshrink[i+1][j+1] = T[shoppingList[i]][shoppingList[j]];
            }
        }
    }


    void run() throws Exception {
        // do not alter this method to standardize the I/O speed (this is already very fast)
        IntegerScanner sc = new IntegerScanner(System.in);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int TC = sc.nextInt(); // there will be several test cases
        while (TC-- > 0) {
            // read the information of the complete graph with N+1 vertices
            N = sc.nextInt(); K = sc.nextInt(); // K is the number of items to be bought

            shoppingList = new int[K];
            for (int i = 0; i < K; i++)
                shoppingList[i] = sc.nextInt();

            T = new int[N+1][N+1];
            for (int i = 0; i <= N; i++)
                for (int j = 0; j <= N; j++)
                    T[i][j] = sc.nextInt();

            pw.println(Query());
        }

        pw.close();
    }

    public static void main(String[] args) throws Exception {
        // do not alter this method
        Supermarket ps6 = new Supermarket();
        ps6.run();
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