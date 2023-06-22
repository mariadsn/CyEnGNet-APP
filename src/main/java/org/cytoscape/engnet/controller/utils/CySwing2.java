package org.cytoscape.engnet.controller.utils;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.application.swing.CytoPanelState;
import org.cytoscape.service.util.CyServiceRegistrar;

/**
 * @license Apache License V2 <http://www.apache.org/licenses/LICENSE-2.0.html>
 * @author María del Saz Navarro
 */
public class CySwing2 {
    private static CySwingApplication cySwingApplication;
    private static CyServiceRegistrar serviceRegistrar;

    private static JFrame CytoscapeJFrame;
    private static CytoPanel eastPanel;
    
    public static void init(CySwingApplication cySwingApplication, CyServiceRegistrar serviceRegistrar) {
        CySwing2.cySwingApplication = cySwingApplication;
        CySwing2.serviceRegistrar = serviceRegistrar;
    }
    
    public static JFrame getDesktopJFrame() {
        if (CytoscapeJFrame == null) {
            CytoscapeJFrame = cySwingApplication.getJFrame();
        }
        return CytoscapeJFrame;
    }
    
    private static CytoPanel getEastPanel() {
        if (eastPanel == null) {
            eastPanel = cySwingApplication.getCytoPanel(CytoPanelName.EAST);
        }
        return eastPanel;
    }
    
    public static void displayPopUpMessage(final String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(getDesktopJFrame(), message);
            }
        });
    }
    
    public static void displayDialog(final JDialog dialog) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        dialog.dispose();
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    public static void addPanel(CytoPanelComponent panel) {
        serviceRegistrar.registerService(panel, CytoPanelComponent.class, new Properties());
        selectPanel(panel);
    }
    
    public static void removePanel(CytoPanelComponent panel, boolean hideResultsPanel) {
        serviceRegistrar.unregisterService(panel, CytoPanelComponent.class);
        if (hideResultsPanel) {
            getEastPanel().setState(CytoPanelState.HIDE);
        }
    }
    
    public static void selectPanel(CytoPanelComponent panel) {
        int panelIndex = getEastPanel().indexOfComponent(panel.getComponent());
        getEastPanel().setSelectedIndex(panelIndex);
        getEastPanel().setState(CytoPanelState.DOCK);
    }
}