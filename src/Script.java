/**
 * Clasa Script.java
 *
 * @author Balint Corneliu Madalin
 * @version 1.0.0 - 24 iulie 2011
 */

import javax.swing.*;
import java.io.*;
import java.util.*;

public class Script {
    String nume_script;
	ArrayList<String> linii_fisier;
    ArrayList<Valori> script;
    BufferedReader in;
    int i, j;

	public Script(String name) {
        nume_script = name;
	}

    public boolean open() {
		linii_fisier = new ArrayList();
		try {
          in = new BufferedReader(new FileReader(nume_script));
          while (in.ready()) {
         	 String s = in.readLine();
         	 if (s != null) {
                s = s.trim();
                linii_fisier.add(s);
             }
          }
          in.close();
        }
        catch (FileNotFoundException e) {
            File f = new File(nume_script);
        	try { f.createNewFile(); }
        	catch (IOException ie) {
                JOptionPane.showMessageDialog(null, "I/O Error", "Eroare", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        }
        catch (IOException e) {
        	JOptionPane.showMessageDialog(null, "I/O Error", "Eroare", JOptionPane.ERROR_MESSAGE);
        	return false;
        }

        return true;
	}

    public void load() {
		String s, t = "";
        Valori c = null;

        script = new ArrayList();
		for (i = 0; i < linii_fisier.size(); i++) {
		   s = linii_fisier.get(i);
	   	   if (s.equals("") == true); else
		   if (s.charAt(0) == ';'); else
		   if (s.charAt(0) == '[') {
		      j = s.indexOf(']');
	          if (j == -1) t = s.substring(1); else t = s.substring(1, j);
              if (c == null) {
                 c = new Valori(t);
              } else {
                  script.add(c);
                  c = new Valori(t);
                }
		   } else {
               /* Adaugam informatiile */
               c.data.add(s);
             }
 		}

        /* La final */
        script.add(c);
 	}

    public ArrayList<ArrayList> process(String id, int param, char separator) {
        ArrayList a = new ArrayList();
        ArrayList temp;
        String s;
        for (i = 0; i < script.size(); i++) {
            Valori v = script.get(i);
            if (id.compareToIgnoreCase(v.nume) == 0) {
               for (j = 0; j < v.data.size(); j++) {
                   s = v.data.get(j);
                   temp = XMLReader.lista_parametriArrayList(s, param, separator);
                   a.add(temp);
               }
               break;
            }
        }

        return a;
    }

    public Vector<Vector> processV(String id, int param, char separator) {
        Vector a = new Vector();
        Vector temp;
        String s;
        for (i = 0; i < script.size(); i++) {
            Valori v = script.get(i);
            if (id.compareToIgnoreCase(v.nume) == 0) {
               for (j = 0; j < v.data.size(); j++) {
                   s = v.data.get(j);
                   temp = XMLReader.lista_parametriVector(s, param, separator);
                   a.add(temp);
               }
               break;
            }
        }

        return a;
    }
}

class Valori {
    String nume;
    ArrayList<String> data;

    Valori(String s) {
        nume = new String(s);
        data = new ArrayList<String>();
    }
}