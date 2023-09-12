package org.cytoscape.engnet.model.businessobjects.MathematicalMeasurement;

import org.cytoscape.engnet.model.businessobjects.model.io.Gen;

public class MutualInformation implements IMathematicalMeasurement {
   public float genGenRelationship(Gen g1, Gen g2) {
      int size = g1.getExperiments().size();
      double[] ge1 = new double[size];
      double[] ge2 = new double[size];

      for(int i = 0; i < size; ++i) {
         ge1[i] = (double)(Float)g1.getExperiments().get(i);
         ge2[i] = (double)(Float)g2.getExperiments().get(i);
      }

      float value = (float)JavaMI.MutualInformation.calculateMutualInformation(ge1, ge2);
      return value;
   }
}
