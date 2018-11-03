import java.util.Scanner;

public class MainApp {

	public static University university;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		university = new University("NTU");
		
		printMainMenu();
		int choice = getChoice();
		
		switch(choice) {
		case 1:
			launchInterface("admin");
			break;
			
		case 2:
			launchInterface("student");
		}
	}

	private static void launchInterface(String type) {
		if (type.equals("admin")) {
			String matricNo = getMatricNo();
			Student currentStudent = university.getStudent(matricNo);
			
			if (currentStudent == null) 
				System.out.println("Student not found");
			else {
				
			}
			
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
		int choice = sc.nextInt();
		return choice;
	}

	private static void printMainMenu() {
		System.out.print(
				  "1) admin"
				+ "2) student"); 
		
	}

}
