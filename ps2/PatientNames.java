// Copy paste this Java Template and save it as "PatientNames.java"
import java.util.*;
import java.io.*;

// write your matric number here:A0111277M
// write your name here:Ning Yu
// write list of collaborators here:
// year 2017 hash code: 7TVOcaRb0L0GfdsoDnh5 (do NOT delete this line)

class PatientNames {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  PatientBST males, females;
  // --------------------------------------------



  // --------------------------------------------

  public PatientNames() {
    // Write necessary code during construction;
    //
    // write your answer here

    // --------------------------------------------

    males = new PatientBST();
    females = new PatientBST();

    // --------------------------------------------
  }

  void AddPatient(String patientName, int gender) {
    // You have to insert the information (patientName, gender)
    // into your chosen data structure
    //
    // write your answer here

    // --------------------------------------------
    if (gender == 1) {
      males.insert(patientName);
    } else {
      females.insert(patientName);
    }

    // --------------------------------------------
  }

  void RemovePatient(String patientName) {
    // You have to remove the patientName from your chosen data structure
    //
    // write your answer here

    // --------------------------------------------

    if (males.search(patientName) != null) {
      males.delete(patientName);
    } else {
      females.delete(patientName);
    }

    // --------------------------------------------
  }

  int Query(String START, String END, int gender) {
    int ans = 0;

    // You have to answer how many patient name starts
    // with prefix that is inside query interval [START..END)
    //
    // write your answer here

    // --------------------------------------------
    String start_found, end_found;
    int start_rank, end_rank;

    if (gender != 2) {
      ans += QueryTree(START, END, males);
    }

    if (gender != 1) {
      ans += QueryTree(START, END, females);
    }

    return ans;

    // --------------------------------------------
  }

  int QueryTree(String START, String END, PatientBST tree) {
    tree.printRoot();
    String startFound, endFound;
    int startRank, endRank;

    startFound = tree.startSearch(START);
    endFound = tree.endSearch(END);

    if (startFound != null) {
      startRank = tree.rank(startFound);
    } else {
      return 0;
    }

    if (endFound != null) {
      endRank = tree.rank(endFound);
    } else {
      return 0;
    }

    // System.out.println("endRank " + endRank + " startRank " + startRank);
    return endRank - startRank + 1;
  }

  void run() throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    while (true) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      if (command == 0) // end of input
        break;
      else if (command == 1) // AddPatient
        AddPatient(st.nextToken(), Integer.parseInt(st.nextToken()));
      else if (command == 2) // RemovePatient
        RemovePatient(st.nextToken());
      else // if (command == 3) // Query
        pr.println(Query(st.nextToken(), // START
                         st.nextToken(), // END
                         Integer.parseInt(st.nextToken()))); // GENDER
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    PatientNames ps2 = new PatientNames();
    ps2.run();
  }
}

class PatientVertex {
  public PatientVertex parent, left, right;
  public String patient;
  public int height, size;

  public PatientVertex(String p, int height, int size) {
    this.patient = p;
    parent = null;
    left = null;
    right = null;
    this.height = height;
    this.size = size;
  } 
}

class PatientBST {
  private PatientVertex root;

  public PatientBST() {
    root = null;
  }

  // method to call a search
  public String search(String patientName) {
    PatientVertex res = search(root, patientName);
    if (res == null) return null;
    return res.patient;
  }

  private PatientVertex search(PatientVertex vertex, String patientName) {
    if (vertex == null) {
      return null;
    } else if (vertex.patient.compareTo(patientName) == 0) {
      return vertex;
    } else if (vertex.patient.compareTo(patientName) > 0) {
      return search(vertex.left, patientName);
    } else {
      return search(vertex.right, patientName);
    }
  }

  public void insert(String p) {
    root = insert(root, p);
  }

  private PatientVertex insert(PatientVertex T, String p) {
    if (T == null) {
      T = new PatientVertex(p, 0, 1);
      T = rebalance(T);
      return T;
    }

    if (T.patient.compareTo(p) < 0) {
      T.right = insert(T.right, p);
      T.right.parent = T;
    } else {
      T.left = insert(T.left, p);
      T.left.parent = T;
    }

    T = rebalance(T);
    return T;
  }

  // inorder traversal... not needed for now
  public ArrayList<String> inorderList() {
    ArrayList<String> list = new ArrayList<String>();
    inorderList(root, list);
    return list;
  }

  private void inorderList(PatientVertex T, ArrayList<String> list) {
    if (T==null) return; 
    inorderList(T.left, list);
    list.add(T.patient);
    inorderList(T.right, list);
  }

  public String findMin() {
    return findMin(root);
  }

  private String findMin(PatientVertex T) {
    if (T == null) {
      return null; // BST is completely empty if this happens
    } else if (T.left == null) {
      return T.patient;
    } else {
      return findMin(T.left);
    }
  }

  public String findMax() {
    return findMax(root).patient;
  }

  private PatientVertex findMax(PatientVertex T) {
    if (T == null) {
      return null;
    } else if (T.right == null) {
      return T;
    } else {
      return findMax(T.right);
    }
  }

  public String successor(String patientName) {
    PatientVertex V = search(root, patientName);
    return V == null ? null : successor(V);
  }

  // succesor inner method
  private String successor(PatientVertex T) {
    if (T.right != null) {
      return findMin(T.right);
    } else {
      PatientVertex parent = T.parent;
      PatientVertex current = T;

      while ((parent != null) && (current == parent.right)) {
        current = parent;
        parent = current.parent;
      }

      return parent == null ? null : parent.patient;
    }
  }

