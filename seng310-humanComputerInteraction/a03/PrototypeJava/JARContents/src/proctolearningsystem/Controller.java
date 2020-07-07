package proctolearningsystem;

/**
 *
 * @author fmd
 */
public class Controller {

    MasterView view;
    Model model;
    public Controller()
    {
        model = new Model();
        view = new MasterView(this,model);
    }
    
    
    
    public boolean verifyUserNameAndPassword(String username, String password, String designation) {
        System.out.print("data: ");
        if ( username != null ) {
            System.out.print(username + ", ");
        }
        if ( password != null ) {
            System.out.print(password + ", ");
        }
        if ( designation != null ) {
            System.out.print(designation + ".");
        }
        System.out.println();
        return true;
    }

    public void startLogin() {
        view.setLogin();
    }
    
    public void startMainSystem(String username, String designation) {
        System.out.printf("Removing current screen\n");
        view.setMain();
        
    }
    

}