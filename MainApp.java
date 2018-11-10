import java.util.Scanner;

public class MainApp {

	
	public static University university;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		university = new University("NTU");
		
		// dummy data -- faculty
		university.addFaculty("SCSE");
		university.addFaculty("NBS");
		
		// dummy data -- faculty staff
		university.addStaffToFaculty("SCSE", "Alice");
		university.addStaffToFaculty("NBS", "Bob");
		
		// dummy data -- student
		university.addStudentToFaculty("SCSE", "Cat", university.getCurrentSemester());
		university.addStudentToFaculty("SCSE", "Darwin", university.getCurrentSemester());
		
		// dummy data -- course
//		 university.addCourseToFaculty("SCSE", "CS100", "algorithms", 
//					"F100001 ", LessonType.TYPE_A , null , university.getCurrentSemester());
//		 university.addCourseToFaculty("NBS", "B100", "business", 
//					"F100002 ", LessonType.TYPE_B , null , university.getCurrentSemester());
		 
		
		printMainMenu();
		int choice = getChoice();
		while(choice != 6) {
			switch(choice) {
			case 1:
				launchInterface("admin");
				break;
				
			case 2:
				launchInterface("student");
				break;
			}
			printMainMenu();
			choice = getChoice();
		}

	}

	private static void launchInterface(String mode) {
		if (mode.equals("student")) {
			String matricNo = getMatricNo();
			Faculty currentFaculty = university.getFacultyOfStudent(matricNo);
			
			if (currentFaculty == null) 
				System.out.println("Student not found");
			
			else {
				System.out.println("Launching student interface...");
				StudentInterface si = new StudentInterface(currentFaculty, matricNo, university);
				si.run();
			}	
		}
		else {
			AdminInterface ai = new AdminInterface(university);
			ai.run();
		}
		
	}

	
	private static String getMatricNo() {
		System.out.println("Enter matric No: ");
		String matricNo = sc.nextLine();
		return matricNo;
	}

	
	private static int getChoice() {
		System.out.println("Enter choice: ");
		int choice = sc.nextInt();
		sc.nextLine();
		return choice;
	}

	
	private static void printMainMenu() {
		System.out.print(
				  "### Main Menu ###\n"
				+ " 1. admin\n"
				+ " 2. student\n"
				+ "~~~~~~~~~~~~~~~~~\n"); 
		
	}

}
