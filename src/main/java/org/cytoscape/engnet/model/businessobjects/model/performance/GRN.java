package org.cytoscape.engnet.model.businessobjects.model.performance;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

import org.cytoscape.engnet.model.businessobjects.model.Arch;
import org.cytoscape.engnet.model.businessobjects.model.Graph;

public class GRN {
   private ArrayList<String> nodes = new ArrayList();
   private ArrayList<Arch> Arches = new ArrayList();
   private static GRN instance = null;
   private double nmiValue;
   private double kendallValue;
   private double spearmanValue;

   public GRN() {
   }

   public GRN(Graph g) {
      this.nodes = g.getNames();
      this.Arches = g.getEdges();
   }

   public static GRN getInstance() {
      return instance == null ? new GRN() : instance;
   }

   public ArrayList<String> getNodes() {
      return this.nodes;
   }

   public void setNodes(ArrayList<String> nodes) {
      this.nodes = nodes;
   }

   public ArrayList<Arch> getArches() {
      return this.Arches;
   }

   public void setArches(ArrayList<Arch> Arches) {
      this.Arches = Arches;
   }
   
   public double getNMIValue() {
	   return this.nmiValue;
   }
   
   public void setNMIValue(double nmiValue) {
	   this.nmiValue = nmiValue;
   }
   
   public double getKendallValue() {
	   return this.kendallValue;
   }
   
   public void setKendallValue(double kendallValue) {
	   this.kendallValue = kendallValue;
   }
   public double getSpearmanValue() {
	   return this.spearmanValue;
   }
   
   public void setSpearmanValue(double spearmanValue) {
	   this.spearmanValue = spearmanValue;
   }

   public void addArches(HashSet<Arch> Arches) {
      this.Arches.addAll(Arches);
   }

   public void addNodes(HashSet<String> nodes) {
      this.nodes.addAll(nodes);
   }

   public void addArch(Arch a) {
      this.Arches.add(a);
   }

   public void dumpToFile(String path) throws IOException {
      System.out.println("Writing network file to File");
      System.out.println("File Path: " + path);
      System.out.println("Networks nodes: " + this.nodes.size());
      System.out.println("Networks arches: " + this.Arches.size());
      FileWriter filew = new FileWriter(path);
      PrintWriter pw = new PrintWriter(filew);
      Iterator<Arch> it = this.Arches.iterator();
      int j = 0;
      pw.println("Origin\tDestination\tWeight1\tWeight2\tWeight3");

      while(it.hasNext()) {
         Arch Arch = (Arch)it.next();
         pw.println(Arch.toStringESM());
         ++j;
      }

      pw.close();
   }

   public void dumpToNormalFile(String path) throws IOException {
      System.out.println("Writing network file to File");
      System.out.println("File Path: " + path);
      System.out.println("Networks nodes: " + this.nodes.size());
      System.out.println("Networks arches: " + this.Arches.size());
      FileWriter filew = new FileWriter(path);
      PrintWriter pw = new PrintWriter(filew);
      Iterator<Arch> it = this.Arches.iterator();
      int j = 0;
      pw.println("Origin\tDestination\tWeight1\tWeight2\tWeight3");

      while(it.hasNext()) {
         Arch Arch = (Arch)it.next();
         pw.println(Arch.toString());
         ++j;
      }

      pw.close();
   }

   public void graph2Tgf(String path) throws IOException {
      System.out.println("Writing file tgf");
      System.out.println("File Path: " + path);
      System.out.println("Networks nodes: " + this.nodes.size());
      System.out.println("Networks arches: " + this.Arches.size());
      TreeMap<String, Integer> aris = new TreeMap();
      FileWriter filew = new FileWriter(path);
      PrintWriter pw = new PrintWriter(filew);
      Iterator<String> it = this.nodes.iterator();

      for(int j = 0; it.hasNext(); ++j) {
         String gname = (String)it.next();
         pw.println(j + " " + gname);
         aris.put(gname, j);
      }

      pw.println("#");
      Iterator itA = this.Arches.iterator();

      while(itA.hasNext()) {
         Arch a = (Arch)itA.next();
         Integer ori = (Integer)aris.get(a.getInitial());
         Integer dest = (Integer)aris.get(a.getTerminal());
         pw.println(ori + " " + dest);
      }

      filew.close();
   }

   public void addNode(String node) {
      this.getNodes().add(node);
   }

   private boolean isPresent(Arch a) {
      Iterator it = this.getArches().iterator();

      while(it.hasNext()) {
         Arch a2 = (Arch)it.next();
         if (a2.equalPartnership(a)) {
            return true;
         }
      }

      return false;
   }

   public void removeDuplicatesEdges() {
      for(int i = 0; i < this.getArches().size() - 1; ++i) {
         Arch a1 = (Arch)this.getArches().get(i);

         for(int j = i + 1; j < this.getArches().size(); ++j) {
            Arch a2 = (Arch)this.getArches().get(j);
            if (a1.equalPartnership(a2)) {
               this.getArches().remove(j);
               System.out.println("edge " + a2.toString() + " removed");
            }
         }
      }

   }
}
