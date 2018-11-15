import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	
	/**
	 * Name of the student
	 */
	private String studentName;
	/**
	 * Matric number of the student
	 */
	private String matricNo;
	/**
	 * Name of the faculty the student is part of
	 */
	private String facultyName;
	/**
	 * Dictionary storing the semester as the key and an array of the courses the student took in the semester
	 */
	private HashMap<Semester, ArrayList<String>> candidature = new HashMap<>();
	
	/**
	 * constructor for the student class
	 * @param name			name of the student to be instantiated
	 * @param matricNo		matric number of the student to be instantiated
	 * @param semester		Initialises the current semester so that courses can be added
	 * @param facultyName	name of the faculty the student is in
	 */
	public Student(String name, String matricNo, Semester semester, String facultyName ) {
		studentName = name;
		this.matricNo = matricNo;
		candidature.put(semester, new ArrayList<String>());
		this.facultyName = facultyName;
		printDetails();
	}
	
	/**
	 * alternate constructor to create the student object, taking in an existing hashmap of semesters and courses
	 * @param name			name of the student to be instantiated
	 * @param matricNo		matric number of the student
	 * @param facultyName	name of the faculty the student is part of
	 * @param candidature	hashmap of the various semesters the student is part of and the courses taken during the semester
	 */
	public Student(String name, String matricNo, String facultyName, HashMap<Semester, ArrayList<String>> candidature) {
		this.studentName = name;
		this.matricNo = matricNo;
		this.candidature.putAll(candidature);
		this.facultyName = facultyName;

	}
	
	/**
	 * get method to retrieve matric number of student
	 * @return	matric number of the student represented as a string
	 */
	public String getMatricNo() {
		return matricNo;
	}
	
	/**
	 * get method to retrieve name of student
	 * @return the first name of the student as a string
	 */
	public String getStudentName() {
		return studentName;	
	}
	
	/**
	 * get method to access the hashmap of the semester keys and string array of course codes
	 * @return the hashmap object
	 */
	public HashMap<Semester, ArrayList<String>> getCandidature() {
		return candidature;
	}
	
	/**
	 * method that prints the name, matriculation number , faculty and candidature hashmap as a string
	 */
	private void printDetails() {
		System.out.print(
				"Name: " + studentName + "\n"
			  + "Matriculation No.: " + matricNo + "\n"
			  + "Faculty: " + facultyName + "\n"
			  + "Candidature: \n" + getCandidatureString() + "\n");
	}
	
	/**
	 * method to return the candidate key-value pairs as a string
	 * @return String of all the semesters and courses taken in a string
	 */
	private String getCandidatureString() {
		String result = "";
		for (Semester item: candidature.keySet()) {
			result += item.toString() + ": \n";
			result += candidature.get(item).toString();
			result += "\n";
		}
		return result;
	}

	/**
	 * this method allows us to add a list of courses taken by the student to a particular sem
	 * @param sem			semester object of the semester that the course is taken in
	 * @param stringArr		string array where each string is a course code
	 */
	public void setCandidature(Semester sem, ArrayList<String> stringArr) {
		this.candidature.put(sem, stringArr);
	}
	
	/**
	 * this method allows us to add a single course taken by the student to the hashmap
	 * @param sem			semester object of the semester that the course is taken in
	 * @param courseCode	string of the course code to be added 
	 */
	public void addCourse(Semester sem, String courseCode) {
		candidature.get(sem).add(courseCode);
	}
	
	/**
	 * this method checks if the student is already registered for a particular course
	 * @param courseCode		course code of the course to be checked if it is added
	 * @param sem				semester of the course it is taken in
	 * @return					true if the course is already taken by the student, false otherwise
	 */
	public boolean alreadyRegistered(String courseCode, Semester sem) {
		if (candidature.get(sem).contains(courseCode))
			return true;
		return false;
	}
	
	/**
	 * method to retrieve the faculty name the student is in
	 * @return		string of the name of the faculty
	 */
	public String getFacultyName() {
		return facultyName;
	}
	
	
	/**
	 * this method returns a list of courses registered by the student in the semester
	 * @param sem	which semester to check for the courses registered by the student
	 * @return		array of string of the course codes
	 */
	public ArrayList<String> getRegisteredCoursesList(Semester sem) {
		ArrayList<String> result = new ArrayList<>();
		for (String courseCode: candidature.get(sem))
			result.add(courseCode);
		
		return result;
	}	
	
	/**
	 * method to unregister the student from a course
	 * @param sem			semester the course is taken in
	 * @param courseCode	course code of the course to be unregistered
	 * @return				returns true if the course is successfully unregistered, false otherwise
	 */
	public boolean unregisterCourse(Semester sem, String courseCode) {
		boolean deleted=false;
		for (String course: candidature.get(sem)) {
			if (courseCode.equalsIgnoreCase(course))
				deleted = candidature.get(sem).remove(courseCode);
		}		
		return deleted;
	}
}
