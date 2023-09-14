package org.cytoscape.engnet.controller.listener;

import java.util.Map.Entry;

import org.cytoscape.engnet.controller.ResultPanelController;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.events.NetworkAboutToBeDestroyedEvent;
import org.cytoscape.model.events.NetworkAboutToBeDestroyedListener;


public class NetworkClosedListener implements NetworkAboutToBeDestroyedListener {
    @Override
    public void handleEvent(NetworkAboutToBeDestroyedEvent nvde) {
        CyNetwork network = nvde.getNetwork();
        for (Entry<CyNetwork, ResultPanelController> en : ResultPanelController.panels.entrySet()) {
            if (en.getKey().equals(network)) {
                en.getValue().dispose();
                break;
            }
        }
    }
}
