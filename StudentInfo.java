import java.util.ArrayList;
import java.util.HashMap;

public class StudentInfo {
	/**
	 * This attribute stores the matricNo of the student
	 */
	private String matricNo;
	/**
	 * This hashmap stores the marks being mapped to the component names
	 */
	private HashMap<String, Double> marks = new HashMap<>();	// component title : marks
	/**
	 * This string stores which tutotialGroup the student belongs to
	 */
	private String tutorialGroup;
	
	/**
	 * This constructor is used to create studentinfo object in run-time
	 * @param matricNo			matric no of student
	 * @param tutorialGroup		tut grp student is in
	 * @param components		list of components in course student is taking
	 */
	StudentInfo(String matricNo, String tutorialGroup, ArrayList<String> components) {
		this.matricNo = matricNo;
		this.tutorialGroup = tutorialGroup;
		updateComponents(components);
	}

	/**
	 * This method is used to create studentinfo object during loading
	 * @param currentMatric			matric no of student currently being read
	 * @param currentTutorialGroup	tutorial group student is in
	 * @param marks					list of marks to add to student
	 */
	StudentInfo(String currentMatric, String currentTutorialGroup, HashMap<String, Double> marks) {
		this.matricNo = currentMatric;
		this.tutorialGroup = currentTutorialGroup;
		this.marks.putAll(marks);
	}
	/**
	 * This method updates the different components
	 * @param components list of components in the course student is taking
	 */
	public void updateComponents(ArrayList<String> components) {
		marks.clear();
		for (String item: components) 
			marks.put(item, null);
		
	}

	/**
	 * get method to retrieve matric number
	 * @return matricNo of the student
	 */
	public String getMatricNo() {
		return matricNo;
	}
	
	/**
	 * get all the marks the student has in the course
	 * @param title
	 * @return marks by title
	 */
	public Double getMarksByComponent(String title) {
		return marks.get(title);
	}

	
	/**
	 * get tutorial group student is in
	 * @return tutorial group number
	 */
	public String getTutorialGroup() {
		return tutorialGroup;
	}
	
	/**
	 * This method prints the marks by components
	 */
	public void printMarksByComponent() {
		for (String key: marks.keySet())
			System.out.println(key + ": " + marks.get(key));
	
	}
	
	/**
	 *get list of all marks
	 * @return a hashmap of all the marks mapped to the title of the component
	 */
	public HashMap<String, Double> getMarks() {
		return marks;
	}
	
	/**
	 * This method sets the mark for the current studentinfo
	 * @param updatedMarks 
	 */
	public void setMarks(HashMap<String, Double> updatedMarks) {
		marks.clear();
		marks.putAll(updatedMarks);
		System.out.println("### MARKS ### \n" + getMarksString());
	}
	/**
	 * 
	 * @return a string that includes the title of the component and the marks
	 */
	public String getMarksString() {
		String result = "";
		for (String key: marks.keySet()) 
			result += key + ": " + marks.get(key) + "\n";
		return result;
	}
	
	@Override
	/**
	 * overides the equals method in object superclass to compare studentinfo objects
	 * @param obj 	student object we are comparing
	 * @return true if equal, false if not
	 */
	public boolean equals(Object obj) {
		StudentInfo studentInfo = (StudentInfo) obj;
		if (studentInfo.getMatricNo().equals(this.matricNo))
			return true;
		else
			return false;
	}
	
	
	
}
