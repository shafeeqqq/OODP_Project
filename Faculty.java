import java.util.ArrayList;
import java.util.HashMap;

public class Faculty {	//need interface w university to add student and course
	private ArrayList<Student> studentList = new ArrayList<>();
	private ArrayList<FacultyStaff> staffList = new ArrayList<>();
	private String facultyName;
	private HashMap<Semester, ArrayList<Course>> courseListBySem = new HashMap<>();
	

	public Faculty(String facultyName, Semester semester) { /*ArrayList<Student> studentList*/
		this.facultyName = facultyName;
		courseListBySem.put(semester, new ArrayList<Course>());
	}
	
	
	public void updateCourseListBySem(Semester semester) {
		if (!courseListBySem.containsKey(semester))
			courseListBySem.put(semester, new ArrayList<Course>());
	}
	
	public ArrayList<String> getStaffNameList() {
		ArrayList<String> staffNameList  = new ArrayList<>();
		for (FacultyStaff staff : staffList) {
			staffNameList.add(staff.getStaffID() + "\t" + staff.getStaffName());
		}
		return staffNameList;
	}
	
	
	/**
	 * this method adds a new student to the student list inside the Faculty object
	 * @param studentname
	 * @param matricNo
	 * @return 
	 */
	public Student addStudent(String studentname, String matricNo, Semester semester) {
		Student newStudent = new Student(studentname, matricNo, semester);
		studentList.add(newStudent);
		return newStudent;
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
	public Course addCourse(String courseCode, String courseName, String coordinator,
			LessonType lessonType, ArrayList<Component> assessment, Semester sem) {
		
		Course newCourse = new Course(courseCode, courseName, coordinator, lessonType, assessment);
		courseListBySem.get(sem).add(newCourse);
		return newCourse;
	}
	
	
	/**
	 * this method register the current student for the course of the code given
	 * @param courseCode
	 */
	public void registerForCourse(String courseCode, Semester currentSem, Student student) {
		System.out.println("processing...");
		
		Course course = getCourse(currentSem, courseCode);
		
		
		if (course.getVacancy() > 0) {
			student.addCourse(currentSem, courseCode);

			System.out.println(course.getAssessmentString());
			course.getStudentInfoList().add(new StudentInfo(student.getMatricNo(), course.getTutorialGroup(), course.getAssessmentTitles()));
			
			System.out.println("Registration Successful");
			student.printDetails();
			course.printStudentInfoOfStudent(student.getMatricNo());

		
		} else 
			System.out.println("There is no more vacancy in the course.");
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
	
	
	/**
	 * This method adds a staff object to the list of staff object by intializing one with the given parameters
	 * @param staffName
	 * @param staffID
	 */
	public void addStaff(String staffName, String staffID) {
		FacultyStaff newStaff = new FacultyStaff(staffName, staffID);
		staffList.add(newStaff);
	}
	
	
	/**
	 * This method returns the specific course object by semester and course ID
	 * @param semester
	 * @param courseID
	 * @return
	 */
	public Course getCourse(Semester semester, String courseCode) {
		ArrayList<Course> copy =  courseListBySem.get(semester);
		int index=0;
		for (int i =0; i<copy.size(); i++) {
			if (copy.get(i).getCourseCode() == courseCode) {
				index = i;
				break;
			}
		}
		return courseListBySem.get(semester).get(index);
	}
	
	
	public Student getStudent(String matricNo) {
		for (Student student: studentList) {
			if (student.getMatricNo().equals(matricNo)) {
				return student;
			}
		}
		return null;
	}
	
	
	public String getTranscript(Student student) {
		String temp = "Student Name: " + student.getStudentName() + 
				"\nMatriculation Number: "+ student.getMatricNo() + "\n";
		
		HashMap<Semester, ArrayList<String>> candidature = student.getCandidature();
		
		for (Semester semester: candidature.keySet()) {
			temp += semester.toString()+ "\n";
			for (String courseCode : candidature.get(semester)) 
				temp += courseCode + ": " + getGradeString(semester, courseCode, student.getMatricNo());

		}

		return temp;
		//print course, grade, weightage and mark for each component
	}	
	
	private String getGradeString(Semester semester, String courseCode, String matricNo) {
		Course course = getCourse(semester, courseCode);
		StudentInfo studentInfo = course.getStudentInfoOfStudent(matricNo);
		return studentInfo.getMarksString();
	}


	public ArrayList<String> getCourseNameList(Semester sem) {
		ArrayList<String> result = new ArrayList<>();
		for(Course course: courseListBySem.get(sem)) {
			result.add(course.getCourseCode() + "\t" + course.getCourseName());
		}
		return result;
	}
	
	private void printArray(ArrayList<String> list) {
		for (int i=0; i<list.size(); i++) 
			System.out.println(i+1 + ". " + list.get(i));
		
	}
	
	public String getStudentNameByMatricNo(String matricNo) {
		String name = null;
		for (Student student: studentList) {
			if (student.getMatricNo().equals(matricNo))
				return student.getStudentName();
		}
		return name;
	}
	
	
	public void printStudentListByGroup(Semester semester, String courseCode, char type) {
		Course course = getCourse(semester, courseCode);
		int i=1;
		if (type == 'L') { // by lecture group 
			
			for (String matricNo: course.getAllStudentList()) {
				System.out.println( " " + i++ + ". " + matricNo + "\t" +getStudentNameByMatricNo(matricNo));
			}
			
		}
		
		else if (type == 'T') {
			HashMap<String, ArrayList<String>> studentListByGroup = course.getStudentListByGroup();
			if (studentListByGroup.containsKey("N.A.")) {
				System.out.println("This course does not have any tutorial groups");
				return;
			}
			
			for (String tutGroup: studentListByGroup.keySet()) {
				System.out.println(tutGroup + ":");
				
				for (String matricNo: studentListByGroup.get(tutGroup)) {
					System.out.println( " " + i + ". " + matricNo + getStudentNameByMatricNo(matricNo));
				}
				
			}
		
		}
	}
	
	
	public void displayCourseList(Semester sem) {
		System.out.println("Course List: ");
		
	}


	public void printCourseStats(Semester sem, String courseCode) {
		Course course = getCourse(sem, courseCode);
		course.printCourseStats();
		
	}
	
}
