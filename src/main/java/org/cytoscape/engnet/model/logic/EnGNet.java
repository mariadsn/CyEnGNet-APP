package org.cytoscape.engnet.model.logic;

import java.util.ArrayList;
import java.util.Hashtable;
import java.io.File;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.cytoscape.engnet.model.businessobjects.EnGNetResult;
import org.cytoscape.engnet.model.businessobjects.MathematicalMeasurement.Correlation;
import org.cytoscape.engnet.model.businessobjects.MathematicalMeasurement.IMathematicalMeasurement;
import org.cytoscape.engnet.model.businessobjects.MathematicalMeasurement.NMI;
import org.cytoscape.engnet.model.businessobjects.exceptions.AnalysisErrorException;
import org.cytoscape.engnet.model.businessobjects.logic.KruskalAlgorithm;
import org.cytoscape.engnet.model.businessobjects.model.Arch;
import org.cytoscape.engnet.model.businessobjects.model.Graph;
import org.cytoscape.engnet.model.businessobjects.model.Node;
import org.cytoscape.engnet.model.businessobjects.model.io.GenesData;
import org.cytoscape.engnet.model.businessobjects.model.io.Gen;
import org.cytoscape.engnet.model.businessobjects.model.performance.GRN;
import org.cytoscape.engnet.model.businessobjects.utils.Constants;
import org.cytoscape.engnet.model.businessobjects.utils.ProgressMonitor;


/**
 * @license Apache License V2 <http://www.apache.org/licenses/LICENSE-2.0.html>
 * @author Maria del Saz Navarro
 */
public class EnGNet {
    private final ProgressMonitor pm;
    protected boolean isInterrupted = false;
    private final int maxThreads = Runtime.getRuntime().availableProcessors();
    private final ExecutorService pool = Executors.newFixedThreadPool(maxThreads);
    private final CompletionService completionService = new ExecutorCompletionService(pool);


    public EnGNet(ProgressMonitor pm) {
        this.pm = pm;
    }

    public void interrupt() {
        isInterrupted = true;
        pool.shutdownNow();
    }

    public boolean isInterrupted() {
        return isInterrupted;
    }

    public EnGNetResult execute(File sPath, String sPathEntry, double fNMI, double fKendall, double fSpearman, double fAverage, int fThb) {

        try {
        	// 1) Execute EnGNet
        	GenesData dg = new GenesData();
        	dg.readInputFile(sPathEntry);
        	GRN g = generateCompleteNetwork(dg, fKendall, fSpearman, fNMI);
        	ArrayList<Gen> genes = new ArrayList(dg.getGenesList());
        	g.dumpToFile(sPath + System.getProperty("file.separator") + "completeNetwork.txt");
        	Graph gc = new Graph(g);
        	Graph predominantNetwork = KruskalAlgorithm.applyKruskal(gc);
        	Graph finalNetwork = addGRNrelations(predominantNetwork, dg, fAverage, fThb, fKendall, fSpearman, fNMI);
        	//ArrayList<Gen> genes = new ArrayList(redFinal.getNodos());
        	finalNetwork.dumpToFile(sPath + System.getProperty("file.separator") + "finalNetwork.txt");    	
        	
        	
		return new EnGNetResult(fNMI,fKendall,fSpearman, fAverage,fThb,finalNetwork,genes,g);
        } catch (AnalysisErrorException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AnalysisErrorException(ex);
        } finally {
            pool.shutdownNow();
        }
    }

    private static GRN generateCompleteNetwork(GenesData dg, double fKendall, double fSpearman, double fNMI) {
        GRN g = GRN.getInstance();
        IMathematicalMeasurement measure3 = null;
        IMathematicalMeasurement measure1 = new NMI();
        IMathematicalMeasurement measure2 = new Correlation(Constants.KENDALL);
        measure3 = new Correlation(Constants.SPEARMAN);
        int numTotal = dg.getGenesList().size();
        ArrayList<Gen> iList = new ArrayList(dg.getGenesList());

        for(int i = 0; i < numTotal - 1; ++i) {
           Gen gene1 = (Gen)iList.get(i);
           for(int j = i + 1; j < numTotal; ++j) {
              Gen gene2 = (Gen)iList.get(j);
              int cont = 0;
              if (!gene1.getName().equals(gene2.getName())) {
                 float value1 = Math.abs(measure1.genGenRelationship(gene1, gene2));
                 float value2 = Math.abs(measure2.genGenRelationship(gene1, gene2));
                 float value3 = Math.abs(measure3.genGenRelationship(gene1, gene2));
                 if (value2 > fKendall) {
                    ++cont;
                 }

                 if (value3 > fSpearman) {
                    ++cont;
                 }

                 if (value1 >= fNMI) {
                    ++cont;
                 }

                 if (cont >= 2) {
                    g.addArch(new Arch(gene1.getName(), gene2.getName(), value1, value2, value3));
                    if (!g.getNodes().contains(gene1.getName())) {
                       g.addNode(gene1.getName());
                    }

                    if (!g.getNodes().contains(gene2.getName())) {
                       g.addNode(gene2.getName());
                    }
                 }
              }
           }
        }

        return g;
     }

    private static Graph addGRNrelations(Graph predominantNetwork, GenesData dg, double fAverage, int fThb ,double fKendall, double fSpearman, double fNMI) {
        GRN g = new GRN(predominantNetwork);
        double umbralCor = fAverage;
        int hubThr = fThb;
        if (hubThr < 0) {
           hubThr = 3;
        }

        IMathematicalMeasurement measure1 = new NMI();
        IMathematicalMeasurement measure2 = new Correlation(Constants.KENDALL);
        IMathematicalMeasurement measure3 = new Correlation(Constants.SPEARMAN);
        Hashtable<String, Node> nodes = predominantNetwork.getNodes();
        int k = 1;
        ArrayList<Gen> iList = new ArrayList(dg.getGenesList());
        int numTotal = dg.getGenesList().size();

        for(int i = 0; i < numTotal - 1; ++i) {
           Gen gene1 = (Gen)iList.get(i);
           if (nodes.containsKey(gene1.getName()) && ((Node)nodes.get(gene1.getName())).getExistingLinks() > hubThr) {
              for(int j = i + 1; j < numTotal; ++j) {
                 Gen gene2 = (Gen)iList.get(j);
                 int cont = 0;
                 if (!gene1.getName().equals(gene2.getName())) {
                    float value1 = Math.abs(measure1.genGenRelationship(gene1, gene2));
                    float value2 = Math.abs(measure2.genGenRelationship(gene1, gene2));
                    float value3 = Math.abs(measure3.genGenRelationship(gene1, gene2));
                    
                     if (value2 > fKendall) {
                     ++cont;
                     }

                     if (value3 > fSpearman) {
                     ++cont;
                     }

                     if (value1 >= fNMI) {
                     ++cont;
                     }

                     if (cont >= 2) { 
                        float average = (value1 + value2 + value3) / 3.0F;
                        if (average >= umbralCor) {
                           g.addArch(new Arch(gene1.getName(), gene2.getName(), average));
                           if (!g.getNodes().contains(gene2.getName())) {
                              g.addNode(gene2.getName());
                           }
                        }
                     }
                 }
              }
           }

           ++k;
        }

        return predominantNetwork;
     }

}
