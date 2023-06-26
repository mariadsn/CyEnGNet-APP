package org.cytoscape.engnet.model.logic;

import java.util.ArrayList;
import java.util.Hashtable;
import java.io.File;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.cytoscape.engnet.model.businessobjects.EnGNetResult;
import org.cytoscape.engnet.model.businessobjects.exceptions.AnalysisErrorException;
import org.cytoscape.engnet.model.businessobjects.logic.AlgoritmoKruskal;
import org.cytoscape.engnet.model.businessobjects.medidaMatematica.Correlacion;
import org.cytoscape.engnet.model.businessobjects.medidaMatematica.IMedidaMatematica;
import org.cytoscape.engnet.model.businessobjects.medidaMatematica.NMI;
import org.cytoscape.engnet.model.businessobjects.model.Arco;
import org.cytoscape.engnet.model.businessobjects.model.Grafo;
import org.cytoscape.engnet.model.businessobjects.model.Nodo;
import org.cytoscape.engnet.model.businessobjects.model.io.DatosGenes;
import org.cytoscape.engnet.model.businessobjects.model.io.Gen;
import org.cytoscape.engnet.model.businessobjects.model.performance.GRN;
import org.cytoscape.engnet.model.businessobjects.utils.Constantes;
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

    public EnGNetResult execute(File sPath, String sPathEntrada, double fNMI, double fKendall, double fSpearman, double fAverage, int fThb) {

        try {
        	// 1) Execute EnGNet
        	DatosGenes dg = new DatosGenes();
        	dg.leerFicheroDeEntrada(sPathEntrada);
        	GRN g = generarGrafoCompleto(dg, fKendall, fSpearman, fNMI);
        	ArrayList<Gen> genes = new ArrayList(dg.getListaGenes());
        	g.volcarAFichero(sPath + System.getProperty("file.separator") + "grafoCompleto.txt");
        	Grafo gc = new Grafo(g);
        	Grafo grafoPredominante = AlgoritmoKruskal.aplicarKruskal(gc);
        	Grafo redFinal = anadirRelacionesGRN(grafoPredominante, dg, fAverage, fThb, fKendall, fSpearman, fNMI);
        	//ArrayList<Gen> genes = new ArrayList(redFinal.getNodos());
        	redFinal.volcarAFichero(sPath + System.getProperty("file.separator") + "finalNetwork.txt");    	
        	
        	
		return new EnGNetResult(fNMI,fKendall,fSpearman, fAverage,fThb,redFinal,genes,g);
        } catch (AnalysisErrorException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AnalysisErrorException(ex);
        } finally {
            pool.shutdownNow();
        }
    }

    private static GRN generarGrafoCompleto(DatosGenes dg, double fKendall, double fSpearman, double fNMI) {
        GRN g = GRN.getInstance();
        IMedidaMatematica medida3 = null;
        IMedidaMatematica medida1 = new NMI();
        IMedidaMatematica medida2 = new Correlacion(Constantes.KENDALL);
        medida3 = new Correlacion(Constantes.SPEARMAN);
        int numTotal = dg.getListaGenes().size();
        ArrayList<Gen> lista = new ArrayList(dg.getListaGenes());

        for(int i = 0; i < numTotal - 1; ++i) {
           Gen gene1 = (Gen)lista.get(i);
           if (i % 1000 == 0) {
        	   //CySwing2.displayPopUpMessage("Gene:" + gene1.getNombre() + " number: " + i + " of: " + numTotal);
           }

           for(int j = i + 1; j < numTotal; ++j) {
              Gen gene2 = (Gen)lista.get(j);
              int cont = 0;
              if (!gene1.getNombre().equals(gene2.getNombre())) {
                 float valor1 = Math.abs(medida1.relacionGenGen(gene1, gene2));
                 float valor2 = Math.abs(medida2.relacionGenGen(gene1, gene2));
                 float valor3 = Math.abs(medida3.relacionGenGen(gene1, gene2));
                 if (valor2 > fKendall) {
                    ++cont;
                 }

                 if (valor3 > fSpearman) {
                    ++cont;
                 }

                 if (valor1 >= fNMI) {
                    ++cont;
                 }

                 if (cont >= 2) {
                    g.addArco(new Arco(gene1.getNombre(), gene2.getNombre(), valor1, valor2, valor3));
                    if (!g.getNodos().contains(gene1.getNombre())) {
                       g.addNodo(gene1.getNombre());
                    }

                    if (!g.getNodos().contains(gene2.getNombre())) {
                       g.addNodo(gene2.getNombre());
                    }
                 }
              }
           }
        }

        return g;
     }

    private static Grafo anadirRelacionesGRN(Grafo grafoPredominante, DatosGenes dg, double fAverage, int fThb ,double fKendall, double fSpearman, double fNMI) {
        GRN g = new GRN(grafoPredominante);
        double umbralCor = fAverage;
        int hubThr = fThb;
        if (hubThr < 0) {
           hubThr = 3;
        }

        IMedidaMatematica medida1 = new NMI();
        IMedidaMatematica medida2 = new Correlacion(Constantes.KENDALL);
        IMedidaMatematica medida3 = new Correlacion(Constantes.SPEARMAN);
        Hashtable<String, Nodo> nodos = grafoPredominante.getNodos();
        int k = 1;
        ArrayList<Gen> lista = new ArrayList(dg.getListaGenes());
        int numTotal = dg.getListaGenes().size();

        for(int i = 0; i < numTotal - 1; ++i) {
           Gen gene1 = (Gen)lista.get(i);
           if (nodos.containsKey(gene1.getNombre()) && ((Nodo)nodos.get(gene1.getNombre())).getEnlacesExistentes() > hubThr) {
        	   //CySwing2.displayPopUpMessage("Adding relations to relevant node " + k + " : " + gene1.getNombre());

              for(int j = i + 1; j < numTotal; ++j) {
                 Gen gene2 = (Gen)lista.get(j);
                 int cont = 0;
                 if (!gene1.getNombre().equals(gene2.getNombre())) {
                    float valor1 = Math.abs(medida1.relacionGenGen(gene1, gene2));
                    float valor2 = Math.abs(medida2.relacionGenGen(gene1, gene2));
                    float valor3 = Math.abs(medida3.relacionGenGen(gene1, gene2));
                    
                     if (valor2 > fKendall) {
                     ++cont;
                     }

                     if (valor3 > fSpearman) {
                     ++cont;
                     }

                     if (valor1 >= fNMI) {
                     ++cont;
                     }

                     if (cont >= 2) { 
                        float media = (valor1 + valor2 + valor3) / 3.0F;
                        if (media >= umbralCor) {
                           g.addArco(new Arco(gene1.getNombre(), gene2.getNombre(), media));
                           if (!g.getNodos().contains(gene2.getNombre())) {
                              g.addNodo(gene2.getNombre());
                           }
                        }
                     }
                 }
              }
           }

           ++k;
        }

        return grafoPredominante;
     }

}
