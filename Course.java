import java.util.ArrayList;
import java.util.HashMap;

public class Course {
	private String courseCode;
	private String courseName;
	private String coordinator;
	private int maxEnrollment;
	private ArrayList<StudentInfo> studentInfoList;
	private ArrayList<String> tutorialGroup;
	private ArrayList<Component> assessment;
	private LessonType lessonType;
	
	/**
	 * this method construct a course object with the given parameters, anything else is initially 0 or NULL
	 * @param Code
	 * @param Name
	 * @param coordinator_staff
	 * @param group
	 * @param Type
	 */
	public Course (String courseCode, String courseName, String coordinator, LessonType Type) {
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.coordinator = coordinator;
//		this.tutorialGroup = group; TODO: add method to name tutorial groups if applicable
		this.lessonType = Type;
		this.maxEnrollment = 0;
		
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
	 * this method set the coordinator to the parameter
	 * @param staff
	 */
	public void setCoordinator(FacultyStaff staff) {
		coordinator=staff.getStaffID();
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

	public void setCoordinator(String coordinator) {
		this.coordinator = coordinator;
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
}
