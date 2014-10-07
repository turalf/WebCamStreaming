/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webcamstream;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author tural
 */
public class Message {
    private String type;
    private  String format="";
    private int width ;
    private int height ;
    private String data= "";
    
    
    public Message(String type){
        this.type = type;
    }
    public Message(String type,String data){
        this.type = type;
        this.data = data;
    }
    public Message(String type,String format,int width, int height){
        this.type = type;
        this.format = format;
        this.width = width;
        this.height = height;
    }
    
    
    public static void main(String ... arg){
        Message m = new Message("startStirng","raw",21,32);
        System.out.println(m.toJSONString());
        
        Message pm = m.parseJSONString(m.toJSONString());
        System.out.println("");
        
    }
    
    
    public static Message parseJSONString(String JSONString){
        JSONObject jo = null;
        try {           
            jo = (JSONObject) new JSONParser().parse(JSONString);
        } catch (ParseException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        Message m = new Message((String)jo.get(JSONFields.TYPE.getFieldValue()));
        Object dataField = jo.get(JSONFields.DATA.getFieldValue());
        Object formatField = jo.get(JSONFields.FORMAT.getFieldValue());
        Object heightField = jo.get(JSONFields.HEIGHT.getFieldValue());
        Object widthField = jo.get(JSONFields.WIDTH.getFieldValue());
        if (dataField!=null){
            m.setData((String) dataField);
        }
        if (formatField!=null){
            m.setFormat((String) formatField);
        }
        if (heightField!=null){
            m.setHeight(((Long) heightField).intValue());
        }
        if (widthField!=null){
            m.setWidth(((Long) widthField).intValue());
        }
        
        return  m;
        
    }
    
    public String toJSONString(){
        
        String typeString = "{\""+JSONFields.TYPE.getFieldValue()+"\":\""+this.type+"\",";
        String formatString = "";
        String dataString = "";
        String widthString = "";
        String heightString = "";
        
        if (!format.equals("")){
              formatString = "\""+JSONFields.FORMAT.getFieldValue()+"\":\""+this.format+"\",";
        }
        if (!data.equals("")){
              dataString = "\""+JSONFields.DATA.getFieldValue()+"\":\""+this.data+"\",";
        }
        if (width!=0){
              widthString = "\""+JSONFields.WIDTH.getFieldValue()+"\":"+this.width+",";
        }
        if (height!=0){
              heightString = "\""+JSONFields.HEIGHT.getFieldValue()+"\":"+this.height+",";
        }
        
        String result = typeString + formatString + dataString + widthString + heightString;
        
        result = result.substring(0,result.length()-1)+"}";
        
        return result;
        
        
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    

    public String getType() {
        return type;
    }

    public String getFormat() {
        return format;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getData() {
        return data;
    }
    
    
    
}
