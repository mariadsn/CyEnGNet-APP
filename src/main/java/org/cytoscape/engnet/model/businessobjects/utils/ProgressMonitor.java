package org.cytoscape.engnet.model.businessobjects.utils;


public interface ProgressMonitor {
    public String getStatus();
    public void setStatus(String status);
    public float getProgress();
    public void setProgress(float status);
}
