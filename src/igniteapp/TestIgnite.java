/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igniteapp;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

/**
 *
 * @author uno
 */
public class TestIgnite {
    public static void main(String[] args) {
        
         String serverIgnite="/home/uno/softwares/apache-ignite-fabric-2.6.0-bin/config/default-config.xml";
//         String serverIgnite="/home/chaitanya/Downloads/apache-ignite-fabric-2.6.0-bin/config/default-config.xml";
//	String serverIgnite="/mongodb/apache-ignite/config/default-config.xml";
       Ignite ignite = Ignition.start(serverIgnite);
        System.out.println("server started ....");
    }
    
}
