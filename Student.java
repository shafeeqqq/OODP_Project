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

	public void setCandidature(Semester sem, ArrayList<String> stringArr) {
		this.candidature.put(sem, stringArr);
	}
}
