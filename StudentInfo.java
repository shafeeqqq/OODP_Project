import java.util.ArrayList;
import java.util.HashMap;

public class StudentInfo {
	
	private String matricNo;
	private HashMap<String, Double> marks = new HashMap<>();	// component title : marks
	private String tutorialGroup;
	
	
	StudentInfo() {}
	
	StudentInfo(String matricNo, String tutorialGroup, ArrayList<String> components) {
		this.matricNo = matricNo;
		this.tutorialGroup = tutorialGroup;
		initialiseMarks(components);
	}

	
	private void initialiseMarks(ArrayList<String> components) {
		for (String item: components) 
			marks.put(item, null);
		
	}

	
	public String getMatricNo() {
		return matricNo;
	}
	
	
	public Double getMarksByComponent(String title) {
		return marks.get(title);
	}
	
	
	public String getTutorialGroup() {
		return tutorialGroup;
	}
	
	
	public void printMarksByComponent() {
		for (String key: marks.keySet())
			System.out.println(key + ": " + marks.get(key));
	
	}
	
	
	public HashMap<String, Double> getMarks() {
		return marks;
	}
	
	
	public void setMarks(HashMap<String, Double> updatedMarks) {
		marks.clear();
		marks.putAll(updatedMarks);
	}
	
	
	
}
