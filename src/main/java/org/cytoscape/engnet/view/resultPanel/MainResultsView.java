package org.cytoscape.engnet.view.resultPanel;

import java.awt.Component;
import java.util.Collection;

import javax.swing.Icon;
import javax.swing.table.TableColumnModel;

import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.engnet.controller.NetworkController;
import org.cytoscape.engnet.controller.ResultPanelController;
import org.cytoscape.engnet.controller.tasks.ApplyVisualStyleTask;
import org.cytoscape.engnet.controller.utils.CySwing2;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskManager;


public class MainResultsView extends javax.swing.JPanel implements CytoPanelComponent {
	private final TaskManager taskManager;
	private final NetworkController network;
    private final ResultPanelController resultPanelController;
    private static VisualMappingManager visualMappingManager;
    private static VisualMappingFunctionFactory continuousMappingFactoryServiceRef;
    
    private CyNetworkView networkView;
    
    /**
     * Creates new form MainResultsView
     */
    public MainResultsView(TaskManager taskManager, ResultPanelController resultPanelController) {
        this.taskManager = taskManager;
    	this.resultPanelController = resultPanelController;
        this.network = resultPanelController.getNetwork();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nmiLabel = new javax.swing.JLabel();
        nmi = new javax.swing.JLabel();
        spearmanLabel = new javax.swing.JLabel();
        spearman = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        kendallLabel = new javax.swing.JLabel();
        kendall = new javax.swing.JLabel();
        closeResultsButton = new javax.swing.JButton();
        resultsTable = new javax.swing.JTable();
        SignificantLabel = new javax.swing.JLabel();
        significant = new javax.swing.JLabel();
        thbLabel = new javax.swing.JLabel();
        thb = new javax.swing.JLabel();

        nmiLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        nmiLabel.setText("NMI threshold");
       
        nmi.setText(String.format("%.2f", resultPanelController.getResult().getfNMI()));
        
        kendallLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        kendallLabel.setText("Kendall threshold");

        kendall.setText(String.format("%.2f", resultPanelController.getResult().getfKendall()));

        spearmanLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        spearmanLabel.setText("Spearman threshold");

        spearman.setText(String.format("%.2f", resultPanelController.getResult().getfSpearman()));
        
        SignificantLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        SignificantLabel.setText("Prune threshold");

        significant.setText(String.format("%.2f", resultPanelController.getResult().getfAverage()));
        
        thbLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        thbLabel.setText("Hub threshold");

        thb.setText(String.format("%.2f", resultPanelController.getResult().getfThb()));

        closeResultsButton.setText("Close results");
        closeResultsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeResultsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addComponent(closeResultsButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(nmiLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spearmanLabel, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kendallLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SignificantLabel, javax.swing.GroupLayout.Alignment.LEADING)
                        	.addComponent(thbLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))                        	                    
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nmi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spearman, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(kendall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(significant, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(thb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        		)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nmiLabel)
                    .addComponent(nmi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spearmanLabel)
                    .addComponent(spearman))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kendallLabel)
                    .addComponent(kendall))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SignificantLabel)
                        .addComponent(significant))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(thbLabel)
                        .addComponent(thb))
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addGap(50, 50, 50)
                .addComponent(closeResultsButton)
                .addContainerGap())
        );

        nmi.getAccessibleContext().setAccessibleName("fNMI");
        spearman.getAccessibleContext().setAccessibleName("fSpearman");
        kendall.getAccessibleContext().setAccessibleName("fSpearman");
    }// </editor-fold>//GEN-END:initComponents

    
    public TaskIterator getRefreshNetworkTasks() {
        TaskIterator tasks = new TaskIterator(new Task[] {
            //new UpdateFiltersTask(network),
            new ApplyVisualStyleTask(network),
        });
        //tasks.append(network.getApplyLayoutTask());
        return tasks;
    }
    
    private void refreshNetwork() {
        taskManager.execute(getRefreshNetworkTasks());
    }
    
    

    private void closeResultsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeResultsButtonActionPerformed
        resultPanelController.dispose();
    }//GEN-LAST:event_closeResultsButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeResultsButton;    
    private javax.swing.JLabel nmi; 
    private javax.swing.JLabel nmiLabel;
    private javax.swing.JLabel spearman;
    private javax.swing.JLabel spearmanLabel;
    private javax.swing.JLabel kendall; 
    private javax.swing.JLabel kendallLabel;
    private javax.swing.JTextField lagTextField;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable resultsTable;
    private javax.swing.JLabel SignificantLabel;
    private javax.swing.JLabel significant;
    private javax.swing.JLabel thbLabel;
    private javax.swing.JLabel thb;
    // End of variables declaration//GEN-END:variables

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public CytoPanelName getCytoPanelName() {
         return CytoPanelName.EAST;
    }

    @Override
    public String getTitle() {
        return "EnGNet";
    }

    @Override
    public Icon getIcon() {
        return null;
    }


}
