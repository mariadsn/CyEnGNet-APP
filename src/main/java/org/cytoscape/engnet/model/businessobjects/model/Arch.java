package org.cytoscape.engnet.model.businessobjects.model;

public class Arch {
	   private String initial;
	   private String terminal;
	   private float weight;
	   private float weight1;
	   private float weight2;
	   private float weight3;

	   public Arch(String ini, String ter, float w) {
	      this.initial = ini;
	      this.terminal = ter;
	      this.weight = w;
	   }

	   public Arch(String ini, String ter, float w1, float w2, float w3) {
	      this.initial = ini;
	      this.terminal = ter;
	      this.weight1 = w1;
	      this.weight2 = w2;
	      this.weight3 = w3;
	      this.weight = (w1 + w2 + w3) / 3.0F;
	   }

	   public float getWeight() {
	      return this.weight;
	   }

	   public void setWeight(float weight) {
	      this.weight = weight;
	   }

	   public String getInitial() {
	      return this.initial;
	   }

	   public void setInitial(String initial) {
	      this.initial = initial;
	   }

	   public String getTerminal() {
	      return this.terminal;
	   }

	   public void setTerminal(String terminal) {
	      this.terminal = terminal;
	   }

	   public String toString() {
	      return this.initial + "\t" + this.terminal + "\t" + this.weight;
	   }

	   public String toStringESM() {
	      return this.initial + "\t" + this.terminal + "\t" + this.weight1 + "\t" + this.weight2 + "\t" + this.weight3;
	   }

	   public boolean equalPartnership(Arch a) {
	      return this.getInitial().equals(a.getInitial()) && this.getTerminal().equals(a.getTerminal()) || this.getInitial().equals(a.getTerminal()) && this.getTerminal().equals(a.getInitial());
	   }

	   public boolean equals(Object obj) {
	      Arch a = (Arch)obj;
	      return this.getInitial().equals(a.getInitial()) && this.getTerminal().equals(a.getTerminal()) || this.getInitial().equals(a.getTerminal()) && this.getTerminal().equals(a.getInitial());
	   }
}
