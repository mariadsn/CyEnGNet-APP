package org.cytoscape.engnet.model.businessobjects.model.performance;

public class Edge {
	   private String origin;
	   private String destination;
	   private float label;
	   private float mathValue;
	   private float bioValue;
	   private float NonDubiousDegree;
	   private double biologicalSimilitude = 0.0D;
	   private double biologicalDisimilitude = 0.0D;

	   public float getNonDubiousDegree() {
	      return this.NonDubiousDegree;
	   }

	   public void setNonDubiousDegree(float nonDubiousDegree) {
	      this.NonDubiousDegree = nonDubiousDegree;
	   }

	   public double getBiologicalSimilitude() {
	      return this.biologicalSimilitude;
	   }

	   public void setBiologicalSimilitude(double biologicalSimilitude) {
	      this.biologicalSimilitude = biologicalSimilitude;
	   }

	   public double getBiologicalDisimilitude() {
	      return this.biologicalDisimilitude;
	   }

	   public void setBiologicalDisimilitude(double biologicalDisimilitude) {
	      this.biologicalDisimilitude = biologicalDisimilitude;
	   }

	   public float getMathValue() {
	      return this.mathValue;
	   }

	   public void setMathValue(float mathValue) {
	      this.mathValue = mathValue;
	   }

	   public float getBioValue() {
	      return this.bioValue;
	   }

	   public void setBioValue(float bioValue) {
	      this.bioValue = bioValue;
	   }

	   public Edge(String name, String name2, float label) {
	      this.origin = name;
	      this.destination = name2;
	      this.label = label;
	   }

	   public float getLabel() {
	      return this.label;
	   }

	   public void setLabel(float label) {
	      this.label = label;
	   }

	   public String getOrigin() {
	      return this.origin;
	   }

	   public void setOrigin(String origin) {
	      this.origin = origin;
	   }

	   public String getDestination() {
	      return this.destination;
	   }

	   public void setDestination(String destination) {
	      this.destination = destination;
	   }

	   public boolean equals(Object obj) {
	      Edge a = (Edge)obj;
	      return (a.getOrigin().equals(this.getOrigin()) || a.getDestination().equals(this.getOrigin())) && (a.getOrigin().equals(this.getDestination()) || a.getDestination().equals(this.getDestination()));
	   }

	   public int hashCode() {
	      return 1;
	   }
}
