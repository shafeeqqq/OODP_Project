import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class University {

	private String name;
	/**
	 * This arraylist stores all faculty objects
	 */
	private ArrayList<Faculty> facultyList = new ArrayList<>();
	/**
	 * This arrayList stores all the semesters
	 */
	private ArrayList<Semester> semesterList = new ArrayList<>();
	/**
	 * This arraylist stores all the matric numbers of all students as strings
	 */
	private ArrayList<String> matricNoList = new ArrayList<>();
	/**
	 * This arraylist stores all staffID of all staff in the university
	 */
	private ArrayList<String> staffIDList = new ArrayList<>();
	
	/**
	 * This constructs the university object, initializing it with a name
	 * @param universityName
	 */
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

	/**
	 * This method calls the faculty constructor and create a faculty object then put it in the list
	 * If the facultyname is already taken then print error message
	 * @param facultyName
	 */
	public void addFaculty(String facultyName) {
		Faculty newFaculty = new Faculty(facultyName, getCurrentSemester());
		if (!facultyExists(facultyName))
			facultyList.add(newFaculty);
		else
			System.out.println("Faculty already exists.");
	}
	
	/**
	 * This method returns all the faculty names as an arraylist of strings
	 * @return faculty name list
	 */
	public ArrayList<String> getFacultyNameList() {
		ArrayList<String> facultyNameList = new ArrayList<>();
		
		for (Faculty faculty: facultyList) 
			facultyNameList.add(faculty.getFacultyName());
		return facultyNameList;
	}
	
	/**
	 * This method calls the faculty add staff method and pass the parameters to that method
	 * This method is used during runtime
	 * @param facultyName
	 * @param staffName
	 */
	public void addStaffToFaculty(String facultyName, String staffName) {
		Faculty faculty = getFacultyByName(facultyName);
		faculty.addStaff(staffName, generateStaffID(), getCurrentSemester());
	}
	
	/**
	 * This method calls the faculty add staff method and pass the parameters to that method
	 * This method is used during loading of pre-existing staffs.
	 * @param facultyName
	 * @param staffName
	 * @param staffID
	 * @param coordinatorOf
	 * @param workLoadBySemester
	 */
	public void addStaffToFaculty(String facultyName, String staffName, String staffID, String coordinatorOf,
			HashMap<Semester, ArrayList<String>> workLoadBySemester) {
		Faculty faculty = getFacultyByName(facultyName);
		staffIDList.add(staffID);
		faculty.addStaff(staffName, staffID, coordinatorOf, workLoadBySemester);
	}
	/**
	 * This method calls the faculty add student method and pass the parameters to that method
	 * This method is used during run-time
	 * @param facultyName
	 * @param studentName
	 * @param semester
	 */
	public void addStudentToFaculty(String facultyName, String studentName, Semester semester) {
		Faculty faculty = getFacultyByName(facultyName);
		faculty.addStudent(studentName, generateMatricNo(), semester);
		printArray(getAllStudentNameList());
		System.out.println("");
	}
	
	/**
	 * This method calls the faculty add student method and pass the parameters to that method
	 * This method is used during loading of pre-existing students
	 * @param facultyName
	 * @param studentName
	 * @param matricNo
	 * @param candidature
	 */
	public void addStudentToFaculty(String facultyName, String studentName, String matricNo, HashMap<Semester, ArrayList<String>> candidature) {
		Faculty faculty = getFacultyByName(facultyName);
		matricNoList.add(matricNo);
		faculty.addStudent(studentName, matricNo, candidature);
		
	}
	
	/**
	 * This method calls the faculty add course method and pass the parameters to that method
	 * It prints all courses after
	 * @param facultyName
	 * @param courseCode
	 * @param courseName
	 * @param coordinator
	 * @param lessonType
	 * @param assessment
	 * @param semester
	 */
	public void addCourseToFaculty(String facultyName, String courseCode, String courseName,
			String coordinator, LessonType lessonType, ArrayList<Component> assessment, Semester semester) {
		
		Faculty faculty = getFacultyByName(facultyName);
		faculty.addCourse(courseCode, courseName, coordinator, lessonType, assessment, semester);
		printArray(getAllCourseNameList());
		System.out.println("");
	}
	
	/**
	 * This method return a faculty object that has the parameter as its name
	 * Returns null if it does not exist
	 * @param facultyName
	 * @return faculty object
	 */
	public Faculty getFacultyByName(String facultyName) {
		for (Faculty faculty: facultyList) {
			if (faculty.getFacultyName().equals(facultyName))
				return faculty;
		}
			return null;
	}
	
	/**
	 * This method generates a new matric number every time a student is added to the university
	 * @return a new matric number
	 */
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
	
	/**
	 * This method generates a new staffID every time a student is added to the university
	 * @return a new staffID
	 */
	public String generateStaffID() {
		int base = 100001;
		int num;
		
		if (staffIDList.isEmpty()) {
			num = base;
		}
		else {
			num = getLast(1) + 1;	// type=1 for staff
		}
		
		String newStaffID = "F" + num;
		if (!staffIDList.contains(newStaffID)){
			staffIDList.add(newStaffID);
			return newStaffID;
			
		}
		else
			return "Error generating staffID";
	}
	
	/**
	 * This method returns the largest digit in the matric/staffID list so the next ID can be generated
	 * @param type
	 * @return largest ID added
	 */
	private int getLast(int type) {
		int num = -100;
		int lastIndex = 0;
		String ID = "";
		if (type == 0) {
			ID = matricNoList.get(0);
			for (String item: matricNoList) {
				if (item.compareTo(ID) > 0)
					ID = item;
			}
		} 
		else if (type ==1) {
			ID = staffIDList.get(0);
			for (String item: staffIDList) {
				if (item.compareTo(ID) > 0)
					ID = item;
			}		
		}

		num = Integer.parseInt(ID.substring(1, ID.length()));	//cannot limit 
		
		return num;
	}
	
	/**
	 * This method uses the matricNo to find the faculty of the student
	 * @param matricNo
	 * @return faculty object that the student is under
	 */
	public Faculty getFacultyOfStudent(String matricNo) {
		Student currentStudent;
		for (Faculty faculty: facultyList) {
			currentStudent = faculty.getStudentObj(matricNo);
			if ( currentStudent != null)
				return faculty;
		}
		return null;
	}
	
	/**
	 * This method checks whether the passed matric number is a correct one
	 * @param matricNo
	 * @return true if correct, false if wrong
	 */
	public boolean isValidMatricNo(String matricNo) {
		if (matricNoList.contains(matricNo)) 
			return true;	
		else 
			return false;
	}
	
	/**
	 * This method get the current semester object
	 * @return current semester object
	 */
	public Semester getCurrentSemester() {
		if (semesterList.size() != 0)
			return semesterList.get(semesterList.size()-1);
		else 
			return null;
	}

	/**
	 * This method calls the get coursename list from faculty 
	 * @param facultyName
	 * @param sem
	 * @return an array list of strings detailing course code inside the faculty name that is passed
	 */
	public ArrayList<String> getCourseListByFaculty(String facultyName, Semester sem) {
		Faculty faculty = getFacultyByName(facultyName);
		return faculty.getCourseNameList(sem, false);
	}
	
	/**
	 * This method calls the faculty print student list by group and passed to it the parameters
	 * @param facultyName
	 * @param semester
	 * @param courseCode
	 * @param type
	 */
	public void printStudentListByGroup(String facultyName, Semester semester, String courseCode, char type) {
		Faculty faculty = getFacultyByName(facultyName);
		faculty.printStudentListByGroup(semester, courseCode, type);
	}
	
	/**
	 * This method gets the course vacancy by calling it from the faculty object
	 * @param facultyName
	 * @param sem
	 * @param courseCode
	 * @return course vacancy as a integer
	 */
	public int getCourseVacancy(String facultyName, Semester sem, String courseCode) {
		Faculty faculty = getFacultyByName(facultyName);
		return faculty.getCourseVacancy(sem, courseCode);
	}
	
	/**
	 * This method gets the course vacancy by calling it from the faculty object
	 * @param facultyName
	 * @param sem
	 * @param courseCode
	 * @return course vacancy as a string
	 */
	public String getCourseVacancyMsg(String facultyName, Semester sem, String courseCode) {
		Faculty faculty = getFacultyByName(facultyName);
		return faculty.getCourseVacancyMsg(sem, courseCode);
	}

	/**
	 * This method calls the faculty princt course stat method
	 * @param facultyName
	 * @param sem
	 * @param courseCode
	 */
	public void printCourseStats(String facultyName, Semester sem, String courseCode) {
		Faculty faculty = getFacultyByName(facultyName);
		faculty.printCourseStats(sem, courseCode);
		
	}

	/**
	 * This method get the courseAssessment as an ArrayList of Component objects
	 * @param facultyName
	 * @param sem
	 * @param courseCode
	 * @return CourseAssessment List
	 */
	public ArrayList<Component> getCourseAssessment(String facultyName, Semester sem, String courseCode) {
		Faculty faculty = getFacultyByName(facultyName);
		return faculty.getCourseAssessment(sem, courseCode);
	}

	/**
	 * This method calls the update course assessment method from faculty and passed to it the parameters
	 * @param facultyName
	 * @param sem
	 * @param courseCode
	 * @param assessment
	 */
	public void updateCourseAssessment(String facultyName, Semester sem, String courseCode, ArrayList<Component> assessment) {
		Faculty faculty = getFacultyByName(facultyName);
		faculty.updateCourseAssessment(sem, courseCode, assessment);
		
	}

	/**
	 * This method calls the getComponentTitltes from faculty
	 * @param facultyName
	 * @param sem
	 * @param courseCode
	 * @return Components titles as an arrayList of strings
	 */
	public ArrayList<String> getComponentTitles(String facultyName, Semester sem, String courseCode) {
		Faculty faculty = getFacultyByName(facultyName);
		return faculty.getComponentTitles(sem, courseCode);
	}

	/**
	 * This method calls the updateMarks method from faculty and pass to it the parameters
	 * @param facultyName
	 * @param sem
	 * @param courseCode
	 * @param matricNo
	 * @param updatedMarks
	 */
	public void updateMarks(String facultyName, Semester sem, String courseCode, String matricNo,
			HashMap<String, Double> updatedMarks) {
		Faculty faculty = getFacultyByName(facultyName);
		faculty.updateMarks(sem, courseCode, matricNo, updatedMarks);
		
	}

	/**
	 * This method calls the getMatricNoList method from faculty and pass it it the parameters
	 * @param facultyName
	 * @param sem
	 * @param courseCode
	 * @return matricNoList as an arraylist of strings
	 */
	public ArrayList<String> getMatricNoList(String facultyName, Semester sem, String courseCode) {
		Faculty faculty = getFacultyByName(facultyName);
		return faculty.getMatricNoList(sem, courseCode);
	}
	
	/**
	 * This method checks whether a faculty exists in the system or not
	 * @param facultyName
	 * @return true if correct, false if wrong
	 */
	private boolean facultyExists(String facultyName) {
		for (Faculty faculty: facultyList) {
			if (faculty.getFacultyName().equalsIgnoreCase(facultyName))
				return true;
		}
		return false;
	}

	/**
	 * This method callst the faculty getAvailable staffs
	 * @param facultyName
	 * @return an arrayList of string of available staffs to become coordinator 
	 */
	public ArrayList<String> getAvailableStaff(String facultyName) { //return the list of available staff to be coordinator
		Faculty currentFaculty = getFacultyByName(facultyName);
		ArrayList<String> result = new ArrayList<>();
		result = currentFaculty.getAvailableStaff();
		return result;
	}
	
	/**
	 * This method get the semester object when creating a new course so that no duplicate semester objects are
	 * created
	 * @param sem
	 * @return
	 */
	public Semester getSemester(Semester sem) {
		for (Semester item: semesterList) {
			if (item.equals(sem))
				return item;
		}
		semesterList.add(sem);
		return sem;
	}

	/**
	 * This method calls the addcourse method from faculty and passes all the parameters to it
	 * @param facultyName
	 * @param courseCode
	 * @param courseName
	 * @param coordinator
	 * @param lessonType
	 * @param assessment
	 * @param sem
	 * @param studentInfoList
	 */
	public void addCourseToFaculty(String facultyName, String courseCode, String courseName, String coordinator,
			LessonType lessonType, ArrayList<Component> assessment, Semester sem,
			ArrayList<StudentInfo> studentInfoList) {
		Faculty faculty = getFacultyByName(facultyName);
		faculty.addCourse(courseCode, courseName, coordinator, lessonType, assessment, sem, studentInfoList);		
	}

	/**
	 * This method return all the students objects
	 * @return ArrayList of all student objects
	 */
	public ArrayList<Student> getAllStudents() {
		ArrayList<Student> result = new ArrayList<>();
		for (Faculty faculty: facultyList)
			result.addAll(faculty.getAllStudents());
		return result;
	}

	/**
	 * This method return all the facultystaff objects
	 * @return ArrayList of all facultystaff objects
	 */
	public ArrayList<FacultyStaff> getAllStaffs() {
		ArrayList<FacultyStaff> result = new ArrayList<>();		
		for (Faculty faculty: facultyList) {
			result.addAll(faculty.getAllStaffs());
		}
		return result;
	}

	/**
	 * This method return all the faculty objects
	 * @return ArrayList of all faculty objects
	 */
	public ArrayList<Faculty> getAllFaculty() {
		ArrayList<Faculty> result = new ArrayList<>();		
		for (Faculty faculty: facultyList) {
			result.add(faculty);
		}
		return result;	
	}

	/**
	 * This method construct a string to be printed as transcript
	 * @param candidature
	 * @param studentName
	 * @param matricNo
	 * @param facultyName
	 * @return transcript as a string
	 */
	public String getTranscript(HashMap<Semester, ArrayList<String>> candidature, String studentName, String matricNo, String facultyName) {
		String result = 
				"Name: " + studentName + "\n"
				+ "Matriculation No: "+ matricNo + "\n"
				+ "Faculty: "+ facultyName + "\n";
	
		
		for (Semester semester: candidature.keySet()) {
			result += semester.toString()+ "\n";
			for (String courseCode : candidature.get(semester)) {
				result += courseCode + ": \n" + getTranscriptMsg(semester, courseCode, matricNo) + "\n"; 
			} 
		}
		return result;
	}

	/**
	 * This method return all the semestral data of the course
	 * @param sem
	 * @param courseCode
	 * @param matricNo
	 * @return a string of overall and components marks
	 */
	private String getTranscriptMsg(Semester sem, String courseCode, String matricNo) {
		for (Faculty faculty: facultyList) {
			if (faculty.containsCourse(sem, courseCode)) {
				return faculty.getTranscriptMsg(sem, courseCode, matricNo);
			}
		}
		return "Error getting marks";
	}
	/**
	 * This method get all the student name list
	 * @return an Arraylist of string of student names
	 */
	public ArrayList<String> getAllStudentNameList() {
		ArrayList<String>  result = new ArrayList<>();
		for (Faculty faculty: facultyList) {
			result.addAll(faculty.getAllStudentNameList());
		}
		return result;
	}
	/**
	 * This method prints the array of strings out
	 * @param list
	 */
	private void printArray(ArrayList<String> list) {
		for (int i=0; i<list.size(); i++) 
			System.out.println(i+1 + ". " + list.get(i));
	}
	/**
	 * This method get all the course name list
	 * @return an arraylist of string of coursecode
	 */
	public ArrayList<String> getAllCourseNameList() {
		ArrayList<String>  result = new ArrayList<>();
		for (Faculty faculty: facultyList) {
			result.addAll(faculty.getAllCourseNameList(getCurrentSemester()));
		}
		return result;
	}
	


}