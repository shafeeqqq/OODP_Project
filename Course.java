import java.util.ArrayList;
import java.util.HashMap;

public class Course {
	
	private String courseCode;
	private String courseName;
	private String coordinator;
	private int maxEnrollment;
	private ArrayList<StudentInfo> studentInfoList  = new ArrayList<>();
	private HashMap<String, Integer> tutorialGroups = new HashMap<>();
	private ArrayList<Component> assessment  = new ArrayList<>();
	private LessonType lessonType;
	
	
	/**
	 * this method construct a course object with the given parameters, anything else is initially 0 or NULL
	 * @param Code
	 * @param Name
	 * @param coordinator_staff
	 * @param group
	 * @param Type
	 */
	public Course (String courseCode, String courseName, String coordinator, LessonType Type, ArrayList<Component> assessment) {
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.coordinator = coordinator;
		this.lessonType = Type;
		this.assessment = assessment;
		initialiseTutorialGroups();
		this.maxEnrollment = initialiseMaxEnrollment();
	}
	
	

	private int initialiseMaxEnrollment() {
		int num = 0;
		if (tutorialGroups == null)
			return 10;
		
		else {
			for (String key: tutorialGroups.keySet())
				num += tutorialGroups.get(key);
		}
		return num;
	}


	private void initialiseTutorialGroups() {
		int tutorialCount = lessonType.getTutorialCount();
		
		if (tutorialCount == 0)
			tutorialGroups = null;
		
		else {
			for (int i=0; i<tutorialCount; i++) 
				tutorialGroups.put("Group " + (i+1), 10);
		}
	}
	
	
	public String getComponentsWeightage(String matricNo) {
		String result="";
		String titleOfComponent;
		char grade;
		Double overallMarks=0.0;
		for (int i =0 ;i < studentInfoList.size();i++) {
			overallMarks=0.0;
			for(int j =0; j<assessment.size(); j++) {
				titleOfComponent = assessment.get(j).getTitle();
				result +=  titleOfComponent + "\t"+ 
				assessment.get(j).getWeightage()+"\t"+ 
				studentInfoList.get(i).getMarks(titleOfComponent)
				+"\t Grade: ";
				overallMarks += assessment.get(j).getWeightage()*
						studentInfoList.get(i).getMarks(titleOfComponent)/100;
			}
			if(overallMarks >= 70) 
				grade = 'A';
			else if (overallMarks >= 60)
				grade = 'B';
			else if (overallMarks >= 50)
				grade = 'C';
			else
				grade = 'F';
			result += "Grade: "+ grade;
			result += "\n";
		}
		return result;
	}
	
	public void printDetails() {
		System.out.print(
				"Course Code: " + courseCode + "\n"
			  + "Course Name: " + courseName + "\n"
			  + "Coordinator: " + coordinator + "\n"
			  + "Lesson Type: " + lessonType.toString() + "\n"
			  + "Assessment: \n"  + getAssessmentString());
		
		if (tutorialGroups == null)
			System.out.println("Tutorial Groups: Not Applicable");
		
		else if (!tutorialGroups.isEmpty())
			System.out.println("Tutorial Groups: " + tutorialGroups.toString());
	}
	
	
	private String getAssessmentString() {
		String result = "";
		for (Component item: assessment)
			result += item.toString();
		return result;
	}
	
	
	public String getTutorialGroup() {
		String group = "";
		
		if (tutorialGroups == null)
			return "N.A.";
		
		else {
			ArrayList<String> keys = new ArrayList<String>(tutorialGroups.keySet());
			return keys.get(0);	// TODO make it random
		}
	}


	/**This method returns the courseCode as a String
	 * @return courseCode
	 */
	public String getCourseCode() {
		return courseCode;
	}
	
	
	/**
	 * This method sets the course code to the parameter given
	 * @param code
	 */
	public void setCourseCode(String code) {
		courseCode = code;
	}
	
	
	/**
	 * @return courseName
	 */
	public String getCourseName() {
		return courseName;
	}
	
	
	/**
	 * this method set the courseName according to the parameters
	 * @param name
	 */
	public void setCourseName(String name) {
		courseName = name;
	}
	
	
	/**
	 * @return maxEnrollment
	 */
	public int getMaxEnrollment() {
		return maxEnrollment;
	}
	
	
	/**
	 * this method sets the max enrollment to the parameter given
	 * @param max
	 */
	public void setMaxEnrollment(int max) {
		maxEnrollment = max;
	}
	
	
	public ArrayList<StudentInfo> getStudentInfoList() {
		return studentInfoList;
	}
	
	
	/**
	 * this method returns the coordinator
	 * @return
	 */
	public String getCoordinator() {
		return coordinator;
	}
	
	
	/**
	 * this method set the type of assessment
	 * @param assessment
	 */
	public void setAssessment(ArrayList<Component> assessment) {
		this.assessment = assessment;
	}
	
	
	/**
	 * this method returns the remaining spaces available
	 * @return
	 */
	public int getVacancy() {
		int count = studentInfoList.size();
		return (maxEnrollment - count);
	}
	
	
	/**
	 * @return lessonType
	 */
	public LessonType getLessonType() {
		return lessonType;
	}
	
	
	/**
	 * this method set the type of lesson
	 * @param lessonType
	 */
	public void setLessonType(LessonType lessonType) {
		this.lessonType = lessonType;
	}
	

	public ArrayList<Component> getAssessment() {
		return assessment;
	}



	public ArrayList<String> getAssessmentTitles() {
		ArrayList<String> result = new ArrayList<>();
		for (Component item: assessment)
			result.add(item.getTitle());
		
		return result;
	}
	
}
