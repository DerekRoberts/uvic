public class StudentListTester
{
	public static void main (String[] args)
	{
		StudentList l = new StudentList();
		l.add("Jane", "V001");
		l.add("Joe", "V002");
		l.add("Susan", "V003");
		l.add("Bob", "V004");
		System.out.println(l);
		l.removeAt(2);
		System.out.println(l);
		l.removeAt(1);
		System.out.println(l);
		l.removeAt(0);
		System.out.println(l);	
	}
}
