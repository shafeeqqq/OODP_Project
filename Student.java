<<<<<<< HEAD
import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private String studentName;
	private String matricNo;
	private HashMap<Semester, ArrayList<String>> candidature;
	
	public Student(String name, String matric) {
		studentName = name;
		matricNo = matric;		
	}
	public String getMatricNo() {
		return matricNo;
	}
	
	public String getStudentName() {
		return studentName;	
	}

	public HashMap<Semester, ArrayList<String>> getCandidature() {
		return candidature;
	}
	
	public void printDetails() {
		System.out.print(
				"Name: " + studentName + "\n"
			  + "Matriculation No.: " + matricNo + "\n");
	}

	
	
}
=======
import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private String studentName;
	private String matricNo;
	private HashMap<Semester, ArrayList<String>> candidature;
	
	public Student(String name, String matric) {
		studentName = name;
		matricNo = matric;		
	}
	public String getMatricNo() {
		return matricNo;
	}
	
	public String getStudentName() {
		return studentName;	
	}
	public HashMap<Semester, ArrayList<String>> getCandidature() {
		return candidature;
	}
	public void printCandidature() {
		for (Semester sem : candidature.keySet()) {
			System.out.println(sem.getYear() + " "+ );
			System.out.println(candidature.get(sem));
		}
	}

	public void setCandidature(Semester sem, ArrayList<String> stringArr) {
		this.candidature.put(sem, stringArr);
	}
}
>>>>>>> branch 'master' of https://github.com/shafeeqqq/OODP_Project.git
