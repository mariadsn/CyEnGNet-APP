package org.cytoscape.engnet.model.businessobjects.MathematicalMeasurement;

import java.util.ArrayList;

import org.cytoscape.engnet.model.businessobjects.model.io.Gen;
import org.cytoscape.engnet.model.businessobjects.utils.Constants;

import jsc.correlation.KendallCorrelation;
import jsc.correlation.SpearmanCorrelation;
import jsc.datastructures.PairedData;

public class Correlation implements IMathematicalMeasurement {
   private String tipo;

   public Correlation(String tipo) {
      this.tipo = Constants.SPEARMAN;
      this.tipo = tipo;
   }

   public float genGenRelationship(Gen g1, Gen g2) {
      float value = 0.0F;
      if (this.tipo.equals(Constants.SPEARMAN)) {
         SpearmanCorrelation sp = new SpearmanCorrelation(new PairedData(this.getsGenesValues(g1.getExperiments()), this.getsGenesValues(g2.getExperiments())));
         value = (float)sp.getR();
      } else if (this.tipo.equals(Constants.KENDALL)) {
         KendallCorrelation kc = new KendallCorrelation(new PairedData(this.getsGenesValues(g1.getExperiments()), this.getsGenesValues(g2.getExperiments())));
         value = (float)kc.getR();
      }

      return value;
   }

   double[] getsGenesValues(ArrayList<Float> datos) {
      double[] exp = new double[datos.size()];

      for(int i = 0; i < datos.size(); ++i) {
         exp[i] = ((Float)datos.get(i)).doubleValue();
      }

      return exp;
   }
}
