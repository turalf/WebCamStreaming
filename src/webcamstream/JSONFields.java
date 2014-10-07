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
public enum JSONFields {
    
    TYPE("type"),
    DATA("data"),
    FORMAT("format"),
    WIDTH("width"),
    HEIGHT("height");
    
    
    private String value ;
    private JSONFields(String value) {
        this.value = value;
    }
    public String  getFieldValue(){
        return  this.value;
    }
}
