/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proctolearningsystem;

/**
 *
 * @author lbissonnette
 */
public class Quiz {
    QuizNode head;
    QuizNode tail;
    QuizNode iterator;
    
    public int size;
    
    public Quiz()
    {
        size = 0;
        iterator = null;
    }
    
    public void addQuestion(QuizQuestion question) {
        try{
            tail.setNext(new QuizNode(question));
            tail.getNext().setPrev(tail);
            tail = tail.getNext();
            size ++;
        } catch(NullPointerException e)
        {
            if(size!=0) System.out.println("Quiz Size Error");
            head = new QuizNode(question);
            tail = head;
            size = 1;
        }
    }
    
    public QuizQuestion getNext()
    {
        if(iterator == null||iterator == tail)
        {
            iterator = head;
            return head.getQuestion();
        }
        iterator = iterator.getNext();
        return iterator.getQuestion();
    }
    
    public QuizQuestion getPrev()
    {
        if(iterator == null||iterator == head)
        {
            iterator = tail;
            return tail.getQuestion();
        }
        iterator = iterator.getPrev();
        return iterator.getQuestion();
    }
    public QuizQuestion getCurrent()
    {
        if(iterator==null)
        {
            iterator = head;
        }
        return iterator.getQuestion();
    }
}
