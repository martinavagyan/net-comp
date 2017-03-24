import tcp.AccessNode;
import web.REST;

import static tcp.AccessNodeInitiator.initAccessNode;


public class Node {

    public static void main(String[] args) {
        //REST api
        REST api = new REST();
        try {
            AccessNode node = initAccessNode("access.txt");
            api.setAccessNode(node);
            node.addNewTask(10000, 2);
            node.addNewTask(100000, 3);
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
