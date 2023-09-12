package org.cytoscape.engnet.model.businessobjects.logic;

import java.util.ArrayList;

import org.cytoscape.engnet.model.businessobjects.model.Arch;
import org.cytoscape.engnet.model.businessobjects.model.Link;
import org.cytoscape.engnet.model.businessobjects.model.Graph;
import org.cytoscape.engnet.model.businessobjects.model.Node;

public class KruskalAlgorithm {
   public static Graph applyKruskal(Graph graph) {
      Graph tree = new Graph();
      ArrayList<String> nodes = graph.getNames();

      for(int j = 0; j < nodes.size(); ++j) {
         tree.enterNode((String)nodes.get(j));
      }

      ArrayList<Arch> L = (ArrayList)graph.getEdges().clone();
      Arch pro = (Arch)L.get(0);
      tree.addLink(pro.getInitial(), pro.getTerminal(), pro.getWeight());
      L.remove(pro);

      for(int i = 0; L.size() != 0; ++i) {
         if (i % 250 == 0) {
            System.out.println("Working on cycle " + i + " of " + L.size());
         }

         pro = (Arch)L.get(0);
         if (!ThereCycle(tree, pro, tree.getNode(pro.getTerminal()), pro.getTerminal())) {
            tree.addLink(pro.getInitial(), pro.getTerminal(), pro.getWeight());
         }

         L.remove(pro);
      }

      return tree;
   }

   public static boolean ThereCycle(Graph g, Arch toVerify, Node terminal, String N) {
      ArrayList<Link> aux = terminal.getLinks();
      if (aux.size() == 0) {
         return false;
      } else if (terminal.thereLink(toVerify.getInitial()) != -1) {
         return true;
      } else {
         for(int i = 0; i < aux.size(); ++i) {
            Link node = (Link)aux.get(i);
            if (!node.getDestination().equals(N) && ThereCycle(g, toVerify, g.getNode(node.getDestination()), terminal.getName())) {
               return true;
            }
         }

         return false;
      }
   }
}
