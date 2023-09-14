package org.cytoscape.engnet.model.businessobjects.model.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

public class GenesData {
   private HashSet<Gen> genesList = new HashSet();
   private ArrayList<String> names = new ArrayList();

   public HashSet<Gen> getGenesList() {
      return this.genesList;
   }

   public void setGenesList(HashSet<Gen> genesList) {
      this.genesList = genesList;
   }

   public void addGen(Gen g) {
      this.genesList.add(g);
   }

   public int tamExperiments() {
      Iterator<Gen> it = this.getGenesList().iterator();
      Gen g = (Gen)it.next();
      return g.getExperiments().size();
   }

   public void readInputFile(String path) throws FichExceptions {
      String line = null;
      Gen g = null;

      try {
         FileReader file = new FileReader(path);
         BufferedReader br = new BufferedReader(file);
         String[] iList = null;
         line = br.readLine();

         for(int i = 0; line != null; ++i) {
            if (i != 0) {
               iList = line.split("\t");
               g = new Gen(iList[0]);
               this.names.add(iList[0]);

               for(int j = 1; j < iList.length; ++j) {
                  if (!iList[j].isEmpty() && !iList[j].equals(" ") && !Float.isNaN(new Float(iList[j]))) {
                     g.addExperiment(new Float(iList[j]));
                  }
               }

               this.addGen(g);
               System.out.println("Loading line: " + (i + 1));
            }

            line = br.readLine();
         }

      } catch (FileNotFoundException var9) {
         throw new FichExceptions(var9.getMessage());
      } catch (IOException var10) {
         throw new FichExceptions(var10.getMessage());
      } catch (Exception var11) {
         var11.printStackTrace();
         throw new FichExceptions(var11.getMessage());
      }
   }

   private void aboutEntry(String path) {
      String line = null;
      Object var5 = null;

      try {
         FileReader file = new FileReader(path);
         FileWriter filew = new FileWriter(path + "output.txt");
         PrintWriter pw = new PrintWriter(filew);
         BufferedReader br = new BufferedReader(file);
         String[] iList = null;
         line = br.readLine();

         for(int i = 0; line != null; ++i) {
            if (i == 0) {
               pw.println(line);
            } else {
               iList = line.split(",");
               pw.print(iList[0].substring(1, iList[0].indexOf(40)));

               for(int j = 1; j < iList.length; ++j) {
                  pw.print("\t" + iList[j]);
               }

               pw.print("\n");
               System.out.println("Loading line: " + (i + 1));
            }

            line = br.readLine();
         }

         br.close();
         pw.close();
      } catch (Exception var11) {
         var11.printStackTrace();
      }

   }

   public ArrayList<String> namesList() {
      return this.names;
   }
}