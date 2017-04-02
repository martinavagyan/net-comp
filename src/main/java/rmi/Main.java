package rmi;

import java.net.InetAddress;

/**
 * Created by Martin on 3/23/2017.
 * RMI Server Program
 */
public class Main {
    public static void main(String[] args) {
        try {
            //RMI
            String serverIP = InetAddress.getLocalHost().getHostAddress();

            RmiServer server = new RmiServer();
            server.startServer();
        } catch (Exception e){ e.printStackTrace();}
    }
}
