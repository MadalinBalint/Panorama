/**
 * Clasa Panorama.java (24 iulie 2011)
 *
 * @author Balint Corneliu Madalin
 * @version 1.0.0 - 24 iulie 2011
 */

public class Panorama {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PanoramaFrame frame = new PanoramaFrame();
                frame.setVisible(true);
            }
        });
    }
}