package org.cytoscape.engnet.model.businessobjects;


import java.util.ArrayList;

import org.cytoscape.engnet.model.businessobjects.model.Graph;
import org.cytoscape.engnet.model.businessobjects.model.io.Gen;
import org.cytoscape.engnet.model.businessobjects.model.performance.GRN;


public class EnGNetResult {
    private final double fNMI;
    private final double fKendall;
    private final double fSpearman;
    private final double fAverage;
    private final float fThb;
	public final Graph finalNetwork;
	private ArrayList<Gen> genes;
	private GRN g;

    
    public EnGNetResult(double fNMI, double fKendall, double fSpearman, double fAverage, int fThb, Graph finalNetwork, ArrayList<Gen> genes, GRN g) {
        this.fNMI = fNMI;
        this.fKendall = fKendall;
        this.fSpearman = fSpearman;
        this.fAverage = fAverage;
        this.fThb = fThb;
        this.finalNetwork = finalNetwork;
        this.genes = genes;
        this.g = g;
    }
    
    public Graph getFinalNetwork() {
    	return finalNetwork;
    }   
    

	public double getfNMI() {
		return fNMI;
	}

	public double getfKendall() {
		return fKendall;
	}

	public double getfSpearman() {
		return fSpearman;
	}

	public double getfAverage() {
		return fAverage;
	}

	public float getfThb() {
		return fThb;
	}

	public ArrayList<Gen> getGenes() {
		return genes;
	}
	
	public GRN getg() {
		return g;
	}
	
}
