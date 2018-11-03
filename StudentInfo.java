import java.util.ArrayList;
import java.util.HashMap;

public class StudentInfo {
	
	private String matricNo;
	private HashMap<String, Double> marks;	// component title : marks
	private String tutorialGroup;
	
	StudentInfo(){
	}
	StudentInfo(String matricNo, String tutorialGroup) {
		this.matricNo = matricNo;
		this.tutorialGroup = tutorialGroup;
	}

	public String getMatricNo() {
		return matricNo;
	}
	public Double getMarks(String title) {
		return marks.get(title);
	}
	public String getTutorialGroup() {
		return tutorialGroup;
	}
	
	
	
}
