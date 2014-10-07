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
import java.io.InputStream;
import java.io.InputStreamReader;
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
    static int   width = 320;
    static int  height = 240;
    public static void main(String... a) throws IOException {
        Socket cs = new Socket("localhost", SERVER_PORT);
        System.out.println("socket created");
        DataInputStream ci = new DataInputStream(cs.getInputStream());
        DataOutputStream co = new DataOutputStream(cs.getOutputStream());
        
        
        //sending negotiaiton to the server
        Message m = new Message(FieldValues.STARTSTREAM.getValue(), FieldValues.FORMAT.getValue(), width, height);
        co.writeUTF(m.toJSONString());
        
        byte[] base64 = new byte[width * height * 3];
        while (true) {
            
            byte [] msgBytes = new byte[base64.length + 2048];
            ci.read(msgBytes);
            System.out.println(new String(msgBytes));
            Message sm = Message.parseJSONString(new String(msgBytes).trim());

            //decode an image
            byte[] compressedImage = Base64.decodeBase64(base64);
            //decompressing an image       
            byte[] decompressedImage = Compressor.decompress(compressedImage);

            System.out.println(decompressedImage.length);
        }

    }
}
