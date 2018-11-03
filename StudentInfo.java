import java.util.ArrayList;
import java.util.HashMap;

public class StudentInfo {
	
	private String matricNo;
	private HashMap<String, Double> marks;	// component title : marks
	private String tutorialGroup;
	
	
	StudentInfo(String matricNo, String tutorialGroup) {
		
	}
	
	
	public String getMatricNo() {
		return matricNo;
	}
	public HashMap<String, Double> getMarks() {
		return marks;
	}
	public String getTutorialGroup() {
		return tutorialGroup;
	}
	
	
	
}
