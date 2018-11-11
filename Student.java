import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	
	private String studentName;
	private String matricNo;
	private String facultyName;
	private HashMap<Semester, ArrayList<String>> candidature = new HashMap<>();
	
	public Student(String name, String matricNo, Semester semester, String facultyName ) {
		studentName = name;
		this.matricNo = matricNo;
		candidature.put(semester, new ArrayList<String>());
		this.facultyName = facultyName;
//		printDetails();
	}
	
	public Student(String name, String matricNo, String facultyName, HashMap<Semester, ArrayList<String>> candidature) {
		this.studentName = name;
		this.matricNo = matricNo;
		this.candidature.putAll(candidature);
		this.facultyName = facultyName;
		printDetails();
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
			  + "Matriculation No.: " + matricNo + "\n"
			  + "Candidature: \n" + getCandidatureString() + "\n");
	}
	
	
	private String getCandidatureString() {
		String result = "";
		for (Semester item: candidature.keySet()) {
			result += item.toString() + ": \n";
			result += candidature.get(item).toString();
		}
		return result;
	}


	public void setCandidature(Semester sem, ArrayList<String> stringArr) {
		this.candidature.put(sem, stringArr);
	}

	public void addCourse(Semester sem, String courseCode) {
		candidature.get(sem).add(courseCode);
	}
}
