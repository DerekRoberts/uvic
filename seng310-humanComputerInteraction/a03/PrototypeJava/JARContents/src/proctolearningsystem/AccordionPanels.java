
package proctolearningsystem;

import MakeQuiz.NewTest;
import TakeQuizPackage.TakeQuiz;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author fmd
 */
public class AccordionPanels implements ActionListener {
    Model model;
    String className;
    String designation;
    int numberOfPanels = Globals.accordionSections.length;
    JPanel[] panels;
    JList[] lists;

    AccordionPanels(Model model) {
        this.model = model;
        this.className = model.getCurrentClass();
        this.designation = model.getLoginType();
        panels = new JPanel[numberOfPanels];
        lists = new JList[numberOfPanels];
        try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AccordionPanels.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(AccordionPanels.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AccordionPanels.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(AccordionPanels.class.getName()).log(Level.SEVERE, null, ex);
            }
        createPanels();
    }

    public void updateAccordionContents(String className) {
        this.className = className;
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AccordionPanels.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AccordionPanels.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AccordionPanels.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(AccordionPanels.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("updating accordion with class: " + this.className);

        for ( int i = 0; i < numberOfPanels; i++) {
            fillPanel(i);
        }
        
        for ( int i = 0; i < numberOfPanels; i++) {
            panels[i].repaint();
            panels[i].validate();
        }
    }

    private JPanel[] createPanels() {
        for ( int i = 0; i < numberOfPanels; i++) {
            panels[i] = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
            
        }

        for ( int i = 0; i < numberOfPanels; i++) {
            fillPanel(i);
        }

        return panels;
    }

    private void fillPanel(int panelNum) {

        lists[panelNum] = new JList();
        String[] content = {""};

        panels[panelNum].setBackground(Color.white);
        panels[panelNum].setLayout(new BoxLayout(panels[panelNum], BoxLayout.Y_AXIS));

        switch (panelNum) {
            case 0: // syllabus
                if ( model.isFaculty() ) {
                    content = new String[] {"Edit Course Outline", "Set Office Hours", "Set Contact Information", "View Enrollment List"};
                } else {
                    content = new String[] {"Course Outline", "Office Hours", "Contact Instructor"};
                }
                break;
            case 1: // calendar
                if ( model.isFaculty() ) {
                    content = new String[] {"Add New Event", "Upcoming Event 1", "Class related Event", "Upcoming Exam"};
                } else {
                    content = new String[] {"Upcoming Event 1", "Class related Event", "Upcoming Exam"};
                }
                break;
            case 2: // lesarning materials
                if ( model.isFaculty() ) {
                    content = new String[] {"Add New Resource", "Lecture Notes"};
                } else {
                    content = new String[] {"Latest Resource", "Lecture Notes"};
                }
                break;
            case 3: // testing
                if ( model.isFaculty() ) {
                    content = new String[] {"Add New Test", "Test #3", "Test #2", "Test #1"};
                } else {
                    content = new String[] {"Test #4", "Test #3", "Test #2", "Test #1"};
                }
                break;
            case 4: //assignments
                content = new String[] {"Assignment #3", "Assignment #2", "Assignment #1"};
                break;
            case 5: // gradebook
                content = new String[] {"Assignment grades", "Midterm Grade", "Final Exam Grade"};
                break;
            case 6: /// forum
                content = new String[] {className + " Discussion", "General Discussion Forum", "Send Direct Message", "Suggestion Box"};
                break;
            default:
                break;

        }

        if (panels[panelNum].getComponentCount() != 0 ) {
            //System.out.println("Componenent count: " + panels[panelNum].getComponentCount());
            panels[panelNum].removeAll();
        } else {
            //System.out.println("Componenent count: " + panels[panelNum].getComponentCount());
        }

        //System.out.println("Adding buttons...");
        for ( int i = 0; i < content.length; i++ ) {
            
            JButton button = new JButton();

            String text = "<HTML><FONT color=\"#000099\"><U>" + content[i] + "</U></FONT></HTML>";

            button.setText(text);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBorderPainted(false);

            button.setOpaque(false);
            button.setBackground(Color.white);
            button.setVisible(true);
            button.addActionListener(this);
            panels[panelNum].add(button);
        }

        /*
        if (panels[panelNum].getComponentCount() == 0 ) {
            lists[panelNum] = new JList(content);
            lists[panelNum].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            lists[panelNum].addListSelectionListener(new ListSelectionListener() {

                @Override
                public void valueChanged(ListSelectionEvent e) {
                    JList list = (JList)e.getSource();3.63 lbs
                    if (e.getValueIsAdjusting() == false) {

                        if (list.getSelectedIndex() == -1) {
                        //No selection, disable fire button.
                            list.setEnabled(false);

                        } else {
                        //Selection, enable the fire button.
                            list.setEnabled(true);
                            if (list.getSelectedValue() == "Add New Resource" ) {
                                System.out.println("adding resource...");
                                new AddResource();
                            }
                            if (list.getSelectedValue() == "Add New Test" ) {
                                System.out.println("adding test...");
                                new NewTest().setVisible(true);
                            }
                            if (list.getSelectedValue().toString().contains("Test #" )) {
                                System.out.println("taking test...");
                                new TakeQuiz();
                            }
                        }
                    }
                }
            });


            panels[panelNum].add(lists[panelNum]);
        } else {
            JList temp = (JList)panels[panelNum].getComponent(0);
            temp.setListData(content);
        }*/
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JButton but = (JButton) ae.getSource();

        if ( but.getText().contains("Add New Resource")) {
            System.out.println("adding resource...");
            new AddResource();
        }

        if ( but.getText().contains("Add New Test")) {
            System.out.println("adding test...");
            new NewTest().setVisible(true);
        }

        if ( but.getText().contains("Test #")) {
            System.out.println("taking test...");
            new TakeQuiz();
        }
    }
}
