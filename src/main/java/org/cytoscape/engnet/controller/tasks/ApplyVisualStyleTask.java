package org.cytoscape.engnet.controller.tasks;

import org.cytoscape.engnet.controller.NetworkController;
import org.cytoscape.engnet.controller.utils.CySwing2;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;


public class ApplyVisualStyleTask extends AbstractTask {
    private final NetworkController network;
    
    public ApplyVisualStyleTask(NetworkController network) {
        this.network = network;
    }
    
    @Override
    public void run(TaskMonitor tm) {
        tm.setTitle("Applying visual style");
        tm.setStatusMessage("Applying visual style to the network.");
        
        try {
            network.applyVisualStyle();
        } catch (Exception ex) {
            String error = ex.getMessage();
            CySwing2.displayPopUpMessage(error == null || error.isEmpty()
                ? "An unexpected error ocurred while styling your results."
                : error);
        }
    }
    
    @Override
    public void cancel() {
        // TODO Cancel
        super.cancel();
    }
}
