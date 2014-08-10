/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proctolearningsystem;

import java.awt.Container;
import javax.swing.JFrame;

/**
 *
 * @author lbissonnette
 */
public interface View {
    /**
     * Removes all content on the current screen.
     * @return true if the content was removed without error.
     */
    public boolean remove();
    /**
     * Sets screen content/makes the requested view screen active.
     * @return true if content was added without error.
     */
     public boolean makeActive(Controller controller, JFrame frame, Container container);
}
