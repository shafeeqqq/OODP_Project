import java.util.Scanner;

public class MainApp {

	
	public static University university;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		university = new University("NTU");
		
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

	private static void launchInterface(String type) {
		if (type.equals("student")) {
			String matricNo = getMatricNo();
			Faculty currentFaculty = university.getFacultyOfStudent(matricNo);
			
			if (currentFaculty == null) 
				System.out.println("Student not found");
			
			else {
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
		while(!university.isValidMatricNo(matricNo)) {
			System.out.println("Please enter a valid matric No: ");
			matricNo = sc.nextLine();
		}
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
				  "1) admin\n"
				+ "2) student\n"); 
		
	}

}
