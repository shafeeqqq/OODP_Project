import java.util.ArrayList;
import java.util.HashMap;
/**
 * 
 * Represents a staff in a specific faculty.
 *
 */
public class FacultyStaff {
	/**
	 * The staff ID of this staff. 
	 */
	private String staffID;
	/**
	 * The name of this staff.
	 */
	private String staffName;
	/**
	 * The course code of the course which this staff is the coordinator.
	 * One course only has one coordinator and a staff can only coordinate one course.
	 * If the staff is not a coordinator for any class the string will be NULL. 
	 */
	private String coordinatorOf;
	/**
	 * An Arraylist of courses 
	 */
	private HashMap<Semester, ArrayList<String>> workLoadBySemester = new HashMap<>();
	private String facultyName;
	
	/**This method construct a FacultyStaff object
	 * @param staffName				name of staff
	 * @param staffID				id of fac staff
	 * @param facultyName			name of faculty staff is part of
	 * @param sem					sem fac staff is added
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
	
	/**
	 * another constructors for facultystaff
	 * @param staffName				name of staff
	 * @param staffID				id of fac staff
	 * @param coordinatorOf			course the staff is a coordinator of, null if NA
	 * @param facultyName			name of faculty staff is part of
	 * @param workLoadBySemester	arraylist of semester and courses part of
	 */
	public FacultyStaff(String staffName, String staffID, String coordinatorOf, String facultyName,
			HashMap<Semester, ArrayList<String>> workLoadBySemester) {
		this.staffName = staffName;
		this.staffID = staffID;
		this.facultyName = facultyName;
		if (coordinatorOf.equals("null"))
			this.coordinatorOf = null;
		else 
			this.coordinatorOf = coordinatorOf;
		this.workLoadBySemester.putAll(workLoadBySemester);
	}
	/**
	 * Method to print Name, Staff ID and Faculty of the FacultyStaff
	 */
	private void printDetails() {
		System.out.print(
				"Name: " + staffName + "\n"
			  + "Staff ID: " + staffID + "\n"
			  + "Faculty: " + facultyName + "\n");
	}
	
	/**
	 * This is a get method to retrieve which course the Faculty Staff is a coordinator of. Returns NULL is the Faculty Staff
	 * is not a coordinator of any course.
	 * 
	 * @return A String containing the course code of which the faculty staff is a coordinator of
	 */
	public String getCoordinatorOf() {
		return coordinatorOf;
	}
	
	
	/**
	 * This method sets the coordinator status for current staff for the course with code passed as parameter
	 * @param courseCode 	String of the course code which the FacultyStaff is the coordinator of
	 */
	public void setCoordinator(String courseCode) { //need interface with faculty to change attribute in course
		this.coordinatorOf = courseCode;
	}

	
	/**
	 * This method adds the passed courseCode to the current Staff's workload as a string
	 * since we are assuming the Staff can be part of more than 1 course
	 * 
	 * @param courseCode	String of the course code which the staff is part of
	 */
	
	
	/**
	 * get method for staff ID
	 * @return staffID
	 */
	public String getStaffID(){
		return staffID;
	}
	
	/**
	 * get method for staff name
	 * @return staffName
	 */
	public String getStaffName() {
		return staffName;
	}
	/**
	 * get method for faculty staff is part of
	 * @return facultyName
	 */
	public String getFacultyName() {
		return facultyName;
	}
	
	/**
	 * get method for staff's workload by semester
	 * @return Hashmap with semester as key and list of courses as key values
	 */
	public HashMap<Semester, ArrayList<String>> getWorkLoadBySemester() {
		return workLoadBySemester;
	}

}