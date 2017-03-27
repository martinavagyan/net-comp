package rmi;

import rmi.RmiServerObjectInterface;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiClient {

    private String rmiObjectName;

    public RmiClient(String ip, int port){
        rmiObjectName = "rmi://" + ip + ":" + port   + "/RmiServer";
    }

    /**
     * Get message from the server
     * */
    public void getRemoteMessage() throws Exception {
        RmiServerObjectInterface obj = connectToServer();
        System.out.println(obj.getMessage());
    }

    /**
     * Send loging message to the server
     * */
    public void logMessage(String msg) throws Exception{
        RmiServerObjectInterface obj = connectToServer();
        obj.logMessage(msg);
    }

    private RmiServerObjectInterface connectToServer(){
        try{
            RmiServerObjectInterface obj = (RmiServerObjectInterface)Naming.lookup(rmiObjectName);
            return obj;
        }
        catch (ConnectException conEx){
            System.out.println("Unable to connect to server!");
            System.exit(1);
        } catch (RemoteException e) {
            System.out.println("Custom: RemoteException");
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.out.println("Custom: NotBoundException");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("Custom: MalformedURLException");
            e.printStackTrace();
        }
        return null;
    }
}