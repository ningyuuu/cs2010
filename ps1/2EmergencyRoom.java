import java.util.*;
import java.io.*;

// write your matric number here: A0111277M
// write your name here: Ning Yu
// write list of collaborators here: nil
// year 2017 hash code: DYrHG8eLvSQ5iyQLrAhu (do NOT delete this line)

class EmergencyRoom {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  PatientQueue patientQueue;

  public EmergencyRoom() {
    // Write necessary code during construction
    //
    // write your answer here
    this.patientQueue = new PatientQueue();
  }

  void ArriveAtHospital(String patientName, int emergencyLvl) {
    // You have to insert the information (patientName, emergencyLvl)
    // into your chosen data structure
    //
    // write your answer here
    Patient newPatient = new Patient(patientName, emergencyLvl);
    patientQueue.insert(newPatient);
    // System.out.println("ArriveAtHospital");
  }

  void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
    // You have to update the emergencyLvl of patientName to
    // emergencyLvl += incEmergencyLvl
    // and modify your chosen data structure (if needed)
    //
    // write your answer here
    // patientQueue.update(patientName, incEmergencyLvl);
    return;
    // System.out.println("Size after update: " + patientQueue.size());
  }

  void Treat(String patientName) {
    // This patientName is treated by the doctor
    // remove him/her from your chosen data structure
    //
    // write your answer here
    patientQueue.extractMax();
    // System.out.println("Size after Treat: " + patientQueue.size());
  }

  String Query() {
    String ans = "The emergency room is empty";

    // You have to report the name of the patient that the doctor
    // has to give the most attention to currently. If there is no more patient to
    // be taken care of, return a String "The emergency room is empty"
    //
    // write your answer here

    // System.out.println("Query");
    if (patientQueue.isEmpty()) {
      return ans;
    }
    return patientQueue.getMax().getName();
  }

  void run() throws Exception {
    // do not alter this method

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >= N
    while (numCMD-- > 0) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      switch (command) {
        case 0: ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 1: UpdateEmergencyLvl(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 2: Treat(st.nextToken()); break;
        case 3: pr.println(Query()); break;
      }
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    EmergencyRoom ps1 = new EmergencyRoom();
    ps1.run();
  }
}

class Patient {
	private String name;
	private int priority;
  private int order;
  private static int currOrder = 0;

	public Patient(String name, int priority) {
		this.name = name;
		this.priority = priority;
    this.order = currOrder;
    currOrder++;
	}

	public void setLevel(int newPriority) {
		priority = newPriority;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return priority;
	}

  public int getOrder() {
    return order;
  }
}

class PatientQueue {
	private ArrayList<Patient> patients;
	private int len;
	private Patient dummy;

	public PatientQueue() {
		dummy = new Patient("DUMMY", 0);
		patients = new ArrayList<Patient>();
		patients.add(dummy);
		len = 0;
	}

	// heap APIs
	private int parent(int i) {
		return i>>1;
	}

	private int left(int i) {
		return i<<1;
	}

	private int right(int i) {
		return (i<<1) + 1;
	}

	private void shiftup(int i) {	
		while (i > 1 && (
      patients.get(parent(i)).getLevel() < patients.get(i).getLevel() ||
        (
          patients.get(parent(i)).getLevel() == patients.get(i).getLevel() && 
          patients.get(parent(i)).getOrder() > patients.get(i).getOrder() 
        ) 
      )
    ) {
			Patient temp = patients.get(i);
			patients.set(i, patients.get(parent(i)));
			patients.set(parent(i), temp);
			i = parent(i);
		}
	}

	private void shiftdown(int i) {
		while (i <= len) {
			int maxL = patients.get(i).getLevel();
      int maxO = patients.get(i).getOrder();
			int max_id = i;
			if (left(i) <= len && 
        (
          maxL < patients.get(left(i)).getLevel() || 
          maxL == patients.get(left(i)).getLevel() && maxO > patients.get(left(i)).getOrder()
        )
      ) {
				maxL = patients.get(left(i)).getLevel();
				max_id = left(i);
			}
			if (right(i) <= len &&
        (
          maxL < patients.get(right(i)).getLevel() ||
          maxL == patients.get(right(i)).getLevel() && maxO > patients.get(right(i)).getOrder()
        )
      ) {
				maxL = patients.get(right(i)).getLevel();
				max_id = right(i);
			}
			if (max_id != i) {
				Patient temp = patients.get(i);
				patients.set(i, patients.get(max_id));
				patients.set(max_id, temp);
				i = max_id;	
			} else {
				break;
			}
		}
	}

	public int size() {
		return len;
	}

	public boolean isEmpty() {
		return len == 0;
	}

	public void insert(Patient patient) {
		len++;
		if (len >= patients.size()) {
			patients.add(patient);
		} else {
			patients.set(len, patient);
		}
		shiftup(len);
	}

	public Patient getMax() {
		if (isEmpty()) {
			return null;
		}
		return patients.get(1);
	}

  public Patient extractMax() {
    Patient target = patients.get(1);
    patients.set(1, patients.get(len));
    len--;
    shiftdown(1); 
    return target;
  }
}