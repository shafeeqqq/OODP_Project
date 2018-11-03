import java.util.ArrayList;
import java.util.HashMap;

public class Faculty {	//need interface w university to add student and course
	private ArrayList<Student> studentList;
	private ArrayList<FacultyStaff> staffList;
	private String facultyName;
	private HashMap<Semester, ArrayList<Course>> courseListBySem;
	/**
	 * this method adds a new student to the student list inside the Faculty object
	 * @param studentname
	 * @param matricNo
	 */
	public void addStudent(String studentname,String matricNo) {
		Student newStudent = new Student(studentname, matricNo);
		studentList.add(newStudent);
	}
	/**
	 * this method adds a course to the faculty, starting with all the parameters as passed and
	 * the rest is instantiated to default values
	 * @param courseCode
	 * @param courseName
	 * @param coordinator
	 * @param tutorialGroup
	 * @param LessonType
	 */
	public void addCourse(String courseCode, String courseName, String coordinator, String[] tutorialGroup, LessonType lessonType) {
		Course newCourse = new Course(courseCode, courseName, coordinator, tutorialGroup, lessonType);
	}
	
	public void addStaff(String staffName,String staffID) {
		FacultyStaff newStaff = new FacultyStaff(staffName, staffID);
	}
	
	
	
	
	
}
