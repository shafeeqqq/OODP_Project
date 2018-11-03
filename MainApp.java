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
			
			
		}
		
	}

	private static String getMatricNo() {
		System.out.println("Enter matric No: ");
		
		String matricNo = sc.nextLine();
		while (university.)
		return null;
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
