package org.cytoscape.engnet.model.businessobjects.logic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.cytoscape.engnet.model.businessobjects.MathematicalMeasurement.Correlation;
import org.cytoscape.engnet.model.businessobjects.MathematicalMeasurement.IMathematicalMeasurement;
import org.cytoscape.engnet.model.businessobjects.MathematicalMeasurement.MutualInformation;
import org.cytoscape.engnet.model.businessobjects.MathematicalMeasurement.SymmetricalUncertainty;
import org.cytoscape.engnet.model.businessobjects.model.Graph;
import org.cytoscape.engnet.model.businessobjects.model.io.GenesData;
import org.cytoscape.engnet.model.businessobjects.model.io.Gen;
import org.cytoscape.engnet.model.businessobjects.utils.Constants;

public class GeneratesCompleteNetwork {
   private static Graph g = new Graph();
   private static GeneratesCompleteNetwork instance = null;

   private GeneratesCompleteNetwork() {
   }

   public static GeneratesCompleteNetwork getInstance() {
      if (instance == null) {
         instance = new GeneratesCompleteNetwork();
      }

      return instance;
   }

   public Graph createNetwork() {
      return g;
   }

   public static Graph generateCompleteNetwork(int inf, int sup, int thread, GenesData dg, String property) {
      try {
         FileWriter filew = new FileWriter(Constants.PATH_ENTRADA + System.getProperty("file.separator") + "Log-" + thread + ".txt");
         PrintWriter pw = new PrintWriter(filew);
         IMathematicalMeasurement measure = null;
         if (!property.equals(Constants.KENDALL) && !property.equals(Constants.SPEARMAN)) {
            if (property.equals(Constants.MI)) {
               measure = new MutualInformation();
            } else if (property.equals(Constants.SU)) {
               measure = new SymmetricalUncertainty();
            }
         } else {
            measure = new Correlation(property);
         }

         int i = 1;
         int numTotal = sup - inf;
         ArrayList list = new ArrayList(dg.getGenesList());

         while(inf < sup) {
            Gen gene1 = (Gen)list.get(inf);
            System.out.println("Thread " + thread + " Trying node:" + gene1.getName() + " number: " + i + " of: " + numTotal);
            pw.println("Thread " + thread + " Trying node:" + gene1.getName() + " number: " + i + " of: " + numTotal);

            for(int k = inf + 1; k < list.size(); ++k) {
               Gen gene2 = (Gen)list.get(k);
               if (!gene1.getName().equals(gene2.getName())) {
                  float value = Math.abs(((IMathematicalMeasurement)measure).genGenRelationship(gene1, gene2));
                  synchronized(g) {
                     g.enterNode(gene1.getName());
                     g.enterNode(gene2.getName());
                     g.addLink(gene1.getName(), gene2.getName(), value);
                  }
               }
            }

            ++inf;
            ++i;
            pw.flush();
         }

         pw.close();
         return g;
      } catch (IOException var17) {
         var17.printStackTrace();
         return null;
      }
   }
}
