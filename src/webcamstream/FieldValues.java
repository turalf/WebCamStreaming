/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webcamstream;

/**
 *
 * @author tural
 */
public enum FieldValues {
    
    STARTSTREAM("startstream"),
    STOPSTREAM("stopstream"),
    IMAGE("image"),
    FORMAT("raw");
    
    private String value ;
    private FieldValues(String value) {
        this.value = value;
    }
    public String  getValue(){
        return  this.value;
    }
}
