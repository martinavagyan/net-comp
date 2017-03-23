import mq.Receive;
import mq.Send;
import rmi.RmiClient;
import rmi.RmiServer;
import tcp.AccessNode;
import web.REST;

/**
 * Created by jurgen on 22-3-17.
 */
public class Node {

    public static void main(String[] args) {
        //REST api
        REST api = new REST();
        AccessNode node = new AccessNode(1111, 50);
        api.setAccessNode(node);

        node.run();


        /*
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
        */
    }
}
