/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webcamstream;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author tural
 */
public class Connection extends Thread {
    
    
    final static int SERVER_PORT = 6262;
    public static void initiateConnection(){
        try{
            ServerSocket ss = new ServerSocket(SERVER_PORT);
            while(true){
                Socket clientSocket  = ss.accept();
                ServerConnectionThread sc = new ServerConnectionThread(clientSocket);
                
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String ... args){
        initiateConnection();
        
        
    }
    
}
