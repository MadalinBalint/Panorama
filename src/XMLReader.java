/**
 * Clasa XMLReader.java
 *
 * @author Balint Corneliu Madalin
 * @version 1.0.0 - 6 martie 2011
 */

import javax.swing.*;
import java.io.*;
import java.util.*;

public class XMLReader {
	String nume_fisier;
	BufferedReader in;
	
	public XMLReader(String nume_fisier) {
		this.nume_fisier = nume_fisier;
	}
	
	public TagData searchTag(String nume_tag, String[] param) {
		TagData tag = null;
		int i, j, k;
		String t;
		StringBuffer s;
		boolean gasit_tag = false, stop_cautare = false;
        ArrayList temp;

		try {
          in = new BufferedReader(new FileReader(nume_fisier));
          while (in.ready()) {
          	 if (stop_cautare) break;
          	 s = new StringBuffer(in.readLine().trim());
         	 if (s.length() > 0) {
		   	    if (s.charAt(0) == '<') {
		   	       if (s.charAt(1) == '?'); else // info tip XML
		   	       if (s.charAt(1) == '!'); else // comentarii sau chestii DTD
		   	       if (s.charAt(1) == '/') { // sfarsitul unui TAG
		   	          j = s.lastIndexOf(">");
		   	          t = s.substring(2, j);

		   	          if ((t.compareToIgnoreCase(nume_tag) == 0) && (gasit_tag)) {
		   	          	 gasit_tag = false;
		   	          	 stop_cautare = true;
		   	          	 break;
		   	          }
		   	       } else {
		   	       	   i = s.indexOf(" ");
		   	       	   j = s.lastIndexOf("/");
		   	       	   k = s.lastIndexOf(">");
		   	       	   if (i > -1) { // TAG cu parametri
		   	       	      t = s.substring(i+1, j).trim();
		   	       	      if (gasit_tag) {
                             temp = proceseazaTag(t, param);
                             tag.date.add(temp);
		   	       	      }
		   	       	   } else { // TAG simplu de inceput
		   	       	   	   t = s.substring(1, k);
		   	       	   	   if (t.compareToIgnoreCase(nume_tag) == 0) {

		   	       	   	   	   gasit_tag = true;
		   	       	   	   	   tag = new TagData();
                               tag.nume = t;
		   	       	   	   }
		   	       	     }
		   	         }
		   	    }
         	 }   	
          }
          in.close();
        } 
        catch (FileNotFoundException e) {
        	JOptionPane.showMessageDialog(null, "Fisierul '"+nume_fisier+"' nu exista!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException e) {
        	JOptionPane.showMessageDialog(null, "I/O Error", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
        
        if (tag != null) {
           tag.parametri = param;
           return tag;
        }   
        
        return null;
	}

    public ArrayList extrageParametru(TagData date, String param) {
        int i, poz = -1; /* pozitie parametru */
        ArrayList v;
        String data_lista;

        /* Cautam pozitia parametrului nostru in lista de parametri a Tag-ului */
        for (i = 0; i < date.parametri.length; i++) {
            if (param.compareToIgnoreCase(date.parametri[i]) == 0) {
               poz = i;
               break;
            }
        }

        /* Daca nu exista acel parametru o lasam balta... */
        if (poz == -1) return null;

        ArboreBinar arbore = new ArboreBinar();

        for (i = 0; i < date.date.size() / 10; i++) {
            v = (ArrayList)date.date.get(i);
            data_lista = (String)v.get(poz);
            int id = DataLogger.getNumericXMLDate(data_lista);
            if (arbore.find(id) == null) arbore.insert(id, data_lista);
        }

        ArrayList lista = arbore.getListaData();
        Collections.sort(lista);

        return lista;
    }
	
	public ArrayList sumarTag() {
		int i, j, k, l=0;
		String t, nume_tag=null;
		StringBuffer s;
		ArrayList w, v;
		boolean gasit_tag = false;
		
		w = new ArrayList();
				
		try {
          in = new BufferedReader(new FileReader(nume_fisier));
          while (in.ready()) {
          	 s = new StringBuffer(in.readLine().trim());
         	 if (s.length() > 0) {
		   	    if (s.charAt(0) == '<') {
		   	       if (s.charAt(1) == '?'); else // info tip XML
		   	       if (s.charAt(1) == '!'); else // comentarii sau chestii DTD
		   	       if (s.charAt(1) == '/') { // sfarsitul unui TAG
		   	          j = s.lastIndexOf(">");
		   	          t = s.substring(2, j);
		   	          if ((t.compareToIgnoreCase(nume_tag) == 0) && (gasit_tag)) {
		   	          	 v = new ArrayList();
		   	          	 v.add(nume_tag);
		   	          	 v.add(l);
		   	          	 w.add(v);
		   	          	 System.out.println("Tag = "+nume_tag+" cu "+l+" articole.");
		   	          	 gasit_tag = false;
		   	          	 l = 0;
		   	          }
		   	       } else {
		   	       	   i = s.indexOf(" ");
		   	       	   j = s.lastIndexOf("/");
		   	       	   k = s.lastIndexOf(">");
		   	       	   if (i > -1) { // TAG cu parametri
		   	       	      t = s.substring(i+1, j).trim();
		   	       	      l++;
		   	       	   } else { // TAG simplu de inceput
		   	       	   	   t = s.substring(1, k);
		   	       	   	   gasit_tag = true;
		   	       	   	   nume_tag = t;
		   	       	   	   l = 0;
		   	       	     }
		   	         }
		   	    }
         	 }   	
          }
          in.close();
        } 
        catch (FileNotFoundException e) {
        	JOptionPane.showMessageDialog(null, "Fisierul '"+nume_fisier+"' nu exista!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException e) {
        	JOptionPane.showMessageDialog(null, "I/O Error", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
        
        return w;
	}
	
	public ArrayList proceseazaTag(String s, String[] param) {
		String u, v;
		StringBuffer t;
		int i, j, k;
		ArrayList sirparam, w;
		
		/* Aloc spatiul pentru fiecare informatie a TAG-ului */
		w = new ArrayList();
		sirparam = new ArrayList();
		t = new StringBuffer(s);
			
		do {
		  i = t.indexOf("=");
		  if (i == -1) break;
		  j = t.indexOf("\"");
		  k = t.indexOf("\"", (j+1));
			  
		  /* Determinam numele si valoarea parametrului, si-l adaugam la ArrayList */
		  u = t.substring(0, i);
		  v = t.substring(j+1, k);
		  
		  sirparam.add(u);
		  v = v.replaceAll(",", ".");
		  w.add(v.replaceAll(";", "."));
		  
		  t = new StringBuffer(t.substring(k+1).trim());
		} while (i > -1);
			
	    if (param != null) {
		   w = aranjeazaTag(w, sirparam, param);
		}

        u = null;
        v = null;
        t = null;
        sirparam = null;
		
		return w;
	}
	
	/* Fiecare inregistrare in parte poate avea un numar variabil de parametri */
	public ArrayList aranjeazaTag(ArrayList w, ArrayList p, String[] parametri_reali) {
		ArrayList v;
		int j, k;
		String s, t;
		boolean adaugat;
		
		v = new ArrayList();
			
		for (j = 0; j < parametri_reali.length; j++) {
			adaugat = false;
			s = parametri_reali[j];
			for (k = 0; k < p.size(); k++) {
				t = (String)p.get(k);
				if (s.compareToIgnoreCase(t) == 0) {
				   adaugat = true;
				   v.add(w.get(k));
				   break;
				}
			}
			if (!adaugat) v.add(null);
		}

        s = null;
        t = null;
		
		return v;
	}
	
	/* Cauta mai multe tag-uri dintr-un fisier XML si returneaza datele gata procesate 
	   Tag-urile sa fie asezate in ordinea aparitiei lor in fisierul XML */
	public ArrayList searchMultipleTags(String[] data, Object[][] param) {
		ArrayList tags_data = new ArrayList();
		
		for (int i = 0; i < data.length; i++) {
			System.out.println("Tag = "+data[i]);
			if (param == null) tags_data.add(searchTag(data[i], null)); else
			                   tags_data.add(searchTag(data[i], (String[])param[i]));
		}

		return tags_data;
	}
	
	/* Returneaze informatiile procesate de metoda 'searchMultipleTags' pentru un anume tag, luandu-ne dupa numele lui 
	   A se utiliza numai cu metoda de mai sus!!! */
	public TagData searchAfterTagName(String[] data, ArrayList tags_data, String tag) {
		for (int i = 0; i < data.length; i++) {
			if (data[i].compareToIgnoreCase(tag) == 0) 
			   return (TagData)tags_data.get(i);
		}
		
		return null;
	}
	
	static public int parametri(String s, char separator) {
		int k, j;
		String t;

        if (s.length() == 0) return 0;

		k = j = 0;
		t = s;
		do {
		   j = t.indexOf(separator);
		   t = new String(t.substring(j+1));
		   k +=1;
		} while (j != -1);

        t = null;

		return k;
	}
	
	static public String[] lista_parametri(String s, int nr, char separator) {
		int j, k;
		String t;
        
    	if (parametri(s, separator) != nr) return null;
		
		ArrayList temp = new ArrayList();
		for (k = 0; k < nr; k++) {
			j = s.indexOf(separator);
			if (j == -1) j = s.length();
			t = new String(s.substring(0, j));
			temp.add(t.trim());
			if (k < (nr-1)) s = new String(s.substring(j+1));
		}
		String[] lista = new String[nr];
		temp.toArray(lista);

        t = null;
        temp.clear();
        temp = null;
		
		return lista;
	}
	
	static public ArrayList lista_parametriArrayList(String s, int nr, char separator) {
		int j, k;
		String t;
        
    	if (parametri(s, separator) != nr) return null;
		
		ArrayList temp = new ArrayList();
		for (k = 0; k < nr; k++) {
			j = s.indexOf(separator);
			if (j == -1) j = s.length();
			t = new String(s.substring(0, j));
			temp.add(t.trim());
			if (k < (nr-1)) s = new String(s.substring(j+1));
		}

        t = null;
        temp.trimToSize();
		
		return temp;
	}

    static public Vector lista_parametriVector(String s, int nr, char separator) {
		int j, k;
		String t;

    	if (parametri(s, separator) != nr) return null;

		Vector temp = new Vector();
		for (k = 0; k < nr; k++) {
			j = s.indexOf(separator);
			if (j == -1) j = s.length();
			t = new String(s.substring(0, j));
			temp.add(t.trim());
			if (k < (nr-1)) s = new String(s.substring(j+1));
		}

        t = null;
        temp.trimToSize();

		return temp;
	}
}

class TagData {
    String nume;
	ArrayList date; /* datele corespunzatoare parametrilor ArrayList de ArrayList */
	String[] parametri; /* numele parametrilor reali */
    //boolean update;

	public TagData() {
		date = new ArrayList();
    }
}