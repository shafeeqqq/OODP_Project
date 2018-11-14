/**
 * Represents a semester of the university.
 */
public class Semester {
	/**
	 * The year of the semester 
	 */
	private int year;
	/**
	 * The number of the semester  (either 1/2)
	 * 
	 */
	private int number;
	/**
	 * Constructor for semester object, takes the semester number and the year of semester
	 * 
	 * @param year		The year of this Semester
	 * @param number	Number of the Semester (either 1/2)
	 */
	Semester(int year, int number) {
		this.year = year;
		this.number = number;
	}
	/**
	 * get method to retrieve the year of the Semester object
	 * 
	 * @return	an int specifying the first part of the Academic Year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * Get method to retrieve the semester number of the Semester object
	 * 
	 * @return	an int (either 1/2) specifying the semester number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Method which outputs the Semester information in a string. For example, 2018 Semester 1
	 * 
	 * @return	String containing the information specified above
	 */
	public String toString() {
		return (year + " Semester " + number);
	}
	
	
	@Override
	/**
	 * This method compares 2 semesters by taking another semester as its parameter and compares it 
	 * to the current semester and returns a boolean 
	 * 
	 * @param obj 	Takes in another semester object
	 * @return 		Returns true if the 2 semester have the same year and semester, false otherwise 
	 */
	public boolean equals(Object obj) {
		Semester semester = (Semester) obj;
		if (semester.getYear() == this.year && semester.getNumber() == this.number)
			return true;
		else
			return false;
	}

	@Override
	/**
	 * Calculate the key of the hash map.
	 * @return an integer which is used as a key for the hash map searching.
	 */
	public int hashCode() {
		final int prime = 2;
        int code = 1;
        code = prime * this.year + this.number; 
        return code;
	}
}
