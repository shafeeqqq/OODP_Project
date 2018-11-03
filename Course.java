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
	
	public String getCourseName() {
		return courseName;
	}
	public StudentInfo[] getStudentInfoList() {
		return studentInfoList;
	}
	/**This method returns the courseCode as a String
	 * @param code
	 */
	public void setCourseCode(String code) {
		courseCode = code;
	}
	
	/**
	 * @return
	 */
	public String getcourseName() {
		return courseName;
	}
	
	/**
	 * @param name
	 */
	public void setCourseName(String name) {
		courseName = name;
	}
	
	/**
	 * @return
	 */
	public int getMaxEnrollment() {
		return maxEnrollment;
	}
	
	/**
	 * @param max
	 */
	public void setMaxEnrollment(int max) {
		maxEnrollment = max;
	}
	
	/**
	 * @return
	 */
	public String getCoordinator() {
		return coordinator;
	}
	
	/**
	 * @param staff
	 */
	public void setCoordinator(FacultyStaff staff) {
		coordinator=staff.getStaffID();
	}
	public ArrayList<Component> getAssessment() {
		return assessment;
	}
	public void setAssessment(ArrayList<Component> assessment) {
		this.assessment = assessment;
	}
	public LessonType getLessonType() {
		return lessonType;
	}
	public void setLessonType(LessonType lessonType) {
		this.lessonType = lessonType;
	}
	public void setCoordinator(String coordinator) {
		this.coordinator = coordinator;
	}
}
