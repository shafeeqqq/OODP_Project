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
				addStudent();
				break;
			
			default:
				System.out.println("Invalid input");
			}
		}
	}
	
	private void addStudent() {
		String confirm = "y";	// add confirmation later
	
		ArrayList<String> facultyNameList = university.getFacultyNameList();
		printArray(facultyNameList);
		
		String facultyName = facultyNameList.get(sc.nextInt() - 1);
		String studentName = sc.nextLine();
	
		university.addStudentToFaculty(facultyName, studentName);
		
	}

	private void printFacultyNameList() {
		
		
	}

	private void addCourse() {
		// TODO Auto-generated method stub
		
	}

	private void addFaculty() {
		System.out.println("Enter new faculty name: ");
		String facultyName = sc.nextLine();
		
		Faculty newFaculty = new Faculty (facultyName);
		
		university.addFaculty(newFaculty);
		
	}



	private void printArray(ArrayList<String> list) {
		for (int i=0; i<list.size(); i++) 
			System.out.println(i + ") " + list.get(i));
		
	}

	private void printMenu() {
		System.out.print(
				  "1) Add Faculty\n"
				+ "2) Add Course\n"
				+ "3) Add Student\n"
				+ "4) Print Course Statistics\n"); 
		
	}
	
	private void printCourseRegMenu() {
		System.out.print(
				  "1) Courses from my Faculty\n"
				+ "2) Courses from other Faculty" ); 
	}

	
	private int getChoice() {
		int choice = sc.nextInt();
		return choice;
	}
	
}
