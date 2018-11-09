import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AdminInterface {

	
	/*
	 * TODO:
	 * add student -- 
	 * add course -- 
	 * check vacancy in course
	 * print student list by tutorial group; lec - everyone
	 * enter course assessment weightage
	 * enter coursework marks
	 * print course stats
	 *
	 */
	
	
	Scanner sc = new Scanner(System.in);
	/*
	 * register for course
	 * print transcript
	 * 
	 */
	
	
	private University university;
	
	
	public AdminInterface(University university) {
		this.university = university;
	
	}

	public void run() {
		int run = 1;
		while (run == 1) {
			printMenu();
			int choice = getChoice();
			
			switch (choice) {
			case 1:
				addFaculty();
				break;
				
			case 2:
				addCourse();
				break;
				
			case 3:
				addFacultyStaff();
				break;
				
			case 4:
				addStudent();
				break;
				
			case 5:
				printCourseStats();
				break;
				
			case 6:
				setMarks();
				break;
				
			case 7:
				run = 2;
				System.out.println("You are logged out of the system!\n");
				break;
				
			case 8 :
				printAllStudents();
			
			default:
				System.out.println("Invalid input");
			}
		}
	}
	private void printAllStudents() {
		ArrayList<Faculty> allFaculty = university.getFacultyList();
		for (Faculty current : allFaculty) {
			ArrayList<Student> allStudent = current.getStudentList();
			for (Student currentStudent: allStudent) {
				currentStudent.printDetails();
			}
		}
	}

	private void printCourseStats() {
		// TODO
	}
	
	private void setMarks() {
		Semester sem = university.getCurrentSemester();
		ArrayList<String> facultyNameList = university.getFacultyNameList();
		
		System.out.println("Choose faculty:");
		printArray(facultyNameList);
		String facultyName = facultyNameList.get(sc.nextInt() - 1);	
		Faculty faculty = university.getFacultyByName(facultyName);
		
		ArrayList<String> courseNameList = faculty.getCourseNameList(sem);
		
		System.out.println("Choose Course:");
		printArray(courseNameList);
		String courseName = courseNameList.get(sc.nextInt() - 1);	
		Course course = faculty.getCourse(sem, processString(courseName));
		
		ArrayList<String> components = course.getComponentTitles();
		HashMap<String, Double> updatedMarks = new HashMap<>();
		
		String matricNo = getMatricNoInput(course.getMatricNoList());
		
		for (String item: components) {
			System.out.println("Enter marks for " + item + ": ");
			Double mark = sc.nextDouble();
			updatedMarks.put(item, mark);
		}
		
		course.updateMarks(matricNo, updatedMarks);
		
//		ArrayList<StudentInfo> studentInfoList = course.getStudentInfoList();
//		
//		StudentInfo student = getStudentInfo(studentInfoList); 
//		
//		HashMap<String, Double> currentMarks = student.getMarks();
//		for (String key: currentMarks.keySet()) {
//			System.out.println("Enter marks for " + key + ": ");
//			double mark = sc.nextDouble(); sc.nextLine();
//			currentMarks.put(key, mark);
//		}
//		
//		System.out.println(currentMarks.toString());
//		student.setMarks(currentMarks);
		
	}
	
	
	private String getMatricNoInput(ArrayList<String> list) {
		printMethod();
		String matricNo = null;
		System.out.println("Choose method:");
		int method = getChoice();
		
		switch (method) {
		case 1:
			System.out.println("Enter matricNo: ");
			matricNo = sc.nextLine();
			return matricNo;
			
		case 2:
			printArray(list);
			return list.get(getChoice()-1); //TODO perform arraylength check
		}
		return null;
	}
	

	private void printMethod() {
		System.out.print(
				  "### ADMIN MENU ###\n"
				+ " 1. Enter Matric No.\n"
				+ " 2. Choose from student list\n"
				+ "~~~~~~~~~~~~~~~~~~\n"); 
		
		
	}

	private void addFacultyStaff() {
	
		ArrayList<String> facultyNameList = university.getFacultyNameList();
		
		System.out.println("Enter Staff Name:");
		String staffName = sc.nextLine();
		
		System.out.println("Choose faculty:");
		printArray(facultyNameList);
		String facultyName = facultyNameList.get(sc.nextInt() - 1);
		
		university.addStaffToFaculty(facultyName, staffName);
		
		// success message
		System.out.println("New Faculty Staff " + staffName + " successfully added.");
		System.out.println("~~~ Faculty Staff List ~~~");
		printArray(university.getFacultyByName(facultyName).getStaffNameList());
		System.out.println("~~~~~~~~~~~~~\n");
		
		
	}

	private void addStudent() {
	
		ArrayList<String> facultyNameList = university.getFacultyNameList();
		
		System.out.println("Enter Student Name:");
		String studentName = sc.nextLine();
		
		System.out.println("Choose faculty:");
		printArray(facultyNameList);
		String facultyName = facultyNameList.get(sc.nextInt() - 1);	
	
		Student newStudent = university.addStudentToFaculty(facultyName, studentName, university.getCurrentSemester());
		
		System.out.println("New student successfully added.");
		System.out.println("--- Details of new student:---");
		newStudent.printDetails();
		System.out.println("-----------\n");
			
	}


	private void addCourse() {
		
		ArrayList<String> facultyNameList = university.getFacultyNameList();
		
		
		System.out.println("Choose faculty:");
		printArray(facultyNameList);
		String facultyName = facultyNameList.get(getChoice() - 1);	
		
		ArrayList<String> staffNameList = university.getFacultyByName(facultyName).getStaffNameList();
		
		System.out.println("Enter Course Code:");
		String courseCode = sc.nextLine();
		
		System.out.println("Enter Course Name:");
		String courseName = sc.nextLine();
		
		System.out.println("Choose Course Coordinator:");
		printArray(staffNameList);
		String staffID = getStaffID(staffNameList.get(getChoice() - 1));	// coordinator
		
		System.out.println("Choose Lesson Type:");
		LessonType.printLessonTypes();
		LessonType lessonType = getLessonType();
		
		ArrayList<Component> assessment = getAssessmentInput();
		
		Course newCourse = university.addCourseToFaculty(facultyName, courseCode, courseName, 
				staffID, lessonType, assessment, university.getCurrentSemester());
		
		System.out.println("New Course successfully added.");
		System.out.println("--- Details of newly added course:---");
		newCourse.printDetails();
		System.out.println("-----------\n");
		
		
	}

	private String getStaffID(String string) {
		int index = string.indexOf('\t');
		return string.substring(0, index);
		
	}
	

	private LessonType getLessonType() {
		System.out.println("Enter Lesson Type (e.g. 'A'): ");
		char type = sc.nextLine().charAt(0);
		
		switch(type) {
		case 'a':
		case 'A':
			return LessonType.TYPE_A;
		
		case 'b':
		case 'B':
			return LessonType.TYPE_B;
			
		case 'c':
		case 'C':
			return LessonType.TYPE_C;
			
		default:
			return LessonType.TYPE_A;
			
		}
	}

	private void addFaculty() {
		System.out.println("Enter new faculty name: ");
		String facultyName = sc.nextLine();

		university.addFaculty(facultyName);
		System.out.println("New Faculty " + facultyName + " successfully added.");
	
		System.out.println("--- Faculty List ---");
		printArray(university.getFacultyNameList());
		System.out.println("-----------\n");
	}



	private void printArray(ArrayList<String> list) {
		for (int i=0; i<list.size(); i++) 
			System.out.println(i+1 + ". " + list.get(i));
		
	}

	private void printMenu() {
		System.out.print(
				  "### ADMIN MENU ###\n"
				+ " 1. Add Faculty\n"
				+ " 2. Add Course\n"
				+ " 3. Add Faculty Staff\n"
				+ " 4. Add Student\n"
				+ " 5. Print Course Statistics\n"
				+ " 6. Set Marks\n"
				+ " 7. Quit\n"
				+ "~~~~~~~~~~~~~~~~~~\n"); 
		
	}
	
	private int getChoice() {
		int choice = sc.nextInt();
		sc.nextLine();
		return choice;
	}
	
	private ArrayList<Component> getAssessmentInput() {
		ArrayList<Component> assessment = new ArrayList<>();
		int more = 1;
		System.out.println("Enter Course Assessment: ");
		
		while (!addsUp(assessment)) {
			while (more == 1) {
				System.out.println("Enter Component name: ");
				String title = sc.nextLine();
	
				System.out.println("Enter Component weightage (%) for " + title + ": ");
				int weightage = getChoice();
				
				assessment.add(new Component(title, weightage));
				
				if (addsUp(assessment))
					more = 0;
			}
			if (!addsUp(assessment)) {
				System.out.println("Component weightage does not add up to 100%. Please re-enter Course Assessment");
				more = 1;
				assessment.clear();
			}
			else 
				break;
		}
		return assessment;
	}

	private boolean addsUp(ArrayList<Component> assessment) {
		if (assessment.isEmpty())
			return false;
		else {
			int total = 0;
			for (Component item: assessment) {
				total += item.getWeightage();
			}
			if (total == 100)
				return true;
			else 
				return false;
		}
	}
	
	private String processString(String string) {
		
		int index = string.indexOf('\t');
		return string.substring(0, index);
		
	}
	
}
