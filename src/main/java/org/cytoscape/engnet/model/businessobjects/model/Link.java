package org.cytoscape.engnet.model.businessobjects.model;

public class Link {
	   private String destination;
	   private double weight;

	   public Link(String desti, double weight1) {
	      this.destination = desti;
	      this.weight = weight1;
	   }

	   public Link(String desti) {
	      this.destination = desti;
	      this.weight = -1.0D;
	   }

	   public void modify(double weight1) {
	      this.weight = weight1;
	   }

	   public String getDestination() {
	      return this.destination;
	   }

	   public double getWeight() {
	      return this.weight;
	   }
	}
