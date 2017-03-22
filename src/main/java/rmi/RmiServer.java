package rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;

public class RmiServer extends UnicastRemoteObject implements RmiServerIntf {
    public static final String MESSAGE = "Hello World";
    private RmiServer server;

    public RmiServer() throws RemoteException {
        super(0);    // required to avoid the 'rmic' step, see below
    }

    public String getMessage() {
        return MESSAGE;
    }

    public void startServer() throws Exception {
        System.out.println("RMI server started");

        try { //special exception handler for registry creation
            LocateRegistry.createRegistry(1099);
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }

        //Instantiate rmi.RmiServer

        server = new RmiServer();

        // Bind this object instance to the name "rmi.RmiServer"
        Naming.rebind("//localhost/rmi.RmiServer", server);
        System.out.println("PeerServer bound in registry");
    }

    public void stopServer() throws Exception {
        server.stopServer();
    }
}
