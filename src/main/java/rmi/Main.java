package rmi;

import java.net.InetAddress;

/**
 * Created by Martin on 3/23/2017.
 */
public class Main {
    public static void main(String[] args) {
        try {
            //RMI
            String serverIP = InetAddress.getLocalHost().getHostAddress();

            RmiServer server = new RmiServer();
            server.startServer();

            /*RmiClient client = new RmiClient(serverIP,1099);
            System.out.println("Client connected to IP: " + serverIP);
            client.logMessage("Connected! just now");*/
        } catch (Exception e){ e.printStackTrace();}
    }
}
