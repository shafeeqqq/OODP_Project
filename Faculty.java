import java.util.ArrayList;
import java.util.HashMap;

public class Faculty {	//need interface w university to add student and course
	private ArrayList<Student> studentList;
	private ArrayList<FacultyStaff> staffList;
	private String facultyName;
	private HashMap<Semester, ArrayList<Course>> courseListBySem;
	public Faculty(String name /*ArrayList<Student> studentList*/) {
		facultyName = name;
	}
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
	public void addCourse(String courseCode, String courseName, String coordinator, ArrayList<String> tutorialGroup, LessonType lessonType,Semester sem) {
		Course newCourse = new Course(courseCode, courseName, coordinator, tutorialGroup, lessonType);
		courseListBySem.get(sem).add(newCourse);
	}
	/**
	 * This method changes the name of the current faculty to the parameter passed
	 * @param facultyName
	 */
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	/**
	 * This method return the faculty name
	 * @return
	 */
	public String getFacultyName() {
		return facultyName;
	}
	public void setCourseListBySem(HashMap<Semester, ArrayList<Course>> courseListBySem) {
		this.courseListBySem = courseListBySem;
	}
	public void addStaff(String staffName,String staffID) {
		FacultyStaff newStaff = new FacultyStaff(staffName, staffID);
	}
	
	public ArrayList<Course> getCourseList(Semester semester) {
		return courseListBySem.get(semester);
	}
	
	public Course getCourse(Semester semester, String courseID) {
		ArrayList<Course> copy =  courseListBySem.get(semester);
		int index=0;
		for (int i =0; i<copy.size(); i++) {
			if (copy.get(i).getCourseCode() == courseID) {
				index = i;
				break;
			}
		}
		return courseListBySem.get(semester).get(index);
	}
	
	
	
	
	
	
}
