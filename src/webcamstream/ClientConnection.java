/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcamstream;

import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.apache.commons.codec.binary.Base64;
import simplestream.Compressor;
import simplestream.Viewer;

/**
 *
 * @author tural
 */
public class ClientConnection extends Thread {

    final static int SERVER_PORT = 6262;

    public static void main(String... a) {
        Viewer myViewer = new Viewer();
        JFrame frame = new JFrame("Simple Stream Viewer");
        frame.setVisible(true);
        frame.setSize(160, 120);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(myViewer);

        try (Socket cs = new Socket("localhost", SERVER_PORT)) {
            DataInputStream in = new DataInputStream(cs.getInputStream());
            DataOutputStream out = new DataOutputStream(cs.getOutputStream());

            out.writeUTF("This is a message from Client");
            while (true) {
                byte[] base64_image = new byte[65000];
                in.read(base64_image);
                System.out.println(base64_image);

                byte[] nobase64_image = Base64.decodeBase64(base64_image);
                /* Decompress the image */
                byte[] decompressed_image = Compressor.decompress(nobase64_image);
                /* Give the raw image bytes to the viewer. */
                myViewer.ViewerInput(decompressed_image);
                frame.repaint();

            }

        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
