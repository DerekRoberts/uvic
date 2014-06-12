public class Student
{
	private String	name;
	private	String	studentNumber;
	private	String	grade;
	
	public Student(String theName, String theNumber)
	{
		name = theName;
		studentNumber = theNumber;
		grade = "N";
	}
	
	public void setGrade (String newGrade)
	{
		grade = newGrade;
	}
	
	public String toString()
	{
		String s = name + " " + studentNumber + " got a(n) " + grade;
		return s;
	}
}
