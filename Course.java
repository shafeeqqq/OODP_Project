
public class Course {
	private String courseCode;
	private String courseName;
	private FacultyStaff coordinator;
	private int maxEnrollment;
	

	public String getCourseCode() {
		return courseCode;
	}
	
	public void setCourseCode(String code) {
		courseCode = code;
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
	
	public FacultyStaff getCoordinator() {
		return coordinator;
	}
	
	public void setCoordinator(FacultyStaff staff) {
		coordinator = staff;
	}
}
