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

public class FacultyStaffList {
	public static final String SEPARATOR = "|";

	public static ArrayList readFacultyStaff(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList alr = new ArrayList() ;// to store Professors data
        for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				String[] strArr = st.split("\\|");
				// get individual 'fields' of the string separated by SEPARATOR
				//StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				String  name = strArr[0];// first token
				String  staffID = strArr[1];// second token
				FacultyStaff staff = new FacultyStaff(name, staffID);
				/*for(int j=2; j < strArr.length ;j=j+3) {                         //to add in if we need workloadbysemester
					//year,number
					int year = Integer.parseInt(strArr[j]);
					int number = Integer.parseInt(strArr[j+1]);
					String coursecode= strArr[j+2];
					String[] str = coursecode.split("\\,");
					ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(str));
				}*/
				alr.add(staff) ;

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
  public static void write(String fileName, List data) throws IOException  {
	  PrintWriter out = new PrintWriter(new FileWriter(fileName));

	  try {
			for (int i =0; i < data.size() ; i++) {
	    		out.println((String)data.get(i));
			}
	  }
	  finally {
	    out.close();
	  }
	}


public static void writeStaffList(String filename, ArrayList<FacultyStaff> al) throws IOException {
	List writeStaffList = new ArrayList() ;// to store faculty staff data

    for (int i = 0 ; i < al.size() ; i++) {
    		FacultyStaff staff = (FacultyStaff)al.get(i);
			StringBuilder st =  new StringBuilder() ;
			
			st.append(staff.getStaffName().trim());
			st.append(SEPARATOR);
			st.append(staff.getStaffID().trim());			 

			writeStaffList.add(st.toString()) ;
		}
		write(filename,writeStaffList);
}
public static void main(String[] aArgs)  {
					
    	String filename = "FacultyStaff.txt" ;
		try {
			// read file containing Professor records.
			ArrayList<FacultyStaff> al = FacultyStaffList.readFacultyStaff(filename) ;

			for (int i = 0 ; i < al.size() ; i++) {
					FacultyStaff staff = (FacultyStaff)al.get(i);
					System.out.println("Name: " + staff.getStaffName() );
					System.out.println( "Staff ID: " + staff.getStaffID() );
			}
			FacultyStaff entry = new FacultyStaff("tom", "F100009");
			al.add(entry);
			FacultyStaffList.writeStaffList(filename, al);
		   }catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
  }
}
