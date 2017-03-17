import java.rmi.Naming;
import java.util.ArrayList;

public class RmiClient {


    public void getRemoteMessage() throws Exception {
        RmiServerIntf obj = (RmiServerIntf)Naming.lookup("//localhost/RmiServer");
        System.out.println(obj.getMessage());
    }
}
