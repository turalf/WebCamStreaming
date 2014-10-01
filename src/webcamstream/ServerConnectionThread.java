/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcamstream;

import com.github.sarxos.webcam.ds.buildin.natives.Device;
import com.github.sarxos.webcam.ds.buildin.natives.DeviceList;
import com.github.sarxos.webcam.ds.buildin.natives.OpenIMAJGrabber;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.apache.commons.codec.binary.Base64;
import org.bridj.Pointer;
import simplestream.Compressor;
import simplestream.Viewer;

/**
 *
 * @author tural
 */
public class ServerConnectionThread extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public ServerConnectionThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void run() {
        String data;
        try {
            data = in.readUTF();

            Viewer myViewer = new Viewer();
            JFrame frame = new JFrame("Simple Stream Viewer");
            frame.setVisible(true);
            frame.setSize(160, 120);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(myViewer);
            /**
             * This example show how to use native OpenIMAJ API to capture raw
             * bytes data as byte[] array. It also calculates current FPS.
             */

            OpenIMAJGrabber grabber = new OpenIMAJGrabber();

            Device device = null;
            Pointer<DeviceList> devices = grabber.getVideoDevices();
            for (Device d : devices.get().asArrayList()) {
                device = d;
                break;
            }

            boolean started = grabber.startSession(160, 120, 30, Pointer.pointerTo(device));
            if (!started) {
                throw new RuntimeException("Not able to start native grabber!");
            }

            int n = 1000;
            int i = 0;
            do {
                /* Get a frame from the webcam. */
                grabber.nextFrame();
                /* Get the raw bytes of the frame. */
                byte[] raw_image = grabber.getImage().getBytes(160 * 120 * 3);
                /* Apply a crude kind of image compression. */
                byte[] compressed_image = Compressor.compress(raw_image);
                /* Prepare the date to be sent in a text friendly format. */
                byte[] base64_image = Base64.encodeBase64(compressed_image);
                
                out.write(base64_image);
                System.out.println(base64_image);
                
                /*
                 * The image data can be sent to connected clients.
                 */
                /*
                 * Assume we received some image data.
                 * Remove the text friendly encoding.
                 */
//                byte[] nobase64_image = Base64.decodeBase64(base64_image);
//                /* Decompress the image */
//                byte[] decompressed_image = Compressor.decompress(nobase64_image);
//                /* Give the raw image bytes to the viewer. */
//                myViewer.ViewerInput(decompressed_image);
                frame.repaint();
            } while (++i < n);

            grabber.stopSession();

           
        } catch (IOException ex) {
            Logger.getLogger(ServerConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
