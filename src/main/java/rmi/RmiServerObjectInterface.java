package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerObjectInterface extends Remote {
    public String getMessage() throws RemoteException;
    public void logMessage(String message) throws RemoteException;
}