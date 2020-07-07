/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proctolearningsystem;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Container;

/**
 *
 * @author lbissonnette
 */
public class MasterView {
    Controller controller;
    JFrame mainFrame; 
    Container mainContainer;
    View currentView;
    Model model;
    
    
    public MasterView(Controller controller, Model model) {
        this.model = model;
        this.controller = controller;
        mainFrame = new JFrame("Procto Learning System - Login");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //loginFrame.setSize(new Dimension(420,250));
        mainFrame.setVisible(true);
        
        mainContainer = mainFrame.getContentPane();
        mainContainer.setLayout(new BorderLayout());
        //window.setLayout(new BoxLayout(window, BoxLayout.X_AXIS));
        //window.setPreferredSize(new Dimension(410,210));
        mainContainer.setBackground(Color.white);
        mainContainer.setVisible(true);
    
    }

    public void setMain(){
        remove();
        currentView = new MainScreen(model);
        currentView.makeActive(controller,mainFrame,mainContainer);
    }
    
    public void setLogin(){
       remove();
       currentView = new LoginFrame(model);
       currentView.makeActive(controller,mainFrame,mainContainer);
       mainFrame.setVisible(true);
    }
    
    
    public boolean remove()
    { 
        if(currentView!= null)
            currentView.remove();
            
        return true;
    }
}
