
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List;	
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ReadGrades {
	public static final String SEPARATOR = "|";

	public static ArrayList readGrades(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList alr = new ArrayList() ;// to store Professors data
        for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				String[] strArr = st.split("\\|");
		
				String  studentID = strArr[0];// first token
				String  tutorialGroup = strArr[1];// second token
				ArrayList<String> compName = new ArrayList<String>();
				ArrayList<String> compMarks = new ArrayList<>();
				for(int j=2; j < strArr.length ;j=j+2) {                         
					compName.add(strArr[j]);
					compMarks.add(strArr[j+1]);

				}
				StudentInfo studInfo = new StudentInfo(studentID, tutorialGroup, compName);
				//need a method to add marks into StudentInfo obj
				alr.add(studInfo) ;
			}
			return alr ;
	}
	
// Read the contents of the given file. 
  public static List read(String fileName) throws IOException {
	List data = new ArrayList() ;
    Scanner scanner = new Scanner(new FileInputStream(fileName));
    try {
      while (scanner.hasNextLine()){
        data.add(scanner.nextLine());
      }
    }
    finally{
      scanner.close();
    }
    return data;
  }
  
public static void main(String[] aArgs)  {
					
    	String filename = "CourseStudentInfo.txt" ;
		try {
			ArrayList<StudentInfo> al = ReadGrades.readGrades(filename) ; //returns arraylist of studentinfo objects

			/*for (int i = 0 ; i < al.size() ; i++) {
					FacultyStaff staff = (FacultyStaff)al.get(i);
					System.out.println("Name: " + staff.getStaffName() );
					System.out.println( "Staff ID: " + staff.getStaffID() );
			}*/
		   }catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
  }
}