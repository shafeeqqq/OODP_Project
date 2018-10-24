
public class FacultyStaff {
	
	private String staffID;
	private String staffName;
	private boolean isCoordinator;
	private boolean isProfessor;
	private Course[] courseList;
	private String facultyName;
	
	/**This method construct a FacultyStaff object
	 * 
	 */
	public FacultyStaff() {
		
	}
	
	/**
	 * @return
	 */
	public String getStaffID(){
		return staffID;
	}
	
	/**
	 * @return
	 */
	public String getStaffName() {
		return staffName;
	}
	
	/**
	 * @return
	 */
	public boolean isCoordinator() {
		return isCoordinator;
	}
	
	/**
	 * @return
	 */
	public boolean isProfessor() {
		return isProfessor;
	}
	
	/**
	 * @return
	 */
	public Course[] getCourseList() {
		return courseList;
	}
	
	/**
	 * @return
	 */
	public String getFacultyName() {
		return facultyName;
	}
}
