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
			
			default:
				System.out.println("Invalid input");
			}
		}
	}
	
	private void addFacultyStaff() {
		String confirm = "y";	// add confirmation later
		
		ArrayList<String> facultyNameList = university.getFacultyNameList();
		
		System.out.println("Enter Staff Name:");
		String staffName = sc.nextLine();
		
		System.out.println("Choose faculty:");
		printArray(facultyNameList);
		String facultyName = facultyNameList.get(sc.nextInt() - 1);
		
		university.addStaffToFaculty(facultyName, staffName);;
		
	}

	private void addStudent() {
		String confirm = "y";	// add confirmation later
	
		ArrayList<String> facultyNameList = university.getFacultyNameList();
		
		System.out.println("Enter Student Name:");
		String studentName = sc.nextLine();
		
		System.out.println("Choose faculty:");
		printArray(facultyNameList);
		String facultyName = facultyNameList.get(sc.nextInt() - 1);	
	
		university.addStudentToFaculty(facultyName, studentName);
		
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
		String staffName = staffNameList.get(getChoice() - 1);	
		
		System.out.println("Choose Lesson Type:");
		LessonType.printLessonTypes();
		LessonType lessonType = getLessonType();
		
		university.addCourseToFaculty(facultyName, courseCode, courseName, staffName, lessonType, university.getCurrentSemester());
		
	}

	private LessonType getLessonType() {
		System.out.println("Enter Lesson Type (e.g. 'A'): ");
		char type = sc.nextLine().charAt(0);
		
		switch(type) {
		case 'A':
			return LessonType.TYPE_A;
			
		case 'B':
			return LessonType.TYPE_B;
			
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
			System.out.println(i+1 + ") " + list.get(i));
		
	}

	private void printMenu() {
		System.out.print(
				  "--- ADMIN MENU ---\n"
				+ "1) Add Faculty\n"
				+ "2) Add Course\n"
				+ "3) Add Faculty Staff\n"
				+ "4) Add Student\n"
				+ "5) Print Course Statistics\n"); 
		
	}
	
	private void printCourseRegMenu() {
		System.out.print(
				  "1) Courses from my Faculty\n"
				+ "2) Courses from other Faculty" ); 
	}

	
	private int getChoice() {
		int choice = sc.nextInt();
		sc.nextLine();
		return choice;
	}
	
}
