import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private String studentName;
	private String matricNo;
	private HashMap<Semester, ArrayList<String>> candidature;
	public String getMatricNo() {
		return matricNo;
	}
	
	public String getStudentName() {
		return studentName;
	}
	public HashMap<Semester,ArrayList<String>> candidature(){
		return candidature;
	}
	public String getTranscript() {
		
	}
	
	
}
