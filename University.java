import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class University {
	
	private String name;
	private ArrayList<Faculty> facultyList = new ArrayList<>();
	private ArrayList<Semester> semesterList = new ArrayList<>();
	
	private ArrayList<String> matricNoList = new ArrayList<>();
	private ArrayList<String> staffIDList = new ArrayList<>();
	
	University(String universityName) {
		this.name = universityName;
		initialiseSemesterList();
	}
	
	/**
	 * initialise the semesterList with Semester (current year, 1)
	 */
	private void initialiseSemesterList() {
		int year =  Calendar.getInstance().get(Calendar.YEAR);
		Semester sem = new Semester(year, 1);
		semesterList.add(sem);
	}


	public void addFaculty(String facultyName) {
		Faculty newFaculty = new Faculty(facultyName, getCurrentSemester());
		facultyList.add(newFaculty);
	}
	
	public ArrayList<Faculty> getFacultyList() {
		return facultyList;
	}
	
	public ArrayList<String> getFacultyNameList() {
		ArrayList<String> facultyNameList = new ArrayList<>();
		
		for (Faculty faculty: facultyList) 
			facultyNameList.add(faculty.getFacultyName());
		return facultyNameList;
	}
	
	
	public ArrayList<Semester> getSemesterList() {
		return semesterList;
	}
	
	
	public void addStaffToFaculty(String facultyName, String staffName) {
		Faculty faculty = getFacultyByName(facultyName);
		faculty.addStaff(staffName, generateStaffID());
	}
	
	public Student addStudentToFaculty(String facultyName, String studentName) {
		Faculty faculty = getFacultyByName(facultyName);
		Student newStudent = faculty.addStudent(studentName, generateMatricNo());
		return newStudent;
	}
	
	public Course addCourseToFaculty(String facultyName, String courseCode, String courseName,
			String coordinator, LessonType lessonType, ArrayList<Component> assessment, Semester semester) {
		
		Faculty faculty = getFacultyByName(facultyName);
		Course newCourse = faculty.addCourse(courseCode, courseName, coordinator, lessonType, assessment, semester);
		return newCourse;
	}
	
	
	public Faculty getFacultyByName(String facultyName) {
		for (Faculty faculty: facultyList) {
			if (faculty.getFacultyName().equals(facultyName))
				return faculty;
		}
			return null;
	}


	public String generateMatricNo() {
		int base = 100001;
		int num;
		
		if (matricNoList.isEmpty())
			num = base;
		else 
			num = getLast(0) + 1;	// type=0 for student
		
		String newMatricNo = "S" + num;
		
		if (!matricNoList.contains(newMatricNo))
			return newMatricNo;
		else
			return "Error generating Matric No";
	}
	

	public String generateStaffID() {
		int base = 100001;
		int num;
		
		if (staffIDList.isEmpty()) {
			num = base;
		}
		else {
			num = getLast(1);	// type=1 for staff
		}
		
		String newStaffID = "F" + num;
		if (!staffIDList.contains(newStaffID)){
			staffIDList.add(newStaffID);
			System.out.println("jlkasfjd" + staffIDList.size());
			return newStaffID;
			
		}
		else
			return "Error generating staffID";
	}
	
	private int getLast(int type) {
		System.out.println("enter 2" );
		int num = -100;
		if (type == 0) {
			int lastIndex = matricNoList.size()-1;
			num = Integer.parseInt(matricNoList.get(lastIndex).substring(1, lastIndex +1));	
		}
		
		else if (type ==1) {
			System.out.println("enter" );
			int lastIndex = staffIDList.size()-1;
			System.out.println("last index " + lastIndex );
			String ID = staffIDList.get(lastIndex);
			num = Integer.parseInt(ID.substring(1, ID.length()));	
		}
		return num + 1;
	}
	
	public Faculty getFacultyOfStudent(String matricNo) {
		Student currentStudent;
		for (Faculty faculty: facultyList) {
			currentStudent = faculty.getStudent(matricNo);
			if ( currentStudent != null)
				return faculty;
		}
		return null;
	}
	
	public boolean isValidMatricNo(String matricNo) {
		if (matricNoList.contains(matricNo))
			return true;
		
		else 
			return false;
	}
	


	public Semester getCurrentSemester() {
		if (semesterList.size() != 0)
			return semesterList.get(semesterList.size()-1);
		else 
			return null;
	}

	
	
}
