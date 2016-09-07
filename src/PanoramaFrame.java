/**
 * Clasa PharmaFrame.java
 *
 * @author Balint Corneliu Madalin
 * @version 1.0.0 - 22 septembrie 2011
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;
import java.util.Vector;

public class PanoramaFrame extends JFrame {
    int margin = 50, button_w = 80, button_h = 25, film35mm = 1872;
    Dimension dim;
    int width = 650, height = 400;
    JPanel panel;
	JButton bOk, bCancel;
    DefaultComboBoxModel model;
    JComboBox cb1, cb2;
    MyJTextField tf1, tf2, tf3, tf4;
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12;
    Vector producatori, aparate;
    Vector<Vector> date_aparate;
    InputMap inputMap;
    ActionMap actionMap;
    Script script;
    int i;

	public PanoramaFrame() {
        dim = getToolkit().getScreenSize();

        panel = new JPanel();
		panel.setLayout(null);
        panel.setBorder(null);

        inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        actionMap = getRootPane().getActionMap();

        script = new Script("sensor.txt");
        if (script.open()) {
           script.load();
        } else {
            JOptionPane.showMessageDialog(null, "Fisier date aparate inexistent!!!", "Eroare", JOptionPane.ERROR_MESSAGE);
            dispose();
          }

        producatori = new Vector();
        aparate = new Vector();

        for (i = 0; i < script.script.size(); i++) {
            Valori v = script.script.get(i);
            producatori.add(v.nume);
        }

        date_aparate = getSensorList((String) producatori.get(0));
        aparate = getAparate(date_aparate);

        l1 = new JLabel("Producator: ");
        l1.setBounds(10, 20, 100, button_h);
        panel.add(l1);

        cb1 = new JComboBox(producatori);
        cb1.setBounds(85, 20, 100, button_h);
        cb1.setBorder(BorderFactory.createLoweredBevelBorder());
        cb1.setForeground(Color.BLACK);
        cb1.setBackground(Color.WHITE);
        cb1.setSelectedIndex(-1);
        panel.add(cb1);

        cb1.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                }
            }
            public void keyReleased(KeyEvent e) {}
            public void keyTyped(KeyEvent e) {}
        });

        cb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int idx = cb1.getSelectedIndex();
                date_aparate = getSensorList((String) producatori.get(idx));
                aparate = getAparate(date_aparate);
                model = new DefaultComboBoxModel(aparate);
                cb2.setModel(model);
                cb2.setSelectedIndex(0);
            }
        });

        l2 = new JLabel("Aparat: ");
        l2.setBounds(200, 20, 100, button_h);
        panel.add(l2);

        cb2 = new JComboBox(aparate);
        cb2.setBounds(250, 20, 150, button_h);
        cb2.setBorder(BorderFactory.createLoweredBevelBorder());
        cb2.setForeground(Color.BLACK);
        cb2.setBackground(Color.WHITE);
        panel.add(cb2);

        cb2.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                }
            }
            public void keyReleased(KeyEvent e) {}
            public void keyTyped(KeyEvent e) {}
        });

        cb2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int f = tf1.getIntValue();

                int idx = cb2.getSelectedIndex();
                String w = Big.big(getSensorWidth(idx)).toString();
                String h = Big.big(getSensorHeight(idx)).toString();
                String a = Big.mul6(w, h).setScale(2, RoundingMode.HALF_UP).toString();
                String w2 = Big.mul6(w, w).toString();
                String h2 = Big.mul6(h, h).toString();
                double x = Big.add6(w2, h2).doubleValue();
                double c2 = Big.div6(film35mm, x).doubleValue();
                double c = Math.sqrt(c2);

                l5.setText(w+"mm x "+h+"mm");
                l7.setText(a+" mm^2");
                l9.setText(Big.big6(String.valueOf(c)).setScale(4, RoundingMode.HALF_UP).toString());

                if (f > 0) {
                   double f2 = Big.mul6(2, tf1.getDoubleValue()).doubleValue();
                   double w1 = Big.div6(Double.valueOf(w), f2).doubleValue();
                   double h1 = Big.div6(Double.valueOf(h), f2).doubleValue();
                   double oriz = Math.toDegrees(Big.mul6(2, Math.atan(w1)).doubleValue());
                   double vert = Math.toDegrees(Big.mul6(2, Math.atan(h1)).doubleValue());

                   tf2.setValue(Big.mul6(f, Double.valueOf(l9.getText())).doubleValue());
                   tf3.setValue(oriz);
                   tf4.setValue(vert);
                }
            }
        });

        l3 = new JLabel("Distanta focala: ");
        l3.setBounds(10, 50, 100, button_h);
        panel.add(l3);

        tf1 = new MyJTextField(0, MyJTextField.TF_TYPE_INT, 0, 0);
        tf1.setBounds(105, 50, 80, button_h);
        panel.add(tf1);

        tf1.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                   int f = tf1.getIntValue();

                   int idx = cb2.getSelectedIndex();
                   String w = Big.big6(getSensorWidth(idx)).toString();
                   String h = Big.big6(getSensorHeight(idx)).toString();

                   if (f > 0) {
                      double f2 = Big.mul6(2, tf1.getDoubleValue()).doubleValue();
                      double w1 = Big.div6(Double.valueOf(w), f2).doubleValue();
                      double h1 = Big.div6(Double.valueOf(h), f2).doubleValue();
                      double oriz = Math.toDegrees(Big.mul6(2, Math.atan(w1)).doubleValue());
                      double vert = Math.toDegrees(Big.mul6(2, Math.atan(h1)).doubleValue());

                      tf2.setValue(Big.mul6(f, Double.valueOf(l9.getText())).doubleValue());
                      tf3.setValue(oriz);
                      tf4.setValue(vert);
                   }
                }
            }
            public void keyReleased(KeyEvent e) {}
            public void keyTyped(KeyEvent e) {}
        });

        l4 = new JLabel("Dimensiuni: ");
        l4.setBounds(415, 20, 100, button_h);
        panel.add(l4);

        l5 = new JLabel();
        l5.setBounds(490, 20, 200, button_h);
        panel.add(l5);

        l6 = new JLabel("Suprafata: ");
        l6.setBounds(415, 50, 100, button_h);
        panel.add(l6);

        l7 = new JLabel();
        l7.setBounds(490, 50, 200, button_h);
        panel.add(l7);

        l10 = new JLabel("Echivalent 35mm:");
        l10.setBounds(200, 50, 100, button_h);
        panel.add(l10);

        tf2 = new MyJTextField(0.0, MyJTextField.TF_TYPE_DOUBLE, 2, 0);
        tf2.setBounds(305, 50, 95, button_h);
        panel.add(tf2);

        l8 = new JLabel("Crop factor:");
        l8.setBounds(415, 80, 100, button_h);
        panel.add(l8);

        l9 = new JLabel();
        l9.setBounds(490, 80, 200, button_h);
        panel.add(l9);

        l11 = new JLabel("Unghi orizontala:");
        l11.setBounds(10, 80, 100, button_h);
        panel.add(l11);

        tf3 = new MyJTextField(0.0, MyJTextField.TF_TYPE_DOUBLE, 2, 0);
        tf3.setBounds(110, 80, 75, button_h);
        panel.add(tf3);

        l12 = new JLabel("Unghi verticala:");
        l12.setBounds(200, 80, 100, button_h);
        panel.add(l12);

        tf4 = new MyJTextField(0.0, MyJTextField.TF_TYPE_DOUBLE, 2, 0);
        tf4.setBounds(305, 80, 95, button_h);
        panel.add(tf4);

        bOk = new JButton("Ok");
		bOk.setBounds(width - button_w *2 - 25 - 10, height - button_h - margin, button_w, button_h);
		bOk.setBorder(BorderFactory.createRaisedBevelBorder());
		bOk.setMnemonic(KeyEvent.VK_O);
	    panel.add(bOk);

	    bCancel = new JButton("Cancel");
		bCancel.setBounds(width - button_w - 25, height - button_h - margin, button_w, button_h);
		bCancel.setBorder(BorderFactory.createRaisedBevelBorder());
		bCancel.setMnemonic(KeyEvent.VK_C);
		panel.add(bCancel);

        cb1.setSelectedIndex(0);

        bOk.addKeyListener(new KeyListener() {
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) bOk.doClick();
        	}
        	public void keyReleased(KeyEvent e) {}
        	public void keyTyped(KeyEvent e) {}
        });

		bCancel.addKeyListener(new KeyListener() {
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) bCancel.doClick();
        	}
        	public void keyReleased(KeyEvent e) {}
        	public void keyTyped(KeyEvent e) {}
        });

        bOk.addActionListener(new ActionListener() {
	       public void actionPerformed(ActionEvent ae) {
              if (doOk()) {
                 dispose();
              }
	       }
        });

		bCancel.addActionListener(new ActionListener() {
	       public void actionPerformed(ActionEvent ae) {
               if (doCancel()) {
                  dispose();
               }
	       }
        });

        setTitle("Panorama");
        setSize(new Dimension(width, height));
        setBounds((dim.width-width)/2, (dim.height-height)/2, width, height);
        setContentPane(panel);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                PanoramaFrame.this.windowClosed();
            }
        });

        setVisible(true);
    }

    public boolean doOk() {
        return true;
    }

	public boolean doCancel() {
        return true;
    }

    protected void windowClosed() {
        System.exit(0);
    }

    public Vector<Vector> getSensorList(String id) {
        return script.processV(id, 4, ',');
    }

    public Vector getAparate(Vector<Vector> sensors) {
        Vector a = new Vector();
        Vector v;

        for (i = 0; i < sensors.size(); i++) {
            v = sensors.get(i);
            a.add(v.get(0));
        }

        return a;
    }

    public String getSensorWidth(int idx) {
        Vector v = date_aparate.get(idx);
        return (String)v.get(1);
    }

    public String getSensorHeight(int idx) {
        Vector v = date_aparate.get(idx);
        return (String)v.get(2);
    }
}