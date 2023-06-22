package org.cytoscape.engnet.controller;

import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskManager;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.engnet.controller.utils.CySwing2;
import org.cytoscape.engnet.model.businessobjects.EnGNetResult;
import org.cytoscape.engnet.model.businessobjects.model.Arco;
import org.cytoscape.engnet.model.businessobjects.model.Grafo;
import org.cytoscape.engnet.model.businessobjects.model.io.Gen;
import org.cytoscape.engnet.model.businessobjects.model.performance.GRN;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.view.layout.CyLayoutAlgorithm;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;
import org.cytoscape.view.vizmap.mappings.ValueTranslator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyEdge;
import org.cytoscape.event.CyEventHelper;



/**
 * @license Apache License V2 <http://www.apache.org/licenses/LICENSE-2.0.html>
 * @author Maria del Saz
 */
public final class NetworkController {
	private static CyNetworkFactory networkFactory;
	private static CyNetworkManager networkManager;
    private static CyApplicationManager applicationManager;
    private static VisualMappingManager visualMappingManager;
    private static CyNetworkViewFactory networkViewFactory;
    private static CyNetworkViewManager networkViewManager;
    private static CyEventHelper eventHelper;
    private static VisualStyleFactory visualStyleFactory;
    private static CyLayoutAlgorithmManager layoutAlgorithmManager;
    private static TaskManager taskManager;
    private static VisualMappingFunctionFactory continuousMappingFactoryServiceRef;
    private static VisualMappingFunctionFactory continuousMappingFactory;
    
      
    public static final String NMIColumn = "NMI";    
    public static final String SpearmanColumn = "Spearman";
    public static final String KendallColumn = "Kendall";
    public static final String WeightColumn = "Weight";
        
    private final EnGNetResult result;
    private CyNetwork network;
    private CyNetworkView networkView;
    private Map<String, CyNode> nodes;
    private Grafo redFinal;
    private ArrayList<Gen> genes;
    private GRN g;
    
    private VisualStyle style;
    
    public static void init(CyNetworkFactory networkFactory, CyNetworkManager networkManager, CyNetworkViewManager networkViewManager, 
    		CyEventHelper eventHelper, CyApplicationManager applicationManager, VisualMappingManager visualMappingManager, 
    		CyNetworkViewFactory networkViewFactory, VisualStyleFactory visualStyleFactory, CyLayoutAlgorithmManager layoutAlgorithmManager,
    		TaskManager taskManager, VisualMappingFunctionFactory continuousMappingFactoryServiceRef) {
        NetworkController.networkFactory = networkFactory;
    	NetworkController.applicationManager = applicationManager;
        NetworkController.visualMappingManager = visualMappingManager;
        NetworkController.networkManager = networkManager;
        NetworkController.networkFactory = networkFactory;
        NetworkController.networkViewManager = networkViewManager;
        NetworkController.eventHelper = eventHelper;
        NetworkController.networkViewFactory = networkViewFactory;
        NetworkController.visualStyleFactory = visualStyleFactory;
        NetworkController.layoutAlgorithmManager = layoutAlgorithmManager;
        NetworkController.taskManager = taskManager;
        NetworkController.continuousMappingFactoryServiceRef = continuousMappingFactoryServiceRef;
        
    }
    
    public NetworkController(EnGNetResult result) {
    	this.result = result;
    	createCyNetwork();
    }
    
