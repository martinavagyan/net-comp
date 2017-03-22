package rmi;

import java.rmi.Naming;

public class RmiClient {


    public void getRemoteMessage() throws Exception {
        RmiServerIntf obj = (RmiServerIntf)Naming.lookup("//localhost/rmi.RmiServer");
        System.out.println(obj.getMessage());
    }
}
