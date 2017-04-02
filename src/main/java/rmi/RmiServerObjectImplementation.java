package rmi;

import java.rmi.*;
import java.rmi.server.*;

public class RmiServerObjectImplementation extends UnicastRemoteObject implements RmiServerObjectInterface {

    public RmiServerObjectImplementation() throws RemoteException {
        super(0);
    }

    /**
     *Test the connection by getting a message
     * */
    @Override
    public String getMessage() throws RemoteException {
        return ("Test Get Message");
    }

    /**
     *Log a message to the server
     * */
    @Override
    public void logMessage(String message) throws RemoteException {
        System.out.println(message);
    }
}
