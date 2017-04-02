package rmi;

import java.net.InetAddress;
import util.Util;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.*;

public class RmiServer {

    private static InetAddress ip;
    private static String host;
    private static int PORT = 1099;
    private static  String rmiObjectName;

    private RmiServerObjectImplementation serverObj;

    public RmiServer() throws RemoteException {
        try {
            ip = InetAddress.getByName(new Util().getProperIp());
            host = ip.getHostName();
            System.out.println("Server IP address : " + ip);
            System.out.println("Server Hostname : " + host);
        } catch (java.net.UnknownHostException e) {
            e.printStackTrace();
        }
        rmiObjectName = "rmi://" + ip.getHostAddress() + "/RmiServer";
    }

    public void startServer() throws Exception {
        System.out.println("RMI server started");

        /**
         * special exception handler for registry creation
         * */
        try {
            LocateRegistry.createRegistry(PORT);
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            /**
             * do nothing, error means registry already exists
             * */
            System.out.println("java RMI registry already exists.");
        }

         /**
         * instance of rmi server object
         * */
        System.setProperty("java.rmi.server.hostname", ip.getHostAddress());
        System.setSecurityManager(new SecurityManager());

        serverObj = new RmiServerObjectImplementation();
        System.setProperty("java.security.policy", "client.policy");

        /**
         * bind the server with object
         * */
        Naming.rebind(rmiObjectName, serverObj);

        System.out.println("Server binding complete...\n");
    }
}