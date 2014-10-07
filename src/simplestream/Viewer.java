package simplestream;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;

public class Viewer extends JPanel {

    private int width = 320;
    private int height = 240;
    private static final long serialVersionUID = 1L;
    private BufferedImage image;

    private int[] toIntArray(byte[] barr) {
        int[] result = new int[barr.length];
        for (int i = 0; i < barr.length; i++) {
            result[i] = barr[i];
        }
        return result;
    }

    public Viewer() {
        this.height = 320;
        this.width = 240;
        image = new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);
    }

    public Viewer(int height, int width) {
        this.height = height;
        this.width = width;
        image = new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);
    }

    public void ViewerInput(byte[] image_bytes) {
        WritableRaster raster = image.getRaster();
        raster.setPixels(0, 0, height, width, toIntArray(image_bytes));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }

}
