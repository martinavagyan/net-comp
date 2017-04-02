package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerObjectInterface extends Remote {
    String getMessage() throws RemoteException;
    void logMessage(String message) throws RemoteException;
}