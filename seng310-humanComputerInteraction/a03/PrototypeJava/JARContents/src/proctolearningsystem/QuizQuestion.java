/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proctolearningsystem;

/**
 *
 * @author lbissonnette
 */
public class QuizQuestion {
    private String type;
    private String question;
    private String answer;
    public String [] multiAnswer;
    private String studentAnswer;

    public QuizQuestion(){ multiAnswer = new String[5];}
    
    public String toString()
    {
        return question + " " + type;
    }
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return the studentAnswer
     */
    public String getStudentAnswer() {
        return studentAnswer;
    }

    /**
     * @param studentAnswer the studentAnswer to set
     */
    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }
    
    public boolean isTF()
    {
        if(type.equals("TF"))
            return true;
        return false;
    }
    
    public boolean isMulti()
    {
        return type.equals("Multi");
    }
    
    public boolean isLong()
    {
        return type.equals("Long");
    }
    
    
}
