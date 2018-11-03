import java.util.ArrayList;
import java.util.HashMap;

public class FacultyStaff {
	
	private String staffID;
	private String staffName;
	private String coordinatorOf;
	private boolean isProfessor;
	private ArrayList<String> courseList;
	private HashMap<Semester, String> workLoadBySemester;
	private String facultyName;
	
	/**This method construct a FacultyStaff object
	 * @param id, name
	 * Instantiate staffID and staffName
	 */
	public FacultyStaff(String id, String name) {
		staffID = id;
		staffName = name;
	}
	/**This method sets the coordinator status for current staff for the course with code passed as parameter
	 * 
	 * @param courseCode
	 */
	public void setCoordinator(String courseCode) { //need interface with faculty to change attribute in course
		
	}
	/**
	 * This method adds the passed courseCode to the current Staff's workload as a string
	 * @param courseCode
	 */
	public void addCourse(String courseCode) {
		courseList.add(courseCode);
	}
	
	/**
	 * @return staffID
	 */
	public String getStaffID(){
		return staffID;
	}
	
	/**
	 * @return staffName
	 */
	public String getStaffName() {
		return staffName;
	}
	/**
	 * 
	 * @return facultyName
	 */
	public String getFacultyName() {
		return facultyName;
	}
}
