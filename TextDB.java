import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List;	
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class TextDB {
	public static final String SEPARATOR1 = "|";
	public static final String SEPERATOR2 = ",";
 // an example of reading
	public static ArrayList readStudents(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList alr = new ArrayList() ;// to store Students data

        for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				String[] strArr = st.split("\\|");
				// get individual 'fields' of the string separated by SEPARATOR
				
				String  name = strArr[0];// first token
				String  matricNo = strArr[1];// second token
				Student stud = new Student(name, matricNo);
				for(int j=2; j < strArr.length ;j=j+3) {
					//year,number
					int year = Integer.parseInt(strArr[j]);
					int number = Integer.parseInt(strArr[j+1]);
					String coursecode= strArr[j+2];
					String[] str = coursecode.split("\\,");
					ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(str));
					Semester sem = new Semester(year,number);
					stud.setCandidature(sem,arrayList);
				}
				// add to Student list
				alr.add(stud) ;
				
			}
			return alr ;
	}

    
	// an example of saving
	public static void saveStudents(String filename, List al) throws IOException {
		List alw = new ArrayList() ;// to store Students data

        for (int i = 0 ; i < al.size() ; i++) {
				Student stud = (Student)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(stud.getStudentName().trim());
				st.append(SEPARATOR1);
				st.append(stud.getMatricNo().trim());
				st.append(SEPARATOR1);
				HashMap<Semester, ArrayList<String>> candidature = stud.getCandidature();
				for(Semester sem : candidature.keySet()) {
					st.append(sem.getYear());
					st.append(SEPARATOR1);
					st.append(sem.getNumber());
					st.append(SEPARATOR1);
						for(String course: candidature.get(sem)) {
							st.append((course).trim());
							st.append(SEPERATOR2);
						}
					st.append(SEPARATOR1);
				}
				
				alw.add(st.toString()) ;
			}
			write(filename,alw);
	}

	//Write fixed content to the given file. 
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
    	TextDB txtDB = new TextDB();
    	String filename = "Students.txt" ;
		try {
			// read file containing Professor records.
			ArrayList<Student> al = TextDB.readStudents(filename) ;
			for (int i = 0 ; i < al.size() ; i++) {
					Student stud = (Student)al.get(i);
					System.out.println("Name " + stud.getStudentName() );
					System.out.println("MatricNo " + stud.getMatricNo() );
					stud.printCandidature();
			}
			
			// al is an array list containing Professor objs
			// write Professor record/s to file.
			TextDB.saveStudents(filename, al);
		   }catch (IOException e) {
			System.out.println("IOException > " + e.getMessage());
		}
  }
}

