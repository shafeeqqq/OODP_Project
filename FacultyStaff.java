import java.util.ArrayList;
import java.util.HashMap;

public class FacultyStaff {
	
	private String staffID;
	private String staffName;
	private String coordinatorOf;
	private boolean isProfessor; 
	private ArrayList<String> courseList;
	private HashMap<Semester, ArrayList<String>> workLoadBySemester = new HashMap<>();
	private String facultyName;
	
	/**This method construct a FacultyStaff object
	 * @param id, names
	 * Instantiate staffID and staffName
	 */
	public FacultyStaff(String staffName, String staffID, Semester sem, String facultyName) {
		this.staffName = staffName;
		this.staffID = staffID;
		this.coordinatorOf = null;
		this.facultyName = facultyName;
		workLoadBySemester.put(sem, new ArrayList<String>());
		printDetails();
	}
	
	
	public FacultyStaff(String staffName, String staffID, String coordinatorOf, String facultyName,
			HashMap<Semester, ArrayList<String>> workLoadBySemester) {
		this.staffName = staffName;
		this.staffID = staffID;
		this.facultyName = facultyName;
		this.coordinatorOf = coordinatorOf;
		this.workLoadBySemester.putAll(workLoadBySemester);
	}

	private void printDetails() {
		System.out.print(
				"Name: " + staffName + "\n"
			  + "Staff ID: " + staffID + "\n"
			  + "Faculty: " + facultyName + "\n");
	}
	
	
	public String getCoordinatorOf() {
		return coordinatorOf;
	}
	
	
	/**This method sets the coordinator status for current staff for the course with code passed as parameter
	 * 
	 * @param courseCode
	 */
	public void setCoordinator(String courseCode) { //need interface with faculty to change attribute in course
		this.coordinatorOf = courseCode;
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

	public HashMap<Semester, ArrayList<String>> getWorkLoadBySemester() {
		return workLoadBySemester;
	}

}