    public void createCyNetwork() {
    	try {
	    	network = networkFactory.createNetwork();
	    	network.getRow(network).set(CyNetwork.NAME, "EnGNet network");
	    	
	    	ArrayList<String> genes = new ArrayList<>();
    		Iterator<Arco> it = result.getRedFinal().getAristas().iterator();
    		while(it.hasNext()) {
    			Arco Arco = (Arco)it.next();
    			String nodoInit = Arco.getInicial();
    			String nodoFin = Arco.getTerminal();
    			if (!genes.contains(nodoInit)) {
    		        genes.add(nodoInit);
    		    }
    			if (!genes.contains(nodoFin)) {
    				genes.add(nodoFin);
    			}
    		}
    		
	    	nodes = new HashMap<>(genes.size());
	    	for (String gene: genes) {
	    		CyNode node = network.addNode();
	    		CyRow row = network.getRow(node);
	    		row.set(CyNetwork.NAME, gene);
	    		nodes.put(gene, node);
	    	}
	    	
	    	CyTable edgeTable = network.getDefaultEdgeTable();
	    	
	    	try {
	    		edgeTable.createColumn(NMIColumn, String.class, false);
	    	}catch (IllegalArgumentException ex){
	    		// la columna ya existe, no es necesario hacer nada
	    	}
	    	try {
	    		edgeTable.createColumn(KendallColumn, String.class, false);
	    	}catch (IllegalArgumentException ex){
	    		// la columna ya existe, no es necesario hacer nada
	    	}
	    	try {
	    		edgeTable.createColumn(SpearmanColumn, String.class, false);
	    	}catch (IllegalArgumentException ex){
	    		// la columna ya existe, no es necesario hacer nada
	    	}
	    	try {
	    		edgeTable.createColumn(WeightColumn, Double.class, false);
	    	}catch (IllegalArgumentException ex) {
	    		// la columna ya existe, no es necesario hacer nada
	    	}
	    	
	    	Set<String> existingEdges = new HashSet<>();
	    	
	    	for (Arco edge: result.getRedFinal().getAristas()) {
        		String sourceName = edge.getInicial();
        		String targetName = edge.getTerminal();
        		String edgeKey = sourceName + "_" + targetName;
        		
        		if (!existingEdges.contains(edgeKey)) {
        			CyNode source = nodes.get(edge.getInicial());
            		CyNode target = nodes.get(edge.getTerminal());
            		double weight = edge.getPeso();
            		CyEdge cyEdge = network.addEdge(source, target, false);
            		existingEdges.add(edgeKey);
            		CyRow row = network.getRow(cyEdge);
            		row.set(CyNetwork.NAME, sourceName);
            		row.set(CyEdge.INTERACTION, edge.getTerminal());
            		row.set(WeightColumn, weight);
            		
            		int contador = 0;
            		Iterator<Arco> id = result.getg().getArcos().iterator();
            		while(id.hasNext()) {
            			Arco Arco = (Arco)id.next();
            			String nodoInit = Arco.getInicial();
            			String nodoFin = Arco.getTerminal();
            			if (sourceName.equals(nodoInit) && targetName.equals(nodoFin)) {
            				String arcoString = Arco.toStringESM();
            				String[] elementos = arcoString.split("\t");
            				String nmiValue = elementos[2];
            				row.set(NMIColumn, nmiValue);
            				String kendallValue = elementos[3];
            				row.set(KendallColumn, kendallValue);
            				String spearmanValue = elementos[4];
            				row.set(SpearmanColumn, spearmanValue);
            				++contador;	
            			}else {
            				++contador;
            			}        		
            		}  
        		}
        	}
	    	
	        }catch(Exception ex) {
	        	CySwing2.displayPopUpMessage("ERROR:" + ex.getMessage());
	        	CySwing2.displayPopUpMessage("ERROR:" + ex.getLocalizedMessage());
	        	ex.printStackTrace();
    	}
    	
    	networkManager.addNetwork(network); 
    	networkView = networkViewFactory.createNetworkView(network); 
    	networkViewManager.addNetworkView(networkView);
    	
    	eventHelper.flushPayloadEvents();
    }
    
    public void dispose() {
    }
    
    public CyNetwork getCyNetwork() {
        return network;
    }
    
    public void applyVisualStyle() {
        if (style == null) {
        	style = visualStyleFactory.createVisualStyle(visualMappingManager.getDefaultVisualStyle());
        	style.setTitle("EnGNet network");
        	
            style.setDefaultValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.decode("#FF9999"));
            style.setDefaultValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.RECTANGLE);
            style.setDefaultValue(BasicVisualLexicon.NODE_LABEL_COLOR, Color.decode("#000000"));
            style.setDefaultValue(BasicVisualLexicon.NODE_BORDER_PAINT, Color.CYAN);
            style.setDefaultValue(BasicVisualLexicon.NODE_BORDER_WIDTH, 1.0);
            
            ContinuousMapping<Double, Double> edgeWidthMapping = (ContinuousMapping<Double, Double>) continuousMappingFactoryServiceRef.createVisualMappingFunction(WeightColumn, Double.class, BasicVisualLexicon.EDGE_WIDTH);
            edgeWidthMapping.addPoint(0.0, new BoundaryRangeValues<>(1.0, 1.0, 1.0));
            edgeWidthMapping.addPoint(1.0, new BoundaryRangeValues<>(5.0, 5.0, 5.0));
            style.addVisualMappingFunction(edgeWidthMapping);
            
            
            style.apply(networkView);
            visualMappingManager.setVisualStyle(style, networkView);
            
            CyLayoutAlgorithm layoutAlgorithm = layoutAlgorithmManager.getLayout("force-directed");
            TaskIterator layoutTaskIterator = layoutAlgorithm.createTaskIterator(networkView, layoutAlgorithm.createLayoutContext(), CyLayoutAlgorithm.ALL_NODE_VIEWS, null);
            taskManager.execute(layoutTaskIterator);

            networkView.updateView();
            }
        }   
}