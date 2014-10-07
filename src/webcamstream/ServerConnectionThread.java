/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcamstream;

import java.awt.Frame;
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
public class ServerConnectionThread extends Thread {

    int width = 320;
    int height = 240;
    String localViewFrameTitle = "Me";
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    WebCamCapture webcam;

    public ServerConnectionThread() {
    }

    public ServerConnectionThread(Socket clientSocket, WebCamCapture webcam) {
        this.clientSocket = clientSocket;
        this.webcam = webcam;
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String... args) {
        new ServerConnectionThread().run();
    }

    @Override
    public void run() {
        try {

            //getting negotiaion from the client
            Message cm = Message.parseJSONString(in.readUTF());
            System.out.println("");

          
            
           

            while (true) {
                
                
                byte[] rawImage = webcam.getLastImage().getBytes(width * height * 3);               
                //compressing an image
                byte[] commpressedImage = Compressor.compress(rawImage);
                //encoding an image
                byte[] base64 = Base64.encodeBase64(commpressedImage);
                
                Message m = new Message(FieldValues.IMAGE.getValue(),new String (base64));
                byte [] completeMsg = m.toJSONString().getBytes();
                out.write(completeMsg);
            }

        } catch (IOException ex) {
            Logger.getLogger(ServerConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
