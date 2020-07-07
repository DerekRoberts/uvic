public class StudentList
{	
	private StudentNode	head;
	private int		count;
	
	public StudentList() 
	{
		head = null;
	}
	
	public void add (String name, String number) 
	{
		StudentNode n = new StudentNode();
		n.element = new Student(name, number);
		
		n.next = head;
		head = n;
	}
	
	public int size () 
	{
		StudentNode p;
		p = head;
		count = 0;
		
		while (p!=null)
		{
			count++;
			p = p.next;
		}
		return count;
	}
	
	public String toString()
	{
		String s = "Student List size " + size() + " :\n";
		int pos = 0;
		StudentNode p = head;
		while (p!=null)
		{
			s += "[" + pos + "]:" ;
			s += p.element + "\n";
			p = p.next;
			pos++;
		}
		return s;
	}
	
	public Student atPosition(int i) 
	{
		if (i < 0 || i >= size() ) 
		{
			// this is an error condition, we cannot satisfy this request
			throw new IndexOutOfBoundsException("Invalid index in atPosition");
		}
		int pos = 0;
		StudentNode p = head;
		
		while (pos < i) 
		{
			p = p.next;
			pos++;
		}
		return p.element;
	}
	
	public void removeAt (int position) 
	{
		if (position < 0 || position >= size() ) {
			// this is an error condition, we cannot satisfy this request
			throw new IndexOutOfBoundsException("Invalid index in atPosition");
		}
		
		if (position == 0)
		{
			head = head.next;
		}
		else
		{
			/* There must be at least 2 elements in the list
			 * if you get here. 
			 */
			int i = 0;
			StudentNode p = head;
			while (i < (position - 1))
			{
				p = p.next;
				i++;
			}
			p.next = p.next.next;   /* Why can't this cause a null pointer reference */
		}		
	}
}
