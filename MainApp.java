import java.util.Scanner;
//display all listing of students after addition of 1 student
public class MainApp {

	
	public static University university;
	public static FileIO file;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		university = new University("NTU");
		file = new FileIO(university);
		
		// dummy data -- faculty
		university.addFaculty("SCSE");
		university.addFaculty("NBS");
	
		file.populateData();
	
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
		boolean error = true;
		int choice = 0;
		do {//make sure it is the right input
			try {
				choice = sc.nextInt();
				error = false;
			}
			catch (Exception e) {
				System.out.println("You have entered a wrong input!");
				System.out.println("Please enter 1 or 2: ");
				sc.reset();
				sc.next();
			}			
		}while (error);
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
