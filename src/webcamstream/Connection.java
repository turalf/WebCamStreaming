/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcamstream;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import simplestream.Viewer;

/**
 *
 * @author tural
 */
public class Connection extends Thread {

    final static int SERVER_PORT = 6262;
    static int  width = 320;
    static int  height = 240;

    public static void initiateConnection() {
        
        //hosts to be taken from command line
        ArrayList<String> connectedHosts = new ArrayList<String>();
        connectedHosts.add("localhost");
        connectedHosts.add("127.0.0.1");
        
        //width and height to be taken from command line
        
        
        try {
            ServerSocket welcomeSocket = new ServerSocket(SERVER_PORT);
            WebCamCapture wc = new WebCamCapture();
            wc.start();
            
            
            
            while (true) {
                Socket clientSocket = welcomeSocket.accept();
                if (!connectedHosts.contains(clientSocket.getInetAddress().getHostAddress())){
                    System.err.println("Connection refused from host: "+clientSocket.getInetAddress().getHostAddress());
                    clientSocket.close();
                }
                else{                    
                    ServerConnectionThread sc = new ServerConnectionThread(clientSocket, wc);
                    sc.start();
                }
                

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        initiateConnection();

    }

}
