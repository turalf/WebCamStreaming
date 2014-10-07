/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcamstream;

import com.github.sarxos.webcam.ds.buildin.natives.Device;
import com.github.sarxos.webcam.ds.buildin.natives.DeviceList;
import com.github.sarxos.webcam.ds.buildin.natives.OpenIMAJGrabber;
import java.awt.Image;
import java.awt.Panel;
import java.util.LinkedList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.bridj.Pointer;
import simplestream.Viewer;

/**
 *
 * @author tural
 */
public class WebCamCapture extends Thread {

    int width = 320;
    int height = 240;

    private Pointer<Byte> img = null;

    public static void main(String... args) {

        new WebCamCapture().run();

    }

    public void paintFrame() {

    }

    @Override
    public void run() {
        
        //creating local webcam viewer
        Viewer myViewer = new Viewer();
        JFrame frame = new JFrame("Me");
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(myViewer);
        

        OpenIMAJGrabber grabber = new OpenIMAJGrabber();

        Device device = null;
        Pointer<DeviceList> devices = grabber.getVideoDevices();
        for (Device d : devices.get().asArrayList()) {
            device = d;
            break;
        }

        boolean started = grabber.startSession(320, 240, 30.0, Pointer.pointerTo(device));
        if (!started) {
            throw new RuntimeException("Not able to start native grabber!");
        }
        int i = 0;
        while (i < 10000) {
            grabber.nextFrame();
            Pointer<Byte> img = grabber.getImage();
            byte[] rawImage = img.getBytes(width * height * 3);
            myViewer.ViewerInput(rawImage);
            this.img = img;
            frame.repaint();
            i++;

        }
        grabber.stopSession();

    }

    synchronized public Pointer<Byte> getLastImage() {

        try {
            this.sleep(100);
            return this.img;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            return null;
        }

    }

}
