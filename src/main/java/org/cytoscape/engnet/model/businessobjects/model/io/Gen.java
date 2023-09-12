package org.cytoscape.engnet.model.businessobjects.model.io;

import java.util.ArrayList;

public class Gen {
   private String name;
   private ArrayList<Float> experiments;

   public Gen(String name) {
      this.name = name;
      this.experiments = new ArrayList();
   }

   public Gen(Gen g) {
      this.setExperiments(g.getExperiments());
      this.setName(g.getName());
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public ArrayList<Float> getExperiments() {
      return this.experiments;
   }

   public void setExperiments(ArrayList<Float> experiments) {
      this.experiments = experiments;
   }

   public void addExperiment(Float a) {
      this.getExperiments().add(a);
   }
}
