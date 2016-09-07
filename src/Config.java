/**
 * Clasa Config.java
 *
 * @author Balint Corneliu Madalin
 * @version 1.0.0 - 28 februarie 2011
 */

import java.util.ArrayList;

public class Config {
    int i, j;
    ArrayList<ArrayList<String>> date;
    ArrayList<String> v;

    public Config(ArrayList a) {
        date = a;
    }

    public String getData(String id) {
        String s = "";

        for (i = 0; i < date.size(); i++) {
            v = date.get(i);
            if (id.compareToIgnoreCase(v.get(0)) == 0) {
               s = v.get(1);
               break;
            }
        }

        return s;
	}

    public String getString(String id) {
        String s = "";

        for (i = 0; i < date.size(); i++) {
            v = date.get(i);
            if (id.compareToIgnoreCase(v.get(0)) == 0) {
               s = v.get(1);
               s = s.replaceAll("\"", "");
               break;
            }
        }

        return s;
	}

    public long getLong(String id) {
        long l;

        l = Long.valueOf(getString(id));

        return l;
	}

    public int getInt(String id) {
        int i;

        i = Integer.valueOf(getString(id));

        return i;
	}

    public double getDouble(String id) {
        double d;

        d = Double.valueOf(getString(id));

        return d;
	}
}