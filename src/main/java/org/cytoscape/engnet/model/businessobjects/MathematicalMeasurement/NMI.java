package org.cytoscape.engnet.model.businessobjects.MathematicalMeasurement;

import org.cytoscape.engnet.model.businessobjects.model.io.Gen;

import JavaMI.Entropy;
import JavaMI.MutualInformation;

public class NMI implements IMathematicalMeasurement {
   public float genGenRelationship(Gen g1, Gen g2) {
      int size = g1.getExperiments().size();
      float value = 0.0F;
      double[] ge1 = new double[size];
      double[] ge2 = new double[size];

      for(int i = 0; i < size; ++i) {
         ge1[i] = (double)(Float)g1.getExperiments().get(i);
         ge2[i] = (double)(Float)g2.getExperiments().get(i);
      }

      try {
         value = 2.0F * (float)MutualInformation.calculateMutualInformation(ge1, ge2) / ((float)Entropy.calculateEntropy(ge1) + (float)Entropy.calculateEntropy(ge2));
      } catch (Exception var8) {
         value = 0.0F;
      }

      return value;
   }
}
