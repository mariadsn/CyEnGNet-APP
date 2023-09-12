package org.cytoscape.engnet.model.businessobjects.model;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Node {
   private String name;
   private ArrayList<Link> links;
   private int existingLinks;

   public ArrayList<Link> getLinks() {
      return this.links;
   }

   public Node(String newName) {
      this.name = newName;
      this.existingLinks = -1;
      this.links = new ArrayList();
   }

   public int getExistingLinks() {
      return this.existingLinks;
   }

   public String getName() {
      return this.name;
   }

   public void addLink(String toLink, double weight) {
      if (this.existingLinks == -1) {
         this.links.add(new Link(toLink, weight));
         ++this.existingLinks;
      } else {
         int position = this.thereLink(toLink);
         if (position == -1) {
            this.links.add(new Link(toLink, weight));
            ++this.existingLinks;
         }
      }

   }

   public int thereLink(String toLink) {
      for(int i = 0; i < this.links.size(); ++i) {
         Link myLink = (Link)this.links.get(i);
         if (myLink.getDestination().equals(toLink)) {
            return i;
         }
      }

      return -1;
   }

   public double linkPosition(int posi) {
      Link myLink = (Link)this.links.get(posi);
      return myLink.getWeight();
   }

   public String nodePosition(int posi) {
      Link myLink = (Link)this.links.get(posi);
      return myLink.getDestination();
   }

   boolean removeLink(int position) {
      if (position >= 0 && position <= this.links.size()) {
         this.links.remove(position);
         --this.existingLinks;
         return true;
      } else {
         JOptionPane.showMessageDialog((Component)null, "No link in position " + position);
         return false;
      }
   }
}
