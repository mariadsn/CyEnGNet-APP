package org.cytoscape.engnet;

import java.util.Properties;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.engnet.controller.NetworkController;
import org.cytoscape.engnet.controller.actions.MenuAction;
import org.cytoscape.engnet.controller.listener.NetworkClosedListener;
import org.cytoscape.engnet.controller.utils.CySwing2;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.io.read.VizmapReaderManager;
import org.cytoscape.model.events.NetworkAboutToBeDestroyedListener;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.work.TaskManager;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.osgi.framework.BundleContext;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;


public class EnGNet extends AbstractCyActivator {
    @Override
    public void start(BundleContext context) throws Exception {
        CyApplicationManager applicationManager = getService(context, CyApplicationManager.class);
        CySwingApplication swingApplication = getService(context, CySwingApplication.class);
        CyServiceRegistrar serviceRegistrar = getService(context, CyServiceRegistrar.class);
        VisualMappingManager visualMappingManager = getService(context, VisualMappingManager.class);
        VizmapReaderManager vizmapReaderManager = getService(context, VizmapReaderManager.class);
        TaskManager taskManager = getService(context, TaskManager.class);
        CyNetworkFactory networkFactory = getService(context, CyNetworkFactory.class);
        CyNetworkManager networkManager = getService(context, CyNetworkManager.class);
        CyNetworkViewManager networkViewManager = getService(context, CyNetworkViewManager.class);
        CyEventHelper eventHelper = getService(context, CyEventHelper.class);
        CyNetworkViewFactory networkViewFactory = getService(context, CyNetworkViewFactory.class);
        VisualStyleFactory visualStyleFactory = getService(context, VisualStyleFactory.class);
        CyLayoutAlgorithmManager layoutAlgorithmManager = getService(context, CyLayoutAlgorithmManager.class);
        VisualMappingFunctionFactory continuousMappingFactoryServiceRef = getService(context, VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
        
        CySwing2.init(swingApplication, serviceRegistrar);
        NetworkController.init(networkFactory, networkManager, networkViewManager, eventHelper, applicationManager, 
        		visualMappingManager, networkViewFactory, visualStyleFactory, layoutAlgorithmManager, taskManager, 
        		continuousMappingFactoryServiceRef);
        // UI controls
        MenuAction menuAction = new MenuAction(taskManager);
        registerService(context, menuAction, CyAction.class, new Properties());
        
        
        serviceRegistrar.registerService(new NetworkClosedListener(), NetworkAboutToBeDestroyedListener.class, new Properties());
    }
}
