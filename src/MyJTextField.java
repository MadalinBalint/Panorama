/**
 * Clasa MyJTextField.java
 *
 * @author Balint Corneliu Madalin
 * @version 1.0.0 - 3 iulie 2011
 */

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MyJTextField extends JTextField /*implements KeyListener*/ {
    public final static int TF_TYPE_DOUBLE = 1, /* Numar cu virgula: 0.00 */
                            TF_TYPE_INT = 2, /* Numar intreg fara virgula: 0 */
                            TF_TYPE_TEXT = 3, /* Text ce contine toate caracterele */
                            TF_TYPE_DATE = 4, /* Data calendaristica: zz-ll-aaaa */
                            TF_TYPE_TIME = 5, /* Timp: hh:mm:ss */
                            TF_TYPE_IP_ADRESS = 6, /* Adresa de IP: xxx.xxx.xxx.xxx */
                            TF_TYPE_BBD = 7,
                            TF_TYPE_LONG = 8,

                            CURRENT = 100, /* Valoarea curenta */
                            START = 101, /* Inceput: data calendaristica, ora */
                            END = 102; /* Sfarsit: data calendaristica, ora */
    public int tf_type = 0;
    public int zecimale = 0;

    public MyJTextField(Object obj, int type, int zecimale, int tip_data) {
        super();

        tf_type = type;
        this.zecimale = zecimale;

        String s = "";
        switch(type) {
            case TF_TYPE_DOUBLE:
                 if (obj == null) obj = 0.00;
                 s = new BigDecimal(Double.toString((Double)obj)).setScale(zecimale, RoundingMode.HALF_UP).toString();
                 setHorizontalAlignment(JTextField.RIGHT);
                 break;
            case TF_TYPE_INT:
                 if (obj == null) obj = 0;
                 s = Integer.toString((Integer)obj);
                 setHorizontalAlignment(JTextField.RIGHT);
                 break;
            case TF_TYPE_LONG:
                 if (obj == null) obj = 0;
                 s = Long.toString((Long)obj);
                 setHorizontalAlignment(JTextField.RIGHT);
                 break;
            case TF_TYPE_TEXT:
                 if (obj != null) s = (String)obj;
                 break;
            case TF_TYPE_DATE:
                 if (tip_data == CURRENT) {
                    s = DataLogger.getNowDate();
                 } else {
                     s = (String)obj;
                     if ((s == null) || (s.trim().compareTo("") == 0)) s = DataLogger.getNowDate();

                     int zi, luna, an;
                     zi = DataLogger.getDayDate(s);
                     luna = DataLogger.getMonthDate(s);
                     an = DataLogger.getYearDate(s);

                     if (tip_data == START) {
                        s = String.format("%1$02d-%2$02d-%3$04d", 1, luna, an);
                     } else
                     if (tip_data == END) {
                        zi = DataLogger.months[luna-1];
                        if (DataLogger.isBisect(an) && (luna == 2)) zi++;
                        s = String.format("%1$02d-%2$02d-%3$04d", zi, luna, an);
                     }
                   }
                 break;
            case TF_TYPE_TIME:
                 break;
            case TF_TYPE_IP_ADRESS:
                 break;
            case TF_TYPE_BBD:
                 break;
        }

        setDocument(new FixedNumericDocument(this, zecimale));
        setBorder(BorderFactory.createLoweredBevelBorder());
        setText(s);

        if (type == TF_TYPE_TEXT) setCaretPosition(0);
    }

    /* Setarea unor valori */
    public void setValue(double d) {
        String s = new BigDecimal(d).setScale(zecimale, RoundingMode.HALF_UP).toString();
        setText(s);
    }

    public void setValue(int i) {
        setText(Integer.toString(i));
    }

    /* Valabila atat pentru text cat si pentru date calendaristice */
    public void setValue(String s) {
        setText(s);
    }

    /* Obtinerea unor valori */
    public double getDoubleValue() {
        double d = 0;

        try { d = Double.valueOf(getText()); }
        catch (NumberFormatException e) { setValue(0.0); }

        return d;
    }

    public int getIntValue() {
        int i = 0;

        try { i = Integer.valueOf(getText()); }
        catch (NumberFormatException e) { setValue(0); }

        return i;
    }

    public long getLongValue() {
        long i = 0;

        try { i = Long.valueOf(getText()); }
        catch (NumberFormatException e) { setValue(0); }

        return i;
    }

    public String getDateValue() {
        String data = getText();
        String data_corecta = DataLogger.corecteazaNormalDate(data);

        if (data.compareToIgnoreCase(data_corecta) != 0) setText(data_corecta);

        return data_corecta;
    }

    public String getTimeValue() {
        String data = getText();

        return data;
    }
}