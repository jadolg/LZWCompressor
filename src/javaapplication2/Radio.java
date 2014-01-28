/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

/**
 *
 * @author Mayo
 */
public class Radio {
    private float radio;
    public void retradio(int dmsglen,int cmsglen,int dictlen){
        int i = 8;
        while (dictlen % Math.pow(2, i) != dictlen){
            i++;
        }        
        radio = ((float)(cmsglen*i)/(float)(dmsglen*8))*100;
    }
    
    public String getValue(){
        return String.valueOf(radio)+" %";
    }
}
