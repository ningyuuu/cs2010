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
  PatientBST patients;
  // --------------------------------------------



  // --------------------------------------------

  public PatientNames() {
    // Write necessary code during construction;
    //
    // write your answer here

    // --------------------------------------------

    patients = new PatientBST();

    // --------------------------------------------
  }

  void AddPatient(String patientName, int gender) {
    // You have to insert the information (patientName, gender)
    // into your chosen data structure
    //
    // write your answer here

    // --------------------------------------------

    patients.insert(new Patient(patientName, gender));

    // --------------------------------------------
  }

  void RemovePatient(String patientName) {
    // You have to remove the patientName from your chosen data structure
    //
    // write your answer here

    // --------------------------------------------

    patients.delete(patientName);

    // --------------------------------------------
  }

  int Query(String START, String END, int gender) {
    int ans = 0;

    // You have to answer how many patient name starts
    // with prefix that is inside query interval [START..END)
    //
    // write your answer here

    // --------------------------------------------

    ArrayList<Patient> list = patients.inorderList();
    int count = 0;

    for (Patient p: list) {
      if (p.compareTo(START) >= 0 && p.compareTo(END) < 0) {
        if (gender == 0 || p.getGender() == gender) {
          count++;
        }
      }
    }
    return count;

    // --------------------------------------------

    return ans;
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

class Patient {
  private int gender;
  private String name;

  public Patient(String name, int gender) {
    this.name = name;
    this.gender = gender;
  }

  public String getName() {
    return name;
  }

  public int getGender() {
    return gender;
  }

  public int compareTo(Patient other) {
    return name.compareTo(other.getName());
  }

  public int compareTo(String otherName) {
    return name.compareTo(otherName);
  }
}

class PatientVertex {
  public PatientVertex parent, left, right;
  public Patient patient;
  public int height; //not used for 1a

  public PatientVertex(Patient p) {
    this.patient = p;
    parent = null;
    left = null;
    right = null;
    height = 0;
  } 
}

class PatientBST {
  private PatientVertex root;

  public PatientBST() {
    root = null;
  }

  // method to call a search
  public Patient search(String patientName) {
    PatientVertex res = search(root, patientName);
  }

  private Patient search(PatientVertex vertex, String patientName) {
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

  public void insert(Patient p) {
    root = insert(root, p);
  }

  private PatientVertex insert(PatientVertex T, Patient p) {
    if (T == null) {
      return new PatientVertex(p);
    }

    if (T.patient.compareTo(p) < 0) {
      T.right = insert(T.right, p);
      T.right.parent = T;
    } else {
      T.left = insert(T.left, p);
      T.right.parent = T;
    }

    return T;
  }

  // inorder traversal... not needed for now
  public ArrayList<Patient> inorderList() {
    ArrayList<Patient> list = new ArrayList<Patient>();
    inorderList(root, list);
    return list;
  }

  private void inorderList(PatientVertex T, ArrayList<Patient> list) {
    if (T==null) return;
    inorderList(T.left, list);
    list.add(T);
    inorderList(T.right, list);
  }

  public Patient successor(String patientName) {
    PatientVertex V = search(root, patientName);
    return V == null ? null : successor(V).patient;
  }


  public PatientVertex successorV(String patientName) {
    PatientVertex V = search(root, patientName);
    return V == null ? null : successor(V);
  }

  // succesor inner method
  private PatientVertex successor(PatientVertex T) {
    if (T.right != null) {
      return findMin(T.right);
    } else {
      PatientVertex parent = T.parent;
      PatientVertex current = T;

      while ((parent != null) && (current == parent.right)) {
        return parent == null ? null : parent;
      }
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
      } else if (T.left != null && T.right != null) {
        T.left.parent = T.parent;
        T = T.left;
      } else {
        Patient successorPatient = successor(patientName);
        T.patient = successorPatient;
        T.right = delete(T.right, successorPatient);
      }
    }
  }
}