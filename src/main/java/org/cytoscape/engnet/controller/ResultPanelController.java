package org.cytoscape.engnet.controller;

import java.util.HashMap;
import java.util.Map;

import org.cytoscape.engnet.controller.utils.CySwing2;
import org.cytoscape.engnet.model.businessobjects.EnGNetResult;
import org.cytoscape.engnet.model.businessobjects.utils.ProgressMonitor;
import org.cytoscape.engnet.view.resultPanel.MainResultsView;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.TaskManager;
import org.cytoscape.work.TaskIterator;


public class ResultPanelController {
	private static TaskManager taskManager;
    public static final Map<CyNetwork, ResultPanelController> panels = new HashMap();
    
    private final NetworkController network;
    private EnGNetResult result;
    private MainResultsView rv;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public ResultPanelController(ProgressMonitor pm, EnGNetResult result) {
		pm.setStatus("Creating network and view.");
        this.network = new NetworkController(result);
        this.result = result;
        
        pm.setStatus("Creating result panel.");
        rv = new MainResultsView(taskManager, this);
        CySwing2.addPanel(rv);
        
        if (panels.containsKey(network.getCyNetwork())) {
            CySwing2.removePanel(panels.get(network.getCyNetwork()).rv, false);
        }
        panels.put(network.getCyNetwork(), this);
    }
    
    public static void init(TaskManager taskManager) {
    	ResultPanelController.taskManager = taskManager;
    }
    
    public void dispose() {
        panels.remove(network.getCyNetwork());
        CySwing2.removePanel(rv, panels.isEmpty());
        rv = null;
        result = null;
    }
    
    public TaskIterator getRefreshNetworkTasks() {
        return rv.getRefreshNetworkTasks();
    }
    
    public NetworkController getNetwork() {
    	return network;
    }
    
    public EnGNetResult getResult() {
        return result;
    }
}
