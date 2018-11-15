import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Faculty {	
	
	/**
	 * arraylist of all the students in the faculty
	 */
	private ArrayList<Student> studentList = new ArrayList<>();
	
	/**
	 * arraylist of all the staff id in the faculty
	 */
	private ArrayList<FacultyStaff> staffList = new ArrayList<>();
	
	/**
	 * name of the faculty
	 */
	private String facultyName;
	
	/**
	 * hashmap containing the semester as the key, and the list of courses as the key value
	 */
	private HashMap<Semester, ArrayList<Course>> courseListBySem = new HashMap<>();
	
	/**
	 * constructor for the faculty class
	 * @param facultyName	name of the faculty to be created
	 * @param semester		semester the faculty is initialised in
	 */
	public Faculty(String facultyName, Semester semester) {
		this.facultyName = facultyName;
		courseListBySem.put(semester, new ArrayList<Course>());
	}
	
	/**
	 * method to add a semester to a faculty so courses can be added to it
	 * @param semester	semester to be added in
	 */
	public void updateCourseListBySem(Semester semester) {
		if (!courseListBySem.containsKey(semester))
			courseListBySem.put(semester, new ArrayList<Course>());
	}
	
	/**
	 * get method to retrieve list of staff id
	 * @return arraylist of staff ids
	 */
	public ArrayList<String> getStaffNameList() {
		ArrayList<String> staffNameList  = new ArrayList<>();
		for (FacultyStaff staff : staffList) {
			staffNameList.add(staff.getStaffID() + "\t" + staff.getStaffName());
		}
		return staffNameList;
	}
	
	
	
	/**
	 * this method adds a new student to the student list inside the Faculty object
	 * @param studentname	name of student to be added
	 * @param matricNo		matric no of student to be added
	 * @return 				student object that is added
	 */
	public Student addStudent(String studentname, String matricNo, Semester semester) {
		Student newStudent = new Student(studentname, matricNo, semester, this.facultyName);
		studentList.add(newStudent);
		return newStudent;
	}
	
	/**
	 * this method is for reading the student txt file and adding the student with existing grades
	 * @param name			name of the student
	 * @param matricNo		matric number of the student
	 * @param candidature	grades of all the components 
	 * @return				Student object created
	 */
	public Student addStudent(String name, String matricNo, HashMap<Semester, ArrayList<String>> candidature) {
		Student newStudent = new Student(name, matricNo, this.facultyName, candidature);
		studentList.add(newStudent);
		return newStudent;
	}
	
	
	/**
	 * this method adds a course to the faculty, starting with all the parameters as passed and
	 * the rest is instantiated to default values
	 * @param courseCode		course code to be added
	 * @param courseName		name of course
	 * @param coordinator		coordinator of course
	 * @param assessment		types of assessments in the course
	 * @param LessonType		lesson typeA/B/C
	 * @param sem				semester course is created in
	 */
	public void addCourse(String courseCode, String courseName, String coordinator,
			LessonType lessonType, ArrayList<Component> assessment, Semester sem) {
		for (FacultyStaff now : staffList) {
			if (now.getStaffID().equals(coordinator)) {
				now.setCoordinator(courseCode);
			}
		}
		Course newCourse = new Course(this.facultyName, courseCode, courseName, coordinator, lessonType, assessment);
		if (courseListBySem.get(sem) == null ) System.out.println("nulll null");
		if (!courseExists(sem, courseCode))
			courseListBySem.get(sem).add(newCourse);
		
		else {
			System.out.println("Course already exists.");
			return;
		}
		
		System.out.println("New Course successfully added.");
		System.out.println("~ Details of newly added course ~");
		newCourse.printDetails();
		System.out.println("~~~\n");

	}
	
	
	/**
	 * this method register the current student for the course of the code given
	 * @param courseCode	course code of course
	 */
	public void registerForCourse(Semester sem, String courseCode, String matricNo, String tutorialGroup) {
		Course course = getCourse(sem, courseCode);	
		
		if (course.getVacancy() > 0) {
			getStudentObj(matricNo).addCourse(sem, courseCode);
			course.addStudent(matricNo, tutorialGroup);
			printFeedback(course, tutorialGroup);
		} 
	}
	/**
	 * This method registers the student for a course from a diffrenct faculty from his own
	 * @param sem			semester the student registers the course for
	 * @param courseCode	code of course registerig for
	 * @param student		student object registering
	 * @param tutorialGroup	tutorial group student registering for
	 */
	public void registerForCourseDiffFaculty(Semester sem, String courseCode, Student student, String tutorialGroup) {
		Course course = getCourse(sem, courseCode);	
		
		if (course.getVacancy() > 0) {
			student.addCourse(sem, courseCode);
			course.addStudent(student.getMatricNo(), tutorialGroup);
			printFeedback(course, tutorialGroup);
		} 
	}
	
	/**
	 * This method print the description of the tutorial and lab groups of a course
	 * @param course			course object
	 * @param tutorialGroup		tutorial group
	 */
	private void printFeedback(Course course, String tutorialGroup) {
		System.out.println("Registration Successful!\n");
		System.out.println(course.getCourseCode() + "\t"+ course.getCourseName());
		
		if (course.getLessonType() == LessonType.TYPE_A)
			System.out.println("This course does not have tutorial/laboratory sessions\n" );
		else if (course.getLessonType() == LessonType.TYPE_B)
			System.out.println("Tutorial Group: " + tutorialGroup +"\nNo Laboratory sessions\n"  );
		else if (course.getLessonType() == LessonType.TYPE_C)
			System.out.println("Tutorial Group: " + tutorialGroup +"\nLab group is same as the tutorial group\n"  );
	}
	
	
	/**
	 * This method changes the name of the current faculty to the parameter passed
	 * @param facultyName	name of faculty
	 */
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	
	
	/**
	 * This method return the faculty name
	 * @return facultyName	name of faculty
	 */
	public String getFacultyName() {
		return facultyName;
	}
	
	/**
	 * This method adds the staff to the current faculty during run-time
	 * @param staffName		name of staff
	 * @param staffID		id of staff
	 * @param sem			semester to add to
	 */
	public void addStaff(String staffName, String staffID, Semester sem) {
		FacultyStaff newStaff = new FacultyStaff(staffName, staffID, sem, this.facultyName);
		staffList.add(newStaff);
	}
	/**
	 * This method adds the staff to the current faculty during loading from text file
	 * @param staffName				name of staff
	 * @param staffID				id of staff	
	 * @param coordinatorOf			course code the staff is the coordinator of
	 * @param workLoadBySemester	list of semester and courses part on in the semester
	 */
	public void addStaff(String staffName, String staffID, String coordinatorOf, HashMap<Semester, ArrayList<String>> workLoadBySemester) {
		FacultyStaff newStaff = new FacultyStaff(staffName, staffID,coordinatorOf, this.facultyName, workLoadBySemester);
		staffList.add(newStaff);
		
	}
	
	
	/**
	 * This method returns the specific course object by semester and course ID
	 * @param semester		semester object course is in	
	 * @param courseID		id of course
	 * @return				course object
	 */
	public Course getCourse(Semester sem, String courseCode) {
	
			for (Course course: courseListBySem.get(sem)) {
				if (course.getCourseCode().equalsIgnoreCase(courseCode))
					return course;
			}
		return null;
	}
	
	/**
	 * This method returns all course names as an array list of string
	 * @param sem			semester to check course list for
	 * @param vacancy		true of false if vacant
	 * @return all coursenames as an array list
	 */
	public ArrayList<String> getCourseNameList(Semester sem, boolean vacancy) {
		ArrayList<String> result = new ArrayList<>();
		for(Course course: courseListBySem.get(sem)) {
			if (!vacancy)
				result.add(course.getCourseCode() + "\t" + course.getCourseName());
			else
				result.add(course.getCourseCode() + "\t" + course.getCourseName() + " [" + course.getVacancy() + "]");
		}
		return result;
	}

	/**
	 * This method gets the student name by matricNo
	 * @param matricNo			matric no of the student
	 * @return studentName		name of the student
	 */
	public String getStudentNameByMatricNo(String matricNo) {
		String name = null;
		for (Student student: studentList) {
			if (student.getMatricNo().equals(matricNo))
				return student.getStudentName();
		}
		return name;
	}
	
	/**
	 * This method returns the student object by matricNo
	 * @param matricNo	matric no of the student
	 * @return			student object we accessing
	 */
	public Student getStudentObj(String matricNo) {
		for (Student student: studentList) {
			if (student.getMatricNo().equals(matricNo))
				return student;
		}
		return null;
	}
	
	/**
	 * this method prints the student list by tutorial group
	 * @param semester		semester we are checking for
	 * @param courseCode	course code we are checking for
	 * @param type			lecture of tutorial
	 */
	public void printStudentListByGroup(Semester semester, String courseCode, char type) {
		Course course = getCourse(semester, courseCode);
		int i=1;
		if (type == 'L') { // by lecture group 
			
			for (String matricNo: course.getAllStudentList()) {
				System.out.println( " " + i++ + ". " + matricNo + "\t" +getStudentNameByMatricNo(matricNo));
				
			}
			System.out.println(""); 
			
		}
		
		else if (type == 'T') {
			HashMap<String, ArrayList<String>> studentListByGroup = course.getStudentListByGroup();
		
			if (studentListByGroup.containsKey("N.A.")) {
				System.out.println("This course does not have any tutorial groups");
				return;
			}
			
			for (String tutGroup: studentListByGroup.keySet()) {
				System.out.println(tutGroup + ":");
				
				for (String matricNo: studentListByGroup.get(tutGroup)) 
					System.out.println( " " + i++ + ". " + matricNo + "\t" + getStudentNameByMatricNo(matricNo));
				
				System.out.println(""); 
				i = 1;
			}
		}
	}
	
	/**
	 * This method calls the printCourseStat method from course to print the course stats
	 * @param sem			semester course is in	
	 * @param courseCode	course code we checking for
	 */
	public void printCourseStats(Semester sem, String courseCode) {
		Course course = getCourse(sem, courseCode);
		course.printCourseStats();
		
	}

	/**
	 * This method returns courseAssessment as arraylist of components objects
	 * @param sem					semester object
	 * @param courseCode			course code
	 * @return courseAssessment 	arraylist of assessment of the course entered
	 */
	public ArrayList<Component> getCourseAssessment(Semester sem, String courseCode) {
		Course course = getCourse(sem, courseCode);
		return course.getAssessment();
	}

	/**
	 * This method let you change the courseAssessment by calling that method from course object
	 * @param sem			semester object
	 * @param courseCode	course code
	 * @param assessment	updated assessment components to add to course
	 */
	public void updateCourseAssessment(Semester sem, String courseCode, ArrayList<Component> assessment) {
		Course course = getCourse(sem, courseCode);
		course.setAssessment(assessment);
	}

	/**
	 * This method returns componentTitles as arraylist of strings
	 * @param sem					semester object
	 * @param courseCode			course code
	 * @return all component titles	names of all components
	 */
	public ArrayList<String> getComponentTitles(Semester sem, String courseCode) {
		Course course = getCourse(sem, courseCode);
		return course.getComponentTitles();
	}

	/**
	 * This method changes the marks by calling the method from course object and
	 * passing all parameters to it
	 * @param sem			semester object
	 * @param courseCode	coursecode
	 * @param matricNo		matric no of student grades we are updating
	 * @param updatedMarks	arraylist of updated marks
	 */
	public void updateMarks(Semester sem, String courseCode, String matricNo, HashMap<String, Double> updatedMarks) {
		Course course = getCourse(sem, courseCode);
		course.updateMarks(matricNo, updatedMarks);
	}

	/**
	 * This method calls the course get matricNo method
	 * @param sem			semester object
	 * @param courseCode	course code
	 * @return all matric numbers as arraylist that is registered to this course
	 */
	public ArrayList<String> getMatricNoList(Semester sem, String courseCode) {
		Course course = getCourse(sem, courseCode);
		return course.getMatricNoList();
	}
	
	/**
	 * This method checks whether a course exist within a semester or not
	 * @param sem
	 * @param courseCode
	 * @return true if correct, false if wrong
	 */
	private boolean courseExists(Semester sem, String courseCode) {
		if (courseListBySem.get(sem).isEmpty()) return false;
		for (Course course: courseListBySem.get(sem)) {
			if (course.getCourseCode().equalsIgnoreCase(courseCode))
				return true;
		}
		return false;
	}

	/**
	 * This method returns all available tut group for registering
	 * @param sem			semester object
	 * @param courseCode
	 * @return all available tutorial group as array list of string
	 */
	public ArrayList<String> getAvailTutGroups(Semester sem, String courseCode) {
		Course course = getCourse(sem, courseCode);
		return course.getAvailTutGroups();
	}

	/**
	 * This method calls the course vacancy method to calculate
	 * @param sem			semester object
	 * @param courseCode
	 * @return number of vacancies in course
	 */
	public int getCourseVacancy(Semester sem, String courseCode) {
		Course course = getCourse(sem, courseCode);
		return course.getVacancy();
	}
	
	/**
	 * This method calls the course vacancy method to print
	 * @param sem		semester object
	 * @param courseCode
	 * @return course vacancy as a string
	 */
	public String getCourseVacancyMsg(Semester sem, String courseCode) {
		Course course = getCourse(sem, courseCode);
		return course.getVacancyMsg();
	}
	
	/**
	 * This method returns all available staff to be coordinator
	 * @return all staff who are not already coordinator
	 */
	public ArrayList<String> getAvailableStaff() {
		ArrayList<String> result = new ArrayList<>();
		for (FacultyStaff currentStaff : staffList) {
			if (currentStaff.getCoordinatorOf() == null) {
				result.add(currentStaff.getStaffID() + "\t" + currentStaff.getStaffName());
			}
		}
		return result;
	}
	
	/**
	 * This method adds course to the current faculty
	 * @param courseCode			code of course to be added
	 * @param courseName			name of course
	 * @param coordinator			staff id of coordinator
	 * @param lessonType			A/B/C
	 * @param assessment			titles of components and weights
	 * @param sem					semester course is in
	 * @param studentInfoList		list of students
	 */
	public void addCourse(String courseCode, String courseName, String coordinator, LessonType lessonType,
			ArrayList<Component> assessment, Semester sem, ArrayList<StudentInfo> studentInfoList) {
		for (FacultyStaff now : staffList) {
			if (now.getStaffID().equals(coordinator)) {
				now.setCoordinator(courseCode);
			}
		}
		Course newCourse = new Course(this.facultyName, courseCode, courseName, coordinator, lessonType, assessment,studentInfoList);
		if (!courseExists(sem, courseCode))
			courseListBySem.get(sem).add(newCourse);
		
		else {
			System.out.println("Course already exists.");
			return;
		}
		System.out.println("New Course successfully added.");
		System.out.println("~ Details of newly added course ~");
		newCourse.printDetails();
		System.out.println("~~~\n");
		
	}
	/**
	 * method returns students in an array
	 * @return studentList as an arrayList
	 */
	public ArrayList<Student> getAllStudents() {
		return studentList;
	}
	
	/**
	 * method returns staff in facultu as an array
	 * @return staffList as arrayList
	 */
	public ArrayList<FacultyStaff> getAllStaffs() {
		return staffList;
	}
	
	/**
	 * This method returns all courses
	 * @return all courses as arraylist of course
	 */
	public ArrayList<Course> getAllCourses() {
		ArrayList<Course> result = new ArrayList<>();
		for(Semester sem : courseListBySem.keySet()) {
			result.addAll(courseListBySem.get(sem));
		}
		return result;
	}
	/**
	 * This method returns the entire courseList by sem as a hashmap
	 * @return map of arraylist of courses mapped to the semester
	 */
	public HashMap<Semester, ArrayList<Course>> getCourseListBySem() {
		return courseListBySem;
	}
	/**
	 * This method returns courseName by semester and courseCode
	 * @param sem
	 * @param courseCode
	 * @return courseName
	 */
	public String getCourseName(Semester sem, String courseCode) {
		String result = "Course not found";
		for (Course course: courseListBySem.get(sem)) {
			if (course.getCourseCode().equalsIgnoreCase(courseCode))
				return course.getCourseName();
		}
		return result; 
	}
	/**
	 * This method unregister a student from the course
	 * @param matricNo		matric no of student to unregister
	 * @param sem			sem of course student is unregistering
	 * @param courseCode	code of course
	 */
	public void unregisterCourse(String matricNo, Semester sem, String courseCode) {
		Course course = getCourse(sem, courseCode);
		boolean updatedCourse = course.unregisterStudent(matricNo);
		boolean updatedStudent = unregisterCourseStudent(matricNo, sem, courseCode);
		
		if (updatedCourse && updatedStudent) 
			System.out.println("Unregistered successfully!");
		else
			System.out.println("Error: Unsuccessful");
	}
	/**
	 * This method unregister students from a course by calling the method from students
	 * passing parameters to that method
	 * @param matricNo
	 * @param sem
	 * @param courseCode
	 * @return true if successful, false if failed
	 */
	private boolean unregisterCourseStudent(String matricNo, Semester sem, String courseCode) {
		Student student = getStudentObj(matricNo);
		return student.unregisterCourse(sem, courseCode);
	}
	/**
	 * This method checks whether a course exist within one sem
	 * @param sem
	 * @param courseCode
	 * @return true if correct, false if wrong
	 */
	public boolean containsCourse(Semester sem, String courseCode) {
		if (getCourse(sem, courseCode) == null)
			return false;
		return true;
	}
	/**
	 * This method returns the transcipt by calling the method from course  and
	 * passing matricNo to it
	 * @param sem
	 * @param courseCode
	 * @param matricNo
	 * @return
	 */
	public String getTranscriptMsg(Semester sem, String courseCode, String matricNo) {
		Course course = getCourse(sem, courseCode);
		return course.getTranscriptMsg(matricNo);
	}

	/**
	 * This method returns all student name as an arraylist of strings
	 * @return all student names
	 */
	public ArrayList<String> getAllStudentNameList() {
		ArrayList<String> result = new ArrayList<>();
		String msg = "";
		for (Student student: studentList) {
			msg = student.getMatricNo() + '\t' + student.getStudentName() + "\t\t" + student.getFacultyName();
			result.add(msg);
		}
		return result;
	}
	/**
	 * This method returns all courseName list by semester
	 * @param sem
	 * @return courseName in the semester
	 */
	public ArrayList<String> getAllCourseNameList(Semester sem) {
		ArrayList<String> result = new ArrayList<>();
		String msg = "";
		for (Course course: courseListBySem.get(sem)) {
			msg = course.getCourseCode() + '\t' + course.getCourseName() + "\t\t" + getStaffName(course.getCoordinator()) + " ("+ course.getCoordinator() +")";
			result.add(msg);
		}
		return result;
	}
	/**
	 * This method returns staffName from staffID
	 * @param staffID
	 * @return staffName
	 */
	private String getStaffName(String staffID) {
		for (FacultyStaff staff: staffList) {
			if (staff.getStaffID().equals(staffID))
				return staff.getStaffName();
		}
		
		return "Error retrieving staff name";
	}

	
}