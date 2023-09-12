package org.cytoscape.engnet.model.businessobjects.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.cytoscape.engnet.model.businessobjects.model.performance.GRN;

public class Graph {
   ArrayList<String> names;
   ArrayList<Arch> edges;
   Hashtable<String, Node> nodes;

   public Graph() {
      this.names = new ArrayList();
      this.nodes = new Hashtable();
      this.edges = new ArrayList();
   }

   public Graph(GRN grn) {
      this.names = new ArrayList(grn.getNodes());
      this.edges = grn.getArches();
      this.nodes = new Hashtable();
      Iterator it = grn.getNodes().iterator();

      while(it.hasNext()) {
         this.enterNode((String)it.next());
      }

   }

   public void enterNode(String name) {
      if (this.nodes.get(name) == null) {
         this.names.add(name);
         this.nodes.put(name, new Node(name));
      }

   }

   public void addLink(String nodeInitial, String nodeTerminal, float weight) {
      Arch oneNew = new Arch(nodeInitial, nodeTerminal, weight);
      int i = this.searchIndex(oneNew.getWeight());
      if (i == -1) {
         this.edges.add(oneNew);
      } else {
         this.edges.add(i, oneNew);
      }

      ((Node)this.nodes.get(nodeInitial)).addLink(nodeTerminal, (double)weight);
      ((Node)this.nodes.get(nodeTerminal)).addLink(nodeInitial, (double)weight);
   }

   public boolean findEdge(Arch arch) {
      for(int i = 0; i < this.edges.size(); ++i) {
         Arch another = (Arch)this.edges.get(i);
         if (arch.getInitial().equals(another.getInitial()) && arch.getTerminal().equals(another.getTerminal()) && arch.getWeight() == another.getWeight()) {
            this.edges.remove(another);
            return true;
         }
      }

      return false;
   }

   public boolean thereEdge(Arch arch) {
      for(int i = 0; i < this.edges.size(); ++i) {
         Arch another = (Arch)this.edges.get(i);
         if (arch.equalPartnership(another)) {
            return true;
         }
      }

      return false;
   }

   public int searchIndex(float weight) {
      for(int i = 0; i < this.edges.size(); ++i) {
         if (weight > ((Arch)this.edges.get(i)).getWeight()) {
            return i;
         }
      }

      return -1;
   }

   public Hashtable getNodes() {
      return this.nodes;
   }

   public void setNodes(Hashtable<String, Node> many) {
      this.nodes = many;
   }

   public ArrayList<String> getNames() {
      return this.names;
   }

   public Node getNode(String name) {
      return (Node)this.nodes.get(name);
   }

   public ArrayList<Arch> getEdges() {
      return this.edges;
   }

   public void setEdges(ArrayList<Arch> edges) {
      this.edges = edges;
   }

   public void setNames(ArrayList<String> names) {
      this.names = names;
   }

   public void dumpToFile(String path) throws IOException {
      System.out.println("Writing network into a file");
      System.out.println("File path: " + path);
      System.out.println("Number of genes of the network: " + this.nodes.size());
      System.out.println("Number of edges of the network: " + this.edges.size());
      FileWriter filew = new FileWriter(path);
      PrintWriter pw = new PrintWriter(filew);
      Iterator<Arch> it = this.edges.iterator();
      int j = 0;
      pw.println("Gene1\tGene2\tWeight");

      while(it.hasNext()) {
         Arch edge = (Arch)it.next();
         pw.println(edge.toString());
         ++j;
      }

      pw.close();
   }
}
