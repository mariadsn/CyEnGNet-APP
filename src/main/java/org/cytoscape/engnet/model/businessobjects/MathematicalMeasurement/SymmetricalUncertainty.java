package org.cytoscape.engnet.model.businessobjects.MathematicalMeasurement;

import org.cytoscape.engnet.model.businessobjects.model.io.Gen;

import JavaMI.Entropy;

public class SymmetricalUncertainty implements IMathematicalMeasurement {
   public float genGenRelationship(Gen g1, Gen g2) {
      int size = g1.getExperiments().size();
      double[] ge1 = new double[size];
      double[] ge2 = new double[size];

      for(int i = 0; i < size; ++i) {
         ge1[i] = (double)(Float)g1.getExperiments().get(i);
         ge2[i] = (double)(Float)g2.getExperiments().get(i);
      }

      float Hx = (float)Entropy.calculateEntropy(ge1);
      float Hy = (float)Entropy.calculateEntropy(ge2);
      float Hxy = (float)Entropy.calculateConditionalEntropy(ge1, ge2);
      float su = 2.0F * Math.abs((Hx - Hxy) / (Hx + Hy));
      return su;
   }
}
