import mq.Receive;
import mq.Send;
import rmi.RmiClient;
import rmi.RmiServer;
import web.REST;

/**
 * Created by jurgen on 22-3-17.
 */
public class Node {

    public static void main(String[] args) {
        //REST api
        new REST();

        //messageQueue
        new Receive();
        new Send();

        try {
            //RMI
            RmiServer server = new RmiServer();
            RmiClient client = new RmiClient();
            server.startServer();
            client.getRemoteMessage();
        } catch (Exception e){ e.printStackTrace();}
    }
}