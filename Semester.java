
public class Semester {
	private int year;
	private int number;
	
	Semester(int year, int number) {
		this.year = year;
		this.number = number;
	}

	public int getYear() {
		return year;
	}

	public int getNumber() {
		return number;
	}
	
	public String toString() {
		return (year + " Semester " + number);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		Semester semester = (Semester) obj;
		if (semester.getYear() == this.year && semester.getNumber() == this.number)
			return true;
		else
			return false;
	}

	@Override
	public int hashCode() {
		final int prime = 2;
        int code = 1;
        code = prime * this.year + this.number; 
        return code;
	}

	
	
}
