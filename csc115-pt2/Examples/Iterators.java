/**
Before you can access a collection through an iterator, you must obtain one. Each of the collection classes provides an iterator( ) method that returns an iterator to the start of the collection. By using this iterator object, you can access each element in the collection, one element at a time. In general, to use an iterator to cycle through the contents of a collection, follow these steps:

   1. Obtain an iterator to the start of the collection by calling the collection's iterator( )
   2. Set up a loop that makes a call to hasNext( ). Have the loop iterate as long as hasNext( ) returns true.
   3. Within the loop, obtain each element by calling next( ).

For collections that implement List, you can also obtain an iterator by calling ListIterator. As explained, a list iterator gives you the ability to access the collection in either the forward or backward direction and lets you modify an element. Otherwise, ListIterator is used just like Iterator.

Here is an example that implements these steps, demonstrating both Iterator and ListIterator. It uses an ArrayList object, but the general principles apply to any type of collection. Of course, ListIterator is available only to those collections that implement the List interface.
*/

// Demonstrate iterators. 
import java.util.*; 
class IteratorDemo { 
public static void main(String args[]){ 
// create an array list 
ArrayList al = new ArrayList(); 
// add elements to the array list 
al.add("C"); 
al.add("A"); 
al.add("E"); 
al.add("B"); 
al.add("D"); 
al.add("F"); 
// use iterator to display contents of al 
System.out.print("Original contents of al: "); 
Iterator itr = al.iterator(); 
while(itr.hasNext()) {

    Object element = itr.next(); 
    System.out.print(element + " ");

} 
System.out.println(); 
// modify objects being iterated 
ListIterator litr = al.listIterator(); 
while(litr.hasNext()) {

    Object element = litr.next(); 
    litr.set(element + "+");

} 
System.out.print("Modified contents of al: "); 
itr = al.iterator();
while(itr.hasNext()) {

    Object element = itr.next(); 
    System.out.print(element + " ");

} 
System.out.println(); 
// now, display the list backwards 
System.out.print("Modified list backwards: "); 
while(litr.hasPrevious()) {

    Object element = litr.previous(); 
    System.out.print(element + " ");

} 
System.out.println(); 
} 
}

/**
The output is shown here: 

Original contents of al: C A E B D F 
Modified contents of al: C+ A+ E+ B+ D+ F+ 
Modified list backwards: F+ D+ B+ E+ A+ C+

Pay special attention to how the list is displayed in reverse. After the list is modified, litr points to the end of the list. (Remember, litr.hasNext( ) returns false when the end of the list has been reached.) To traverse the list in reverse, the program continues to use litr, but this time it checks to see whether it
*/
