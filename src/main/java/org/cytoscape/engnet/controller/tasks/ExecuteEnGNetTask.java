package org.cytoscape.engnet.controller.tasks;

import java.io.File;

import org.cytoscape.engnet.controller.utils.CySwing2;
import org.cytoscape.engnet.controller.utils.CytoscapeTaskMonitor;
import org.cytoscape.engnet.model.businessobjects.EnGNetResult;
import org.cytoscape.engnet.model.businessobjects.utils.ProgressMonitor;
import org.cytoscape.engnet.model.logic.EnGNet;
import org.cytoscape.engnet.view.configurationDialogs.ConfigurationDialog;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;


public class ExecuteEnGNetTask extends AbstractTask {
    private EnGNet engnet;
    private final File sPath;
    private final String sPathEntrada;
    private final double fNMI;
    private final double fKendall;
    private final double fSpearman;
    private final double fAverage;
    private final int fThb;

    private final ConfigurationDialog configurationDialog;
    
   public ExecuteEnGNetTask(File sPath, String sPathEntrada, double fNMI, double fKendall, double fSpearman, double fAverage, int fThb, ConfigurationDialog configurationDialog) {
	   this.sPath = sPath;
	   this.sPathEntrada = sPathEntrada;
	   this.fNMI = fNMI;
	   this.fKendall = fKendall;
	   this.fSpearman = fSpearman;
	   this.fAverage = fAverage;
	   this.fThb = fThb;
	   this.configurationDialog = configurationDialog;
    }
    
    @Override
    public void run(TaskMonitor tm) {
        
        try {
        	tm.setTitle("Executing EnGNet algorithm");
            ProgressMonitor pm = new CytoscapeTaskMonitor(tm);
            engnet = new EnGNet(pm);
            pm.setStatus("Loading input files");
            EnGNetResult result = engnet.execute(sPath, sPathEntrada, fNMI, fKendall, fSpearman, fAverage, fThb);
            CySwing2.displayPopUpMessage("EnGNet anlysis succesfully completed!");
            insertTasksAfterCurrentTask(new ShowResultsTask(result));
        } catch (Exception ex) {
        	String error = ex.getMessage();
            CySwing2.displayPopUpMessage(error == null || error.isEmpty()
            		? "An unexpected error ocurred during the analysis."
            		: error);
        }
    } 
    
    @Override
    public void cancel() {
        if (engnet != null) {
            engnet.interrupt();
        }
        super.cancel();
    }
}
