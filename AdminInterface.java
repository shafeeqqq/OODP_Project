import java.util.ArrayList;
import java.util.Scanner;

public class AdminInterface {

	
	/*
	 * TODO:
	 * add student
	 * add course
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
		
		// dummy data -- faculty
		university.addFaculty("SCSE");
		university.addFaculty("NBS");
		
		// dummy data -- faculty staff
		university.addStaffToFaculty("SCSE", "Alice");
		university.addStaffToFaculty("NBS", "Bob");
		
		// dummy data -- student
		university.addStudentToFaculty("SCSE", "Cat", university.getCurrentSemester());
		university.addStudentToFaculty("NBS", "Darwin", university.getCurrentSemester());
		
		// dummy data -- course
		 university.addCourseToFaculty("SCSE", "CS100", "algorithms", 
					"F100001 ", LessonType.TYPE_A , null , university.getCurrentSemester());
		 university.addCourseToFaculty("NBS", "B100", "business", 
					"F100002 ", LessonType.TYPE_B , null , university.getCurrentSemester());
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
				run = 2;
				System.out.println("You are logged out of the system!\n");
				break;
			
			default:
				System.out.println("Invalid input");
			}
		}
	}
	
	
	private void printCourseStats() {
		// TODO
	}
	
	
	private void addFacultyStaff() {
		String confirm = "y";	// add confirmation later
		
		ArrayList<String> facultyNameList = university.getFacultyNameList();
		
		System.out.println("Enter Staff Name:");
		String staffName = sc.nextLine();
		
		System.out.println("Choose faculty:");
		printArray(facultyNameList);
		String facultyName = facultyNameList.get(sc.nextInt() - 1);
		
		university.addStaffToFaculty(facultyName, staffName);
		
		// success message
		System.out.println("New Faculty Staff " + staffName + " successfully added.");
		System.out.println("--- Faculty Staff List ---");
		printArray(university.getFacultyByName(facultyName).getStaffNameList());
		System.out.println("-----------\n");
		
		
	}

	private void addStudent() {
		String confirm = "y";	// add confirmation later
	
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
		String res = string.substring(0, index);
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
				+ " 6. Exit interface\n"
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
				
				System.out.println("Add more components? (yes: 1/no: 0): ");
				more = getChoice();
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
				System.out.println(total);
			}
			if (total == 100)
				return true;
			else 
				return false;
		}
	}
	
}
