import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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
		if (!facultyExists(facultyName))
			facultyList.add(newFaculty);
		else
			System.out.println("Faculty already exists.");
	}
	

	public ArrayList<String> getFacultyNameList() {
		ArrayList<String> facultyNameList = new ArrayList<>();
		
		for (Faculty faculty: facultyList) 
			facultyNameList.add(faculty.getFacultyName());
		return facultyNameList;
	}
	
	
	public void addStaffToFaculty(String facultyName, String staffName) {
		Faculty faculty = getFacultyByName(facultyName);
		faculty.addStaff(staffName, generateStaffID());
	}
	
	
	public Student addStudentToFaculty(String facultyName, String studentName, Semester semester) {
		Faculty faculty = getFacultyByName(facultyName);
		Student newStudent = faculty.addStudent(studentName, generateMatricNo(), semester);
		return newStudent;
	}
	
	
	public void addCourseToFaculty(String facultyName, String courseCode, String courseName,
			String coordinator, LessonType lessonType, ArrayList<Component> assessment, Semester semester) {
		
		Faculty faculty = getFacultyByName(facultyName);
		faculty.addCourse(courseCode, courseName, coordinator, lessonType, assessment, semester);
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
		
		if (!matricNoList.contains(newMatricNo)) {
			matricNoList.add(newMatricNo);
			return newMatricNo;			
		}

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
			return newStaffID;
			
		}
		else
			return "Error generating staffID";
	}
	
	
	private int getLast(int type) {
		int num = -100;
		int lastIndex = 0;
		String ID = "";
		if (type == 0) {
			lastIndex = matricNoList.size()-1;
			ID = matricNoList.get(lastIndex);	
		} 
		else if (type ==1) {
			lastIndex = staffIDList.size()-1;
			ID = staffIDList.get(lastIndex);			
		}

		num = Integer.parseInt(ID.substring(1, ID.length()));	//cannot limit 
		
		return num;
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


	public ArrayList<String> getCourseListByFaculty(String facultyName, Semester sem) {
		Faculty faculty = getFacultyByName(facultyName);
		return faculty.getCourseNameList(sem);
	}
	
	
	public void printStudentListByGroup(String facultyName, Semester semester, String courseCode, char type) {
		Faculty faculty = getFacultyByName(facultyName);
		faculty.printStudentListByGroup(semester, courseCode, type);
	}


	public void printCourseStats(String facultyName, Semester sem, String courseCode) {
		Faculty faculty = getFacultyByName(facultyName);
		faculty.printCourseStats(sem, courseCode);
		
	}


	public ArrayList<Component> getCourseAssessment(String facultyName, Semester sem, String courseCode) {
		Faculty faculty = getFacultyByName(facultyName);
		return faculty.getCourseAssessment(sem, courseCode);
		
	}


	public void updateCourseAssessment(String facultyName, Semester sem, String courseCode, ArrayList<Component> assessment) {
		Faculty faculty = getFacultyByName(facultyName);
		faculty.updateCourseAssessment(sem, courseCode, assessment);
		
	}


	public ArrayList<String> getComponentTitles(String facultyName, Semester sem, String courseCode) {
		Faculty faculty = getFacultyByName(facultyName);
		return faculty.getComponentTitles(sem, courseCode);
	}


	public void updateMarks(String facultyName, Semester sem, String courseCode, String matricNo,
			HashMap<String, Double> updatedMarks) {
		Faculty faculty = getFacultyByName(facultyName);
		faculty.updateMarks(sem, courseCode, matricNo, updatedMarks);
		
	}


	public ArrayList<String> getMatricNoList(String facultyName, Semester sem, String courseCode) {
		Faculty faculty = getFacultyByName(facultyName);
		return faculty.getMatricNoList(sem, courseCode);
	}
	
	
	private boolean facultyExists(String facultyName) {
		for (Faculty faculty: facultyList) {
			if (faculty.getFacultyName().equalsIgnoreCase(facultyName))
				return true;
		}
		return false;
	}


	public ArrayList<String> getAvailableStaff(String facultyName) { //return the list of available staff to be coordinator
		Faculty currentFaculty = getFacultyByName(facultyName);
		ArrayList<String> result = new ArrayList<>();
		for(FacultyStaff currentStaff : currentFaculty.getStaffList()) {
			if (currentStaff.getCoordinatorOf() == null) {
				result.add(currentStaff.getStaffName());
			}
		}
		return result;
	}
	
	
}