  public String predecessor(String patientName) {
    PatientVertex V = search(root, patientName);
    return V == null ? null : predecessor(V).patient;
  }

  private PatientVertex predecessor(PatientVertex T) {
    if (T.left != null) {
      return findMax(T.left);
    } else {
      PatientVertex parent = T.parent;
      PatientVertex current = T;

      while (parent != null && current == parent.left) {
        current = parent;
        parent = current.parent;
      }

      return parent == null ? null : parent; 
    }
  }

  public void delete(String patientName) {
    root = delete(root, patientName);
  }

  private PatientVertex delete(PatientVertex T, String patientName) {
    if (T == null) {
      return T;
    }

    if (T.patient.compareTo(patientName) < 0) {
      T.right = delete(T.right, patientName);
    } else if (T.patient.compareTo(patientName) > 0) {
      T.left = delete(T.left, patientName);
    } else {
      if (T.left == null && T.right == null) {
        T = null;
      } else if (T.left == null && T.right != null) {
        T.right.parent = T.parent;
        T = T.right;
        // System.out.println("DELETE A HERE");
      } else if (T.left != null && T.right == null) {
        // System.out.println("NTH HERE");
        T.left.parent = T.parent;
        T = T.left;
      } else {
        String successorPatient = successor(patientName);
        // System.out.println(successorPatient.getName());
        T.patient = successorPatient;
        T.right = delete(T.right, successorPatient);
      }
    }

    T = rebalance(T);
    return T;
  }

  // self-balancing functions from week 4 lecture

  public int size() {
    return size(root);
  }

  private int size(PatientVertex T) {
    if (T == null) {
      return 0;
    } else {
      return T.size;
    }
  }

  public int rank(String patientName) {
    return rank(root, patientName);
  }

  private int rank(PatientVertex T, String patientName) {
    if (T == null) {
      return 0;
    }

    if (T.patient.compareTo(patientName) > 0) {
      return rank(T.left, patientName);
    } else if (T.patient.compareTo(patientName) < 0) {
      return size(T.left) + 1 + rank(T.right, patientName); 
    } else if (T.patient.compareTo(patientName) == 0) {
      return size(T.left) + 1;
    } else {
      System.out.println("Did not find"); // this shouldn't happen
      return -1;
    }
  }

  private int getHeight(PatientVertex T) {
    if (T == null) {
      return -1;
    } else {
      return T.height;
    }
  }

  private void updateHeight(PatientVertex T) {
    if (T == null) {
      return;
    }
    T.height = Math.max(getHeight(T.left), getHeight(T.right)) + 1;
  }

  private int getBalanceFactor(PatientVertex T) {
    if (T == null) {
      return 0;
    }
    return getHeight(T.left) - getHeight(T.right);
  }

  private PatientVertex rotateLeft(PatientVertex T) {
    // System.out.println("Rotated left at " + T.patient.getName());
    PatientVertex W = T.right;
    W.parent = T.parent;
    T.parent = W;
    T.right = W.left;
    if (W.left != null) {
      W.left.parent = T;
    }
    W.left = T;
    updateSize(T);
    updateHeight(T);
    return W;
  }

  private PatientVertex rotateRight(PatientVertex T) {
    // System.out.println("Rotated right at " + T.patient.getName());
    PatientVertex W = T.left;
    W.parent = T.parent;
    T.left = W.right;
    if (W.right != null) {
      W.right.parent = T;
    }
    W.right = T;
    updateSize(T);
    updateHeight(T);
    return W;
  }

  private PatientVertex rebalance(PatientVertex T) {
    int bf = getBalanceFactor(T);
    if (bf >= 2) {
      if (getBalanceFactor(T.left) == -1) {
        T.left = rotateLeft(T.left);
      }
      T = rotateRight(T);
    } else if (bf <= -2) {
      if (getBalanceFactor(T.right) == 1) {
        T.right = rotateRight(T.right);
      }
      T = rotateLeft(T);
    }

    updateSize(T);
    updateHeight(T);
    return T;
  }

  private void updateSize(PatientVertex T) {
    if (T == null) return;
    T.size = size(T.left) + size(T.right) + 1;
  }

  // functions to quickly find start and end
  public String startSearch(String startString) {
    // gives the first name from (including) the start
    PatientVertex result = startSearch(root, startString);
    if (result != null) {
      return result.patient;
    } else {
      return null;
    }
  }

  private PatientVertex startSearch(PatientVertex T, String startString) {
    if (T == null) {
      return null;
    }

    if (T.patient.compareTo(startString) == 0) {
      return T;
    } else if (T.patient.compareTo(startString) < 0) {
      return startSearch(T.right, startString);
    } else {
      PatientVertex v = startSearch(T.left, startString);
      if (v != null) {
        return v;
      } else {
        // System.out.println(T.patient.getName());
        return T;
      }
    }
  }

  public String endSearch(String endString) {
    PatientVertex result = endSearch(root, endString);
    if (result != null) {
      return result.patient;
    } else {
      return null;
    }
  }

  private PatientVertex endSearch(PatientVertex T, String endString) {
    if (T == null) {
      return null;
    }

    if (T.patient.compareTo(endString) == 0) {
      return predecessor(T);
    } else if (T.patient.compareTo(endString) > 0) {
      return endSearch(T.left, endString);
    } else {
      PatientVertex v = endSearch(T.right, endString);
      if (v != null) {
        return v;
      } else {
        return T;
      }
    }
  }

  // debug functions

  public void printRoot() {
    // System.out.println("Root: " + root.patient + " " + root.size + root.left.patient + root.left.size+ root.right.patient + root.right.size);
  }
}