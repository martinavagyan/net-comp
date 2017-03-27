package rmi;

import java.rmi.*;
import java.rmi.server.*;

public class RmiServerObjectImplementation extends UnicastRemoteObject implements RmiServerObjectInterface {

    public RmiServerObjectImplementation() throws RemoteException {
        //There is no action need in this moment.
        super(0);
    }

    @Override
    public String getMessage() throws RemoteException {
        return ("Test Get Message");
    }

    @Override
    public void logMessage(String message) throws RemoteException {
        System.out.println(message);
    }
}
