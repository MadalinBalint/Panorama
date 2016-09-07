/**
 * Clasa FixedNumericDocument.java
 *
 * @author Balint Corneliu Madalin
 * @version 1.0.0 - 3 iulie 2011
 */

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class FixedNumericDocument extends PlainDocument {
    MyJTextField tf;
    int zecimale;

    public FixedNumericDocument(MyJTextField tf, int zecimale) {
        super();
        this.tf = tf;
        this.zecimale = zecimale;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        char c = str.charAt(0);

        /* Preprocesare: filtreaza caracterele care pot fi introduse */
        if (str.length() == 1) {
           String text = tf.getText();
           int point = text.indexOf(".");
           int caret = tf.getCaretPosition();
           boolean dupavirgula = (caret > point);

           if (point == -1) dupavirgula = false;

           switch(tf.tf_type) {
              case MyJTextField.TF_TYPE_DOUBLE:
                   if ((c == '.') || (c == ',')) {
                      if (point >= 0) {
                         if (!dupavirgula) {
                            String s = text.substring(0, caret);
                            String t = text.substring(point+1, text.length());
                            if (s.length() > 0) tf.setText(s+"."+t);
                         }
                         point = tf.getText().indexOf(".");
                         tf.setCaretPosition(point+1);
                      } else super.insertString(offset, ".", attr);
                   } else
                   if (Character.isDigit(c)) {
                      /* Atunci cand introducem semnul minus: '-' */
                      int minus = tf.getText().indexOf("-");
                      if (minus > -1) {
                         tf.setCaretPosition(1);
                         super.insertString(1, str, attr);
                      } //else

                      /* Cand suprascriem valoarea initiala si nu avem nici o zecimala introdusa dupa virgula */
                      if ((dupavirgula) && (text.length() - (point+1) < zecimale)) {
                         super.insertString(offset, str, attr);
                      } else
                      /* Cand suprascriem zecimalele deja introduse */
                      if ((dupavirgula) && (caret < text.length())) {
                         StringBuffer buf = new StringBuffer(text);
                         buf.setCharAt(caret, c);
                         tf.setText(buf.toString());
                         tf.setCaretPosition(caret+1);
                      } else
                      if (!dupavirgula) {
                         super.insertString(offset, str, attr);
                      }
                   }
                   break;
              case MyJTextField.TF_TYPE_INT:
              case MyJTextField.TF_TYPE_LONG:
                   if (Character.isDigit(c)) {
                      int minus = tf.getText().indexOf("-");
                      if (minus > -1) {
                         tf.setCaretPosition(1);
                         super.insertString(1, str, attr);
                      } else super.insertString(offset, str, attr);
                   }
                   break;
              case MyJTextField.TF_TYPE_TEXT:
                   super.insertString(offset, str, attr);
                   break;
              case MyJTextField.TF_TYPE_DATE:
                   if (caret < 10) {
                      if (Character.isDigit(c)) {
                         /* In caz ca textul nostru a fost selectat si apoi suprascris prin apasarea unei taste */
                         if (text.isEmpty()) {
                            tf.setText("00-00-0000");
                            tf.setCaretPosition(0);
                            text = tf.getText();
                            caret = tf.getCaretPosition();
                         } else
                         if (!text.isEmpty()) {
                            StringBuffer buf = new StringBuffer(text);
                            buf.setCharAt(caret, c);
                            tf.setText(buf.toString());
                            tf.setCaretPosition(caret+1);
                         }
                      }

                      caret = tf.getCaretPosition();
                      if ((caret == 2) || (caret == 5)) {
                         tf.setCaretPosition(caret+1);
                      }
                   }
                   break;
           }
        } else {
            super.insertString(offset, str, attr);
            return;
          }

        /* Procesarea propriu-zisa */
        switch(tf.tf_type) {
            case MyJTextField.TF_TYPE_DOUBLE:
                 if (c == '-') {
                    int pos = tf.getCaretPosition();
                    double d = -tf.getDoubleValue();
                    if (d != 0.0) {
                       if (d < 0) pos++; else pos--;
                       tf.setValue(d);
                       if (pos < 0) pos = 0;
                       tf.setCaretPosition(pos);
                    }
                 }
                 break;
            case MyJTextField.TF_TYPE_INT:
            case MyJTextField.TF_TYPE_LONG:
                 if (c == '-') {
                    int pos = tf.getCaretPosition();
                    int i = -tf.getIntValue();
                    if (i != 0) {
                       if (i < 0) pos++; else pos--;
                       tf.setValue(i);
                       tf.setCaretPosition(pos);
                    }
                 }
                 break;
            case MyJTextField.TF_TYPE_TEXT:
                 break;
            case MyJTextField.TF_TYPE_DATE:
                 break;
        }
    }

    public void remove(int offset, int len) throws BadLocationException {
        super.remove(offset, len);
        if (tf.tf_type == MyJTextField.TF_TYPE_DATE) {
           String s = "0";
           if ((offset == 2) || (offset == 5)) s = "-";
           if (len == 1) {
              super.insertString(offset, s, null);
              tf.setCaretPosition(offset);
           }
        }
    }
}