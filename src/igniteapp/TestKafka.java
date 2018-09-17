/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igniteapp;

/**
 *
 * @author uno
 */
public class TestKafka {
    public static void main(String[] args) {
        String s="'abc', 'pfdsf', 'gj gj'";
        System.out.println("s="+s);
        String replaceAll = s.replaceAll("'","");
        System.out.println("data="+replaceAll);
        String replaceAll1 = replaceAll.replaceAll(" ","");
        System.out.println("res="+replaceAll1);
        
        String[] split = replaceAll.split(",");
        for (String string : split) {
            System.out.println("data="+string);
            
        }
        
            
        
    }
    
}
