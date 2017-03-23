import mq.Receive;
import mq.Send;
import rmi.RmiClient;
import rmi.RmiServer;
import tcp.AccessNode;
import tcp.AccessNodeInitiator;
import tcp.TCPNode;
import web.REST;

import static tcp.AccessNodeInitiator.initiAccessNode;

/**
 * Created by jurgen on 22-3-17.
 */
public class Node {

    public static void main(String[] args) {
        //REST api
        REST api = new REST();
        try {
            AccessNode node = initiAccessNode("target/classes/access.txt");
            api.setAccessNode(node);
            node.run();
        } catch (Exception e) {
            e.printStackTrace();
        }



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
