package proctolearningsystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author fmd
 */
public class LoginFrame extends JFrame implements ActionListener,View{
    
    static final String proctoLocation = "procto" + File.separator + "procto2.gif";
    static final String welcomeMessage = "     Welcome to Procto Learning System!";
    static final String loginRequestMessage = "Please enter your username and password:";
    
    
    Controller controller;
    String[] designations = {"Student", "Faculty"};
    JButton btnLogin;
    JButton btnQuit;
    JComboBox designationSelector;
    String username;
    String password;
    String designation = designations[0];
    JTextField usernameEntry;
    JPasswordField passwordEntry;
    JTextArea messageField;
    JFrame loginFrame;
    Container mainContainer;
    Model model;
    
    public LoginFrame(Model model) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        this.model = model;
    }
    
    public boolean makeActive(Controller controller, JFrame frame, Container container)
    {
        this.controller = controller;
        this.loginFrame = frame;
        this.mainContainer = container;

        JTextArea greeting = new JTextArea(welcomeMessage);
        greeting.setFont(new Font("sansserif",Font.PLAIN,14));
        
        greeting.setEditable(false);
        //greeting.setEnabled(false);
        greeting.setFocusable(false);
        greeting.setFont(new Font(greeting.getFont().getFontName(), greeting.getFont().getStyle(), greeting.getFont().getSize() * 2));
        greeting.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        greeting.setForeground(new Color(191,0,255,200));
        
        
        mainContainer.add(greeting, BorderLayout.NORTH);
        
        
        JPanel window = new JPanel();
        window.setLayout(new BoxLayout(window, BoxLayout.X_AXIS));
        window.setBackground(Color.white);
        window.setVisible(true);
        
        mainContainer.add(window, BorderLayout.CENTER);
        mainContainer.add(Box.createHorizontalStrut(50), BorderLayout.EAST);
        
        JPanel labelsAndButtons = new JPanel();
        //labelsAndButtons.setLayout(new FlowLayout());
        labelsAndButtons.setLayout(new BoxLayout(labelsAndButtons, BoxLayout.Y_AXIS));
        //window.setPreferredSize(new Dimension(410,210));
        labelsAndButtons.setBackground(Color.white);
        //labelsAndButtons.setAlignmentY(TOP_ALIGNMENT);
        labelsAndButtons.setVisible(true);
        
        JLabel loginRequest = new JLabel(loginRequestMessage);
        loginRequest.setVisible(true);
        loginRequest.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        //loginRequest.setBackground(Color.red);
        loginRequest.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        loginRequest.setHorizontalTextPosition(JLabel.LEFT);
        loginRequest.setHorizontalAlignment(JLabel.LEFT);
        
        JPanel requestPanel = new JPanel();
        requestPanel.setVisible(true);
        requestPanel.setBackground(Color.white);
        requestPanel.add(loginRequest);
        
        labelsAndButtons.add(requestPanel);
        
        createLoginGUI(window, labelsAndButtons);
        
        window.add(labelsAndButtons);

        loginFrame.pack();
        return true;
    }
    
    private void createLoginGUI(Container container, JPanel labelsAndButtons) {
        int spacer1Height = 10;
        JPanel proctoPanel = new JPanel();
        proctoPanel.setLayout(new BoxLayout(proctoPanel, BoxLayout.X_AXIS));
        proctoPanel.setBackground(Color.white);
        proctoPanel.setVisible(true);
        
        ImageIcon image;
        image = new javax.swing.ImageIcon(getClass().getResource("/proctolearningsystem/procto/procto3.gif"));

        System.out.println(image.getIconHeight());
        spacer1Height = image.getIconHeight() / 8 ;
        System.out.println(spacer1Height);
        JLabel proctoLabel = new JLabel(image);
        proctoPanel.add(proctoLabel);
      
        container.add(proctoPanel);
        
        JPanel components = new JPanel();
        components.setLayout(new BoxLayout(components, BoxLayout.X_AXIS));
        components.setBackground(Color.white);
        components.setVisible(true);
        labelsAndButtons.add(components);
        
        makeLabels(components);
        makeEntries(components);
                
        JPanel messageArea = new JPanel();
        messageArea.setLayout(new FlowLayout(FlowLayout.LEFT));
        messageArea.setPreferredSize(new Dimension(10,spacer1Height));
        messageArea.setBackground(Color.white);
        labelsAndButtons.add(messageArea);
        
        messageField = new JTextArea();
        messageField.setEditable(false);
        messageField.setVisible(false);
        messageArea.add(messageField);
        
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,3,10,10));
        buttons.setBackground(Color.white);
        //entries.setPreferredSize(new Dimension(200,200));
        buttons.setVisible(true);
        labelsAndButtons.add(buttons);
        
        
        JPanel spacer2 = new JPanel();
        spacer2.setSize(1,10);
        spacer2.setBackground(Color.white);
        buttons.add(spacer2);

        btnQuit = new JButton("Quit");
        btnQuit.addActionListener(this);
        buttons.add(btnQuit);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(this);
        buttons.add(btnLogin);
        
        JPanel spacer3 = new JPanel();
        spacer3.setSize(1,50);
        spacer3.setBackground(Color.white);
        labelsAndButtons.add(spacer3);
        
    }

    private void makeLabels(Container container) {
        
        JPanel labels = new JPanel();
        labels.setLayout(new GridLayout(4,1));
        labels.setBackground(Color.white);
        //labels.setPreferredSize(new Dimension(40,200));
        labels.setVisible(true);
        
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        JLabel designationLabel = new JLabel("Designation: ");
        usernameLabel.setVisible(true);
        usernameLabel.setBackground(Color.white);
        passwordLabel.setVisible(true);
        passwordLabel.setBackground(Color.white);
        designationLabel.setVisible(true);
        designationLabel.setBackground(Color.white);
        
        
        labels.add(usernameLabel);
        labels.add(passwordLabel);
        labels.add(Box.createVerticalStrut(25));
        
        labels.add(designationLabel);
        
        container.add(labels);
    }
    
    private void makeEntries(Container container) {
        JPanel entries = new JPanel();
        entries.setLayout(new GridLayout(4,1));
        entries.setBackground(Color.white);
        entries.setVisible(true);
        
        usernameEntry = new JTextField(15);
        passwordEntry = new JPasswordField(15);
        designationSelector = new JComboBox(designations);
        designationSelector.setSelectedItem(designations[0]);
        
        usernameEntry.setText(Globals.users[0]);
        passwordEntry.setText("");
        
        usernameEntry.setVisible(true);
        passwordEntry.setVisible(true);
        designationSelector.setVisible(true);
        
        designationSelector.addActionListener(this);
        //usernameEntry.addActionListener(this);
        //passwordEntry.addActionListener(this);
        
        entries.add(usernameEntry);
        entries.add(passwordEntry);
        entries.add(Box.createVerticalStrut(25));
        entries.add(designationSelector);
        
        container.add(entries);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            System.out.println("event: " + e.getSource().toString());
        } catch(Exception a)
        {
            System.out.println("Event is a null pointer");
            return;
        }
        /*
        if ( e.getSource() == usernameEntry ) {
            username = usernameEntry.getText();
        }
        if ( e.getSource() == passwordEntry ) {
            password = passwordEntry.getText();
        }*/
        
        if ( e.getSource().equals(btnLogin)) {
            System.out.println("btnLogin pressed");
            
            username = usernameEntry.getText();
            password = passwordEntry.getPassword().toString();
            
            model.setUsername(username);
            model.setLoginType((String)designationSelector.getSelectedItem());
            
            if (controller.verifyUserNameAndPassword(username, password, designation)) {
                // name/pass ok
                System.out.println("verify returns true");
                // some how make the main system window
                controller.startMainSystem(username, designation);
                messageField.setVisible(false);
            } else {
                // name/pass not ok
                System.out.println("verify returns false");
                messageField.setText("Bad username or password. Please fix.");
                messageField.setVisible(true);
                messageField.getParent().doLayout();
                
            }
        }
        if ( e.getSource().equals(designationSelector)) {
            designation = designationSelector.getSelectedItem().toString();
        }
        if ( e.getSource().equals(btnQuit)) {
            System.exit(0);
        }
    }
    
    public String getUsername()
    {
        return new String(usernameEntry.getText());
    }
    
   public boolean remove()
   {
       try{
            mainContainer.removeAll();
            //loginFrame.revalidate();
            loginFrame.repaint();
            loginFrame.setVisible(false);
            dispose();
            return true;
       } catch (Exception e)
       {
           System.out.println("Unable to remove LoginFrame");
           return false;
       }
   }
}
