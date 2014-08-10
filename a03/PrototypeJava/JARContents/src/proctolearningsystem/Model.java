/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proctolearningsystem;

/**
 *
 * @author lbissonnette
 */
public class Model {
    private String username;
    private String loginType; //Faculty or Student
    private String currentClass;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the loginType
     */
    public String getLoginType() {
        return loginType;
    }

    /**
     * @param loginType the loginType to set
     */
    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
    
    public boolean isFaculty()
    {
        return loginType.equals("Faculty");
    }
    
    public boolean isStudent()
    {
        return loginType.equals("Student");
    }

    /**
     * @return the currentClass
     */
    public String getCurrentClass() {
        return currentClass;
    }

    /**
     * @param currentClass the currentClass to set
     */
    public void setCurrentClass(String currentClass) {
        this.currentClass = currentClass;
    }
    
    
}
