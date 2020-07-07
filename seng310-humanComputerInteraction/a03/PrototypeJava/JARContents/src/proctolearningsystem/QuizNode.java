/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proctolearningsystem;

/**
 *
 * @author lbissonnette
 */
public class QuizNode {
    private QuizNode next;
    private QuizNode prev;
    private QuizQuestion question;
    
    QuizNode(){}
    QuizNode(QuizQuestion question)
    {
        this.question = question;
    }
    /**
     * @return the next
     */
    public QuizNode getNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(QuizNode next) {
        this.next = next;
    }

    /**
     * @return the prev
     */
    public QuizNode getPrev() {
        return prev;
    }

    /**
     * @param prev the prev to set
     */
    public void setPrev(QuizNode prev) {
        this.prev = prev;
    }

    /**
     * @return the question
     */
    public QuizQuestion getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(QuizQuestion question) {
        this.question = question;
    }
}
