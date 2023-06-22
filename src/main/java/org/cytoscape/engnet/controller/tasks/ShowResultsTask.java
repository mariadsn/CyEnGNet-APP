package org.cytoscape.engnet.controller.tasks;

import org.cytoscape.engnet.controller.ResultPanelController;
import org.cytoscape.engnet.controller.utils.CySwing2;
import org.cytoscape.engnet.controller.utils.CytoscapeTaskMonitor;
import org.cytoscape.engnet.model.businessobjects.EnGNetResult;
import org.cytoscape.engnet.model.businessobjects.utils.ProgressMonitor;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class ShowResultsTask extends AbstractTask {
    private final EnGNetResult result;
    
    public ShowResultsTask(EnGNetResult result) {
        this.result = result;
    }
    
    @Override
    public void run(TaskMonitor tm) {
        tm.setTitle("Displaying the results");
            
        try {
            ProgressMonitor pm = new CytoscapeTaskMonitor(tm);
            ResultPanelController rpc = new ResultPanelController(pm, result);
            insertTasksAfterCurrentTask(rpc.getRefreshNetworkTasks());
        } catch (Exception ex) {
        	CySwing2.displayPopUpMessage("ERROR:" + ex.getMessage());
        }
    }
    
    @Override
    public void cancel() {
        // TODO Cancel
        super.cancel();
    }
}
