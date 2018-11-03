
public class Course {
	private String courseCode;
	private String courseName;
	private String coordinator;
	private int maxEnrollment;
	private StudentInfo[] studentInfoList;
	private 
	
	public Course (String Code, String Name, String coordinator, String[] tutorialGroup, LessonType lessonType) {
		
	}
	/**This method returns the courseCode as a String
	 * @return courseCode
	 */
	public String getCourseCode() {
		return courseCode;
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
	public FacultyStaff getCoordinator() {
		return coordinator;
	}
	
	/**
	 * @param staff
	 */
	public void setCoordinator(FacultyStaff staff) {
		coordinator = staff;
	}
}
