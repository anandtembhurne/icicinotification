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
public class IgniteApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String localIgnite="/home/uno/softwares/apache-ignite-fabric-2.6.0-bin/config/default-config.xml";
	//String serverIgnite="/mongodb/apache-ignite/config/default-config.xml";
       Ignite ignite = Ignition.start(localIgnite);
        System.out.println("server started ....");
       ///home/uno/.m2/repository/org/apache/ignite/ignite-clients/1.0.0/ignite-clients-1.0.0.jar
    }
    
}
