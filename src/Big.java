/**
 * Clasa Big.java
 *
 * @author Balint Corneliu Madalin
 * @version 1.0.0 - 30 august 2011
 */

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Big {
    /* Operatii cu BigDecimal cu 2 zecimale dupa virgula */
    public static BigDecimal big(String numar) {
        BigDecimal valoare = new BigDecimal(numar);
        return valoare.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal big(BigDecimal numar) {
        return numar.setScale(2, RoundingMode.HALF_UP);
    }

    /* Operatii cu BigDecimal cu 6 zecimale dupa virgula */
    public static BigDecimal big6(String numar) {
        BigDecimal valoare = new BigDecimal(numar);
        return valoare.setScale(6, RoundingMode.HALF_UP);
    }

    public static BigDecimal big6(BigDecimal numar) {
        return numar.setScale(6, RoundingMode.HALF_UP);
    }

    /* Adunare */
    public static BigDecimal add(String a, String b) {
        BigDecimal big_a = big(a);
        BigDecimal big_b = big(b);
        return big_a.add(big_b);
    }

    public static BigDecimal add(double a, double b) {
        BigDecimal big_a = new BigDecimal(a).setScale(2, RoundingMode.HALF_UP);
        BigDecimal big_b = new BigDecimal(b).setScale(2, RoundingMode.HALF_UP);
        return big_a.add(big_b);
    }

    public static BigDecimal add(double a, double b, double c) {
        BigDecimal big_a = big(Double.toString(a));
        BigDecimal big_b = big(Double.toString(b));
        BigDecimal big_c = big(Double.toString(c));
        return big_a.add(big_b).add(big_c);
    }

    /* Scadere */
    public static BigDecimal sub(double a, double b) {
        BigDecimal big_a = new BigDecimal(a).setScale(2, RoundingMode.HALF_UP);//big6(Double.toString(a));
        BigDecimal big_b = new BigDecimal(b).setScale(2, RoundingMode.HALF_UP);//big6(Double.toString(b));
        return big_a.subtract(big_b);
    }

    public static BigDecimal sub(double a, double b, double c) {
        BigDecimal big_a = big(Double.toString(a));
        BigDecimal big_b = big(Double.toString(b));
        BigDecimal big_c = big(Double.toString(c));
        return big_a.subtract(big_b).subtract(big_c);
    }

    public static BigDecimal mul(String a, String b) {
        BigDecimal big_a = new BigDecimal(a).setScale(2, RoundingMode.HALF_UP);//big6(Double.toString(a));
        BigDecimal big_b = new BigDecimal(b).setScale(2, RoundingMode.HALF_UP);//big6(Double.toString(b));
        return big_a.multiply(big_b);
    }

    /* Inmultire */
    public static BigDecimal mul(double a, double b) {
        BigDecimal big_a = new BigDecimal(a).setScale(2, RoundingMode.HALF_UP);//big6(Double.toString(a));
        BigDecimal big_b = new BigDecimal(b).setScale(2, RoundingMode.HALF_UP);//big6(Double.toString(b));
        return big_a.multiply(big_b);
    }

    public static BigDecimal mul(double a, double b, double c) {
        BigDecimal big_a = new BigDecimal(a).setScale(2, RoundingMode.HALF_UP);//big6(Double.toString(a));
        BigDecimal big_b = new BigDecimal(b).setScale(2, RoundingMode.HALF_UP);//big6(Double.toString(b));
        BigDecimal big_c = new BigDecimal(c).setScale(2, RoundingMode.HALF_UP);//big6(Double.toString(c));
        return big_a.multiply(big_b).multiply(big_c);
    }

    /* Impartire */
    public static BigDecimal div(double a, double b) {
        BigDecimal big_a = new BigDecimal(a).setScale(2, RoundingMode.HALF_UP);//big6(Double.toString(a));
        BigDecimal big_b = new BigDecimal(b).setScale(2, RoundingMode.HALF_UP);//big6(Double.toString(b));
        return big_a.divide(big_b, 2, RoundingMode.HALF_UP);
    }

    /* Adunare cu 6 zecimale */
    public static BigDecimal add6(double a, double b) {
        BigDecimal big_a = new BigDecimal(a).setScale(6, RoundingMode.HALF_UP);//big6(Double.toString(a));
        BigDecimal big_b = new BigDecimal(b).setScale(6, RoundingMode.HALF_UP);//big6(Double.toString(b));
        return big_a.add(big_b);
    }

    public static BigDecimal add6(String a, String b) {
        BigDecimal big_a = big6(a);
        BigDecimal big_b = big6(b);
        return big_a.add(big_b);
    }

    public static BigDecimal add6(double a, double b, double c) {
        BigDecimal big_a = big6(Double.toString(a));
        BigDecimal big_b = big6(Double.toString(b));
        BigDecimal big_c = big6(Double.toString(c));
        return big_a.add(big_b).add(big_c);
    }

    public static BigDecimal add6(String a, String b, String c) {
        BigDecimal big_a = big6(a);
        BigDecimal big_b = big6(b);
        BigDecimal big_c = big6(c);
        return big_a.add(big_b).add(big_c);
    }

    /* Scadere cu 6 zecimale */
    public static BigDecimal sub6(double a, double b) {
        BigDecimal big_a = new BigDecimal(a).setScale(6, RoundingMode.HALF_UP);//big6(Double.toString(a));
        BigDecimal big_b = new BigDecimal(b).setScale(6, RoundingMode.HALF_UP);//big6(Double.toString(b));
        return big_a.subtract(big_b);
    }

    public static BigDecimal sub6(String a, String b) {
        BigDecimal big_a = new BigDecimal(a).setScale(6, RoundingMode.HALF_UP);//big6(Double.toString(a));
        BigDecimal big_b = new BigDecimal(b).setScale(6, RoundingMode.HALF_UP);//big6(Double.toString(b));
        return big_a.subtract(big_b);
    }

    public static BigDecimal sub6(double a, double b, double c) {
        BigDecimal big_a = big6(Double.toString(a));
        BigDecimal big_b = big6(Double.toString(b));
        BigDecimal big_c = big6(Double.toString(c));
        return big_a.subtract(big_b).subtract(big_c);
    }

    /* Inmultire cu 6 zecimale */
    public static BigDecimal mul6(double a, double b) {
        BigDecimal big_a = new BigDecimal(a).setScale(6, RoundingMode.HALF_UP);//big6(Double.toString(a));
        BigDecimal big_b = new BigDecimal(b).setScale(6, RoundingMode.HALF_UP);//big6(Double.toString(b));
        return big_a.multiply(big_b);
    }

    public static BigDecimal mul6(String a, String b) {
        BigDecimal big_a = new BigDecimal(a).setScale(6, RoundingMode.HALF_UP);//big6(Double.toString(a));
        BigDecimal big_b = new BigDecimal(b).setScale(6, RoundingMode.HALF_UP);//big6(Double.toString(b));
        return big_a.multiply(big_b);
    }

    public static BigDecimal mul6(double a, double b, double c) {
        BigDecimal big_a = big6(Double.toString(a));
        BigDecimal big_b = big6(Double.toString(b));
        BigDecimal big_c = big6(Double.toString(c));
        return big_a.multiply(big_b).multiply(big_c);
    }

    public static BigDecimal mul6(String a, String b, String c) {
        BigDecimal big_a = big6(a);
        BigDecimal big_b = big6(b);
        BigDecimal big_c = big6(c);
        return big_a.multiply(big_b).multiply(big_c);
    }

    /* Impartire cu 6 zecimale */
    public static BigDecimal div6(double a, double b) {
        BigDecimal big_a = new BigDecimal(a).setScale(6, RoundingMode.HALF_UP);//big6(Double.toString(a));
        BigDecimal big_b = new BigDecimal(b).setScale(6, RoundingMode.HALF_UP);//big6(Double.toString(b));
        return big_a.divide(big_b, 6, RoundingMode.HALF_UP);
    }

    public static BigDecimal div6(String a, String b) {
        BigDecimal big_a = new BigDecimal(a).setScale(6, RoundingMode.HALF_UP);//big6(Double.toString(a));
        BigDecimal big_b = new BigDecimal(b).setScale(6, RoundingMode.HALF_UP);//big6(Double.toString(b));
        return big_a.divide(big_b, 6, RoundingMode.HALF_UP);
    }

    public static BigDecimal div6(double a, double b, double c) {
        BigDecimal big_a = big6(Double.toString(a));
        BigDecimal big_b = big6(Double.toString(b));
        BigDecimal big_c = big6(Double.toString(c));
        BigDecimal rez = big_a.divide(big_b, 6, RoundingMode.HALF_UP);
        return rez.divide(big_c, 6, RoundingMode.HALF_UP);
    }

    public static BigDecimal div6(String a, String b, String c) {
        BigDecimal big_a = big6(a);
        BigDecimal big_b = big6(b);
        BigDecimal big_c = big6(c);
        BigDecimal rez = big_a.divide(big_b, 6, RoundingMode.HALF_UP);
        return rez.divide(big_c, 6, RoundingMode.HALF_UP);
    }

    /* Calcul taxa: TVA 24% -> 1.24 */
    public static BigDecimal tax6(double a) {
        BigDecimal big_a = big6(Double.toString(a));
        BigDecimal big_b = big_a.add(big6("100.0"));
        return big_b.divide(big6("100.0"), 6, RoundingMode.HALF_UP);
    }

    public static BigDecimal tax6(String a) {
        BigDecimal big_a = big6(a);
        BigDecimal big_b = big_a.add(big6("100.0"));
        return big_b.divide(big6("100.0"), 6, RoundingMode.HALF_UP);
    }

    /* Calcul discount: Reducere 19% -> 0.81 */
    public static BigDecimal disc6(double a) {
        BigDecimal big_a = sub6(100, a);
        return big_a.divide(big6("100.0"), 6, RoundingMode.HALF_UP);
    }

    public static BigDecimal disc6(String a) {
        BigDecimal big_a = sub6("100.0", a);
        return big_a.divide(big6("100.0"), 6, RoundingMode.HALF_UP);
    }

    /* Transforma val adaos in adaos procentual: Adaos 1.2345 -> 23.45% */
    public static BigDecimal adaos_proc(BigDecimal a) {
        return a.subtract(big6("1")).multiply(big6("100.0"));
    }

    /* Returneaza valoarea cu 2 zecimale dupa virgula in format 'String' */
    public static String z2(double a) {
        BigDecimal x = new BigDecimal(a);
        x = x.setScale(2, RoundingMode.HALF_UP);
        return x.toString();
    }
}