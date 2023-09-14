package org.cytoscape.engnet.controller.utils;

import org.cytoscape.engnet.model.businessobjects.utils.ProgressMonitor;
import org.cytoscape.work.TaskMonitor;


public class CytoscapeTaskMonitor implements ProgressMonitor {
    private final TaskMonitor tm;
    
    public CytoscapeTaskMonitor(TaskMonitor tm) {
        this.tm = tm;
    }
    
    @Override
    public String getStatus() {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    @Override
    public void setStatus(String status) {
        tm.setStatusMessage(status);
    }
    
    @Override
    public float getProgress() {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    @Override
    public void setProgress(float progress) {
        tm.setProgress(progress);
    }
}
