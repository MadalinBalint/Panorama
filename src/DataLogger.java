/**
 * Clasa DataLogger.java
 *
 * @author Balint Corneliu Madalin
 * @version 1.0.0 - 15 iunie 2011
 */

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DataLogger {
    static byte TIP_STRING = 0, TIP_INTEGER = 1, TIP_DOUBLE = 2;
	String log_name;
	File fisier;
	FileWriter out;
	String newline;
	Frame frame;
    int latest_log_date;
    String last_log_date;

    static int[] months = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }; // duratele unei luni in anii non-bisecti
	
	public DataLogger(Frame frame) {
		this.frame = frame;
	    log_name = "Jurnal "+data_curenta()+".txt";
		newline = System.getProperty("line.separator");
	}
	


    /*
    *  FUNCTII PENTRU PRELUCRAREA OREI SI A DATEI, IN FORMAL LOCAL sau XML  
    */
	static public String getDate() {
		Date azi = new Date();
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.UK); 
		return df.format(azi);
	}
	
	static public String getTime() {
		Date azi = new Date();
        DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.UK);
		return df.format(azi);
	}

    /* Valoarea numerica a zilei curente */
	static public int getNumericNowDate() {
		int i, zi, luna = 0, an;

		String[] luni = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
		String s = getDate();
		String[] param = XMLReader.lista_parametri(s, 3, ' ');

        if (param == null) return 0;

		zi = Integer.parseInt(param[0]);
		for (i = 0; i < 12; i++) {
			if (param[1].compareToIgnoreCase(luni[i]) == 0) {
			   luna = i+1;
			   break;
			}
		}
		an = Integer.parseInt(param[2]);

		return (zi + luna*100 + an*10000);
	}

    static public int getNumericNowTime() {
		int ore, min, sec;

		String s = getTime();
		String[] param = XMLReader.lista_parametri(s, 3, ':');

        if (param == null) return 0;

		ore = Integer.parseInt(param[0]);
		min = Integer.parseInt(param[1]);
        sec = Integer.parseInt(param[2]);

		return (sec + min*100 + ore*10000);
	}

    static public String xmlDate(int zi, int luna, int an) {
        return String.format("%1$04d-%2$02d-%3$02d", an, luna, zi);
    }

    static public String normalDate(int zi, int luna, int an) {
        return String.format("%1$02d-%2$02d-%3$04d", zi, luna, an);
    }

    static public String numeric2Time(int ora, int min, int sec) {
        return String.format("%1$02d:%2$02d:%3$02d", ora, min, sec);
    }

    /* Valoarea in format XML a zilei curente */
    static public String getXMLNowDate() {
		int i, zi=0, luna=0, an=0;

		String[] luni = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
		String s = getDate();
		String[] param = XMLReader.lista_parametri(s, 3, ' ');

        if (param != null) {
		   zi = Integer.parseInt(param[0]);
		   for (i = 0; i < 12; i++) {
		       if (param[1].compareToIgnoreCase(luni[i]) == 0) {
			      luna = i+1;
			      break;
			   }
		   }
		   an = Integer.parseInt(param[2]);
        }

		return String.format("%1$04d-%2$02d-%3$02d", an, luna, zi);
	}

    static public String getNowDate() {
		int i, zi=0, luna=0, an=0;

		String[] luni = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
		String s = getDate();
		String[] param = XMLReader.lista_parametri(s, 3, ' ');

        if (param != null) {
		   zi = Integer.parseInt(param[0]);
		   for (i = 0; i < 12; i++) {
		  	   if (param[1].compareToIgnoreCase(luni[i]) == 0) {
			      luna = i+1;
			      break;
			   }
		   }
		   an = Integer.parseInt(param[2]);
        }

		return String.format("%1$02d-%2$02d-%3$04d", zi, luna, an);
	}

    /* Transforma o data normala intr-una in format XML */
	static public String normal2XML(String s) {
		int luna=0, an=0, zi=0;
        String[] param;

		param = XMLReader.lista_parametri(s, 3, '-');
        if (param == null) {
           param = XMLReader.lista_parametri(s, 3, '.');
        }

        if (param != null) {
           zi = Integer.parseInt(param[0]);
		   luna = Integer.parseInt(param[1]);
           an = Integer.parseInt(param[2]);
        }

        return String.format("%1$04d-%2$02d-%3$02d", an, luna, zi);
	}

    /* Transforma o data din format XML intr-una normala */
	static public String xml2Normal(String s) {
		int luna=0, an=0, zi=0;

		String[] param = XMLReader.lista_parametri(s, 3, '-');

        if (param != null) {
           an = Integer.parseInt(param[0]);
		   luna = Integer.parseInt(param[1]);
           zi = Integer.parseInt(param[2]);
        }

        return String.format("%1$02d-%2$02d-%3$04d", zi, luna, an);
	}

    /* Transforma o data din format XML intr-una de expirare */
	static public String xml2Expirare(String s) {
		int luna = 0, an = 0;

		String[] param = XMLReader.lista_parametri(s, 3, '-');
        if (param != null) {
		   an = Integer.parseInt(param[0]);
		   luna = Integer.parseInt(param[1]);
        }

		return String.format("%1$02d.%2$04d", luna, an);
	}

    /* Valoarea numerica a unei date in format XML */
	static public int getNumericXMLDate(String s) {
		int zi, luna, an;

		String[] param = XMLReader.lista_parametri(s, 3, '-');
        if (param == null) return 0;

        an = Integer.parseInt(param[0]);
		luna = Integer.parseInt(param[1]);
		zi = Integer.parseInt(param[2]);

		return (zi + luna*100 + an*10000);
	}

    /* Valoarea numerica a unei date in format normal */
	static public int getNumericDate(String s) {
		int zi, luna, an;

		String[] param = XMLReader.lista_parametri(s, 3, '-');
        if (param == null) return 0;

        an = Integer.parseInt(param[2]);
		luna = Integer.parseInt(param[1]);
		zi = Integer.parseInt(param[0]);

		return (zi + luna*100 + an*10000);
	}


    /* Valoarea anului dintr-o data in format XML */
	static public int getYearXMLDate(String s) {
		String[] param = XMLReader.lista_parametri(s, 3, '-');

        if (param == null) return 0;

        return Integer.parseInt(param[0]);
	}

    /* Valoarea lunii dintr-o data in format XML */
	static public int getMonthXMLDate(String s) {
		String[] param = XMLReader.lista_parametri(s, 3, '-');

        if (param == null) return 0;

		return Integer.parseInt(param[1]);
	}

    /* Valoarea zilei dintr-o data in format XML */
	static public int getDayXMLDate(String s) {
		String[] param = XMLReader.lista_parametri(s, 3, '-');

        if (param == null) return 0;

		return Integer.parseInt(param[2]);
	}

    /* Valoarea anului dintr-o data in format normal */
	static public int getYearDate(String s) {
		String[] param = XMLReader.lista_parametri(s, 3, '-');

        if (param == null) return 0;

        return Integer.parseInt(param[2]);
	}

    /* Valoarea lunii dintr-o data in format normal */
	static public int getMonthDate(String s) {
		String[] param = XMLReader.lista_parametri(s, 3, '-');

        if (param == null) return 0;

		return Integer.parseInt(param[1]);
	}

    /* Valoarea zilei dintr-o data in format normal */
	static public int getDayDate(String s) {
		String[] param = XMLReader.lista_parametri(s, 3, '-');

        if (param == null) return 0;

		return Integer.parseInt(param[0]);
	}

    /* Verifica corectitudinea datei de expirare: 'true' este buna, 'false' este gresita */
    static public boolean verificaDataExpirare(String s) {
        int luna_exp, an_exp, an;
        String[] param;

		param = XMLReader.lista_parametri(s, 2, '.');
        if (param == null) {
           param = XMLReader.lista_parametri(s, 2, '-');
        }

        if (param == null) return false;

        an_exp = Integer.parseInt(param[1]);
		luna_exp = Integer.parseInt(param[0]);
        an = getYearXMLDate(getXMLNowDate());

        return !((luna_exp < 1) || (luna_exp > 12) || (an_exp < an));

    }

    static public boolean verificaDataExpirareCompleta(String s) {
        int zi_exp, luna_exp, an_exp, an;
        String[] param;

		param = XMLReader.lista_parametri(s, 3, '.');
        if (param == null) {
           param = XMLReader.lista_parametri(s, 3, '-');
        }

        if (param == null) return false;

        zi_exp = Integer.parseInt(param[0]);
		luna_exp = Integer.parseInt(param[1]);
        an_exp = Integer.parseInt(param[2]);

        System.out.println(s+": "+zi_exp+"."+luna_exp+"."+an_exp);

        an = getYearXMLDate(getXMLNowDate());

        /* Produs 'expirat' */
        if (getNumericDate(s) < getNumericNowDate()) return false;

        if (isBisect(an_exp) && (luna_exp == 2)) {
           if (zi_exp > months[luna_exp-1]+1) return false;
        }

        return !((zi_exp < 1) || (zi_exp > months[luna_exp-1]) || (luna_exp < 1) || (luna_exp > 12) || (an_exp < an));
    }

    static public boolean isATC(String s) {
        if (s == null) return false;
        if (s.isEmpty() || (s.length() != 7)) return false;

        if (!Character.isLetter(s.charAt(0))) return false;
        if (!Character.isDigit(s.charAt(1))) return false;
        if (!Character.isDigit(s.charAt(2))) return false;
        if (!Character.isLetter(s.charAt(3))) return false;
        if (!Character.isLetter(s.charAt(4))) return false;
        if (!Character.isDigit(s.charAt(5))) return false;
        if (!Character.isDigit(s.charAt(6))) return false;

        return true;
    }

    static public String expirare2XMLDate(String s) {
        int zi = 0, luna = 0, an = 0;
        String[] param;

		param = XMLReader.lista_parametri(s, 2, '.');
        if (param == null) {
           param = XMLReader.lista_parametri(s, 2, '-');
        }

        if (param != null) {
           an = Integer.parseInt(param[1]);
		   luna = Integer.parseInt(param[0]);
           if (luna == 2) zi = 28; else zi = 30;
        }

        return String.format("%1$04d-%2$02d-%3$02d", an, luna, zi);
    }
	
	static public String timp(long time) {
		long ore, min, sec, temp;
		
		temp = time / 1000000000; /* numarul total de secunde */
        ore = temp / 3600;
        temp = temp % 3600;
		min = temp / 60;
		sec = temp % 60;
		
		return String.format("%1$02d:%2$02d:%3$02d", ore, min, sec);
	}

    static public String data_curenta() {
        Date today;
        SimpleDateFormat formatter;

        formatter = new SimpleDateFormat("dd.MM.yyyy");
        today = new Date();
        return formatter.format(today);
    }

    static public String ora_curenta() {
        Date today;
        SimpleDateFormat formatter;

        formatter = new SimpleDateFormat("HH:mm:ss");
        today = new Date();
        return formatter.format(today);
    }

    static public MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("Formatter is bad: " + exc.getMessage());
          }
        return formatter;
    }

    /* Calculeaza o suma de control si mai slaba pentru un String */
    static public long hashVeryWeak(String s) {
        int i;
        byte[] b;
        long hash = 0, x, z;

        if (s == null) return 0;
        b = s.getBytes();

        for (i = 0; i < b.length; i++) {
            x = b[i];
            z = i + 1l;
            hash += (x * z);
        }

        return hash;
    }

    /* Calculeaza o suma de control mai slaba pentru un String */
    static public long hashWeak(String s) {
        int i;
        byte[] b;
        long hash = 0, x, z;
        long power = 4;

        if (s == null) return 0;
        b = s.getBytes();

        for (i = 0; i < b.length; i++) {
            x = b[i];
            z = i + 1l;
            hash += (x * (z << (power - (x % power))));
        }

        return hash;
    }

    /* Calculeaza o suma de control mai puternica pentru un String */
    static public long hashStrong(String s) {
        int i;
        byte[] b;
        long hash = 0, x, z;
        long power = 4;

        if (s == null) return 0;
        b = s.getBytes();

        for (i = 0; i < b.length; i++) {
            x = b[i];
            z = (i + 1l) * (i + 1l);
            hash += (x * (z << (power - (x % power))));
        }

        return hash;
    }

    static public ArrayList convertArray(ArrayList<ArrayList> a, int[] types) {
        ArrayList c = new ArrayList(), v, temp;
        String s;
        int i, j, k;
        double d;

        if (a == null) return c; // returnam un ArrayList alocat dar gol

        for (i = 0; i < a.size(); i++) {
            temp = a.get(i);
            v = new ArrayList();
            for (j = 0; j < temp.size(); j++) {
                s = (String)temp.get(j);
                if (s == null) s = "";

                if (types[j] == TIP_STRING) {
                   v.add(s);
                } else
                if (types[j] == TIP_INTEGER) {
                   try { k = Integer.valueOf(s); }
                   catch (NumberFormatException ex) { k = 0; }
                   v.add(k);
                } else
                if (types[j] == TIP_DOUBLE) {
                   try { d = Double.valueOf(s); }
                   catch (NumberFormatException ex) { d = 0.0; }
                   v.add(d);
                } else v.add(s); // In caz ca e vreun fel de variabila extraterestra !!!
            }
            c.add(v);
        }

        return c;
    }

    public static int normalDate2days(String data, int an_referinta) {
        int i, days = 0;
        int luna=0, an=0, zi=0, start, end, sign = 1;
        boolean an_bisect = false;

		String[] param;

		param = XMLReader.lista_parametri(data, 3, '-');
        if (param == null) {
           param = XMLReader.lista_parametri(data, 3, '.');
        }

        if (param != null) {
           an = Integer.parseInt(param[2]);
		   luna = Integer.parseInt(param[1]);
           zi = Integer.parseInt(param[0]);

           if (an_referinta > an) {
              start = an;
              end = an_referinta;
              sign = -1;
           } else {
              start = an_referinta;
              end = an;
             }

           an_bisect = isBisect(an);

           /* Calculam cate zile s-au scurs pana la inceputul lunii curente... */
           for (i = 0; i < luna-1; i++) {
               days += months[i];
           }

           /* ...apoi adaugam zilele trecute din luna curenta */
           days += zi;

           /* Daca avem un an bisect si luna > februarie, mai adaugam o zi, pt. ca februarie in anii bisecti are 29 zile */ 
           if (an_bisect && (luna > 2)) {
              days++;
           }

           /* Adaugam si diferenta intre anul datei noastre si cel de referinta */ 
           for (i = start; i < end; i++) {
               if (isBisect(i)) days += 366; else days += 365;
           }
        }

        /* In caz ca se calculeaza o intoarcere in timp */
        return days * sign;
    }

    /* Adauga zilele de scadenta la o data, rezultand data scadenta corecta */
    public static String addScadenta2Date(String data, int scadenta) {
        int luna=0, an=0, zi=0, days, ani=0, bisect;
		String[] param;

		param = XMLReader.lista_parametri(data, 3, '-');
        if (param == null) {
           param = XMLReader.lista_parametri(data, 3, '.');
        }

        if (param != null) {
           /* Separam data noastra in componentele principale: zi-luna-an */
           an = Integer.parseInt(param[2]);
		   luna = Integer.parseInt(param[1]);
           zi = Integer.parseInt(param[0]);

           /* Transformam data noastra intr-un nr zile trecute de la inceputul anului respectiv din data */
           days = normalDate2days(data, an);

           System.out.println("Zile initiale = "+days);

           /* Adaugam zilele de scadenta */
           days += scadenta;

           /* Setam anul de inceput */
           ani = an;

           System.out.println("Zile finale = "+days+", an inceput = "+ani);

           do {
              if (((days < 366) && (isBisect(ani))) || ((days < 365) && (!isBisect(ani)))) break;

              if (isBisect(ani)) days -= 366; else days -= 365;
              ani++;
           } while (true);

           System.out.println("Ani finali = "+ani+", zile ramase = "+days); 

           luna = 1;

           do {
              if (isBisect(ani) && (luna == 2)) bisect = 1; else bisect = 0;

              if (days < months[luna-1] + bisect) break;
              days -= months[luna-1] + bisect;
              luna++;
           } while (true);

           System.out.println("Luna++ = "+luna);

           if (days == 0) {
              if (luna == 1) {
                 luna = 12;
                 ani--;
              } else luna--;
              days = months[luna-1];
           }

           zi = days;
        }

        return String.format("%1$02d-%2$02d-%3$04d", zi, luna, ani);
    }

    public static boolean isBisect(int an) {
        if (an % 400 == 0) {
           return true;
        } else
        if (an % 100 == 0) {
           return false; 
        } else
        if (an % 4 == 0) {
           return true; 
        }

        return false;
    }

    public static ArrayList generateDates(String start_xml, String end_xml) {
        ArrayList dates = new ArrayList();
        String s = start_xml;
        int start = getNumericXMLDate(start_xml);
        int end = getNumericXMLDate(end_xml);
        int zi = getDayXMLDate(s), luna = getMonthXMLDate(s), an = getYearXMLDate(s);
        int bisect = 0;

        /* Data de inceput nu poate fi mai mare decat cea de sfarsit */
        if (start > end) return dates;
        
        do {
          dates.add(s);
          zi++;

          /* Daca e an bisect mai adaugam o zi la luna februarie */
          if (isBisect(an)) {
             if (luna == 2) bisect = 1;
          } else bisect = 0;

          if ((months[luna-1]+bisect) < zi) { // a trecut luna
             zi = 1;
             luna++;
          }

          if (luna > 12) { // a trecut anul
             luna = 1;
             an++;
          }

          s = String.format("%1$04d-%2$02d-%3$02d", an, luna, zi);
          start = getNumericXMLDate(s);
        } while (start <= end);

        return dates;
    }

    /* Verifica corectitudinea datei calendaristice: doar date pozitive !!! */
    public static String corecteazaNormalDate(String data) {
        int luna=0, an=0, zi=0, bisect;
		String[] param;

		param = XMLReader.lista_parametri(data, 3, '-');
        if (param == null) {
           param = XMLReader.lista_parametri(data, 3, '.');
        }

        if (param != null) {
           /* Separam data noastra in componentele principale: zi-luna-an */
           an = Integer.parseInt(param[2]);
		   luna = Integer.parseInt(param[1]);
           zi = Integer.parseInt(param[0]);

           if (isBisect(an) && (luna == 2)) bisect = 1; else bisect = 0; /* In caz ca avem un an bisect si luna februarie */

           /* Verificam anul */
           if (an == 0) an = 1;

           /* Verificam luna */
           if (luna > 12) {
              luna = 12;
           } else
           if (luna == 0) {
              luna = 1;
           }

           /* Verificam ziua */
           if (zi > months[luna - 1]+bisect) {
              zi = months[luna - 1]+bisect;
           } else
           if (zi == 0) {
              zi = 1;
           }
        }

        return String.format("%1$02d-%2$02d-%3$04d", zi, luna, an);
    }
}