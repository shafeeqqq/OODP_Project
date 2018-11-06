import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	
	private String studentName;
	private String matricNo;
	private HashMap<Semester, ArrayList<String>> candidature = new HashMap<>();
	
	public Student(String name, String matric, Semester semester) {
		studentName = name;
		matricNo = matric;
		candidature.put(semester, new ArrayList<String>());
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
			  + "Candidature: " + getCandidatureString());
	}
	
	
	private String getCandidatureString() {
		String result = "";
		for (Semester item: candidature.keySet()) {
			result += item.toString() + ": \n";
			result += candidature.get(item).toString();
		}
		return null;
	}

	public void printCandidature() {
		for (Semester sem : candidature.keySet()) {
			System.out.println(sem.getYear() + " "+ sem.getNumber());
			for (String course: candidature.get(sem)) {
				System.out.print(course+ " ");
			}
			System.out.println();		}
	}

	public void setCandidature(Semester sem, ArrayList<String> stringArr) {
		this.candidature.put(sem, stringArr);
	}
}
