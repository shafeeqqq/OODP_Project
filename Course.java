import java.util.ArrayList;
import java.util.HashMap;

public class Course {
	private String courseCode;
	private String courseName;
	private String coordinator;
	private int maxEnrollment;
	private StudentInfo[] studentInfoList;
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
	public Course (String Code, String Name, String coordinator_staff, ArrayList<String> group, LessonType Type) {
		courseCode = Code;
		courseName = Name;
		coordinator = coordinator_staff;
		tutorialGroup = group;
		lessonType = Type;
		maxEnrollment = 0;
		
	}
	/**This method returns the courseCode as a String
	 * @return courseCode
	 */
	public String getCourseCode() {
		return courseCode;
	}
	/**
	 * 
	 * @param code
	 */
	public void setCourseCode(String code) {
		courseCode = code;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public String getcourseName() {
		return courseName;
	}
	
	public void setCourseName(String name) {
		courseName = name;
	}
	
	public int getMaxEnrollment() {
		return maxEnrollment;
	}
	
	public void setMaxEnrollment(int max) {
		maxEnrollment = max;
	}
	
	public StudentInfo[] getStudentInfoList() {
		return studentInfoList;
	}

	public String getCoordinator() {
		return coordinator;
	}
	
	public void setCoordinator(FacultyStaff staff) {
		coordinator=staff.getStaffID();
	}
	
	public void setAssessment(ArrayList<Component> assessment) {
		this.assessment = assessment;
	}
	
	public int getVacancy() {
		int count = studentInfoList.length;
		return (maxEnrollment - count);
	}
	
	public LessonType getLessonType() {
		return lessonType;
	}
	public void setLessonType(LessonType lessonType) {
		this.lessonType = lessonType;
	}
	
	public ArrayList<Component> getAssessment() {
		return assessment;
	}

	public void setCoordinator(String coordinator) {
		this.coordinator = coordinator;
	}
}
