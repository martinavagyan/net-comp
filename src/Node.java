import java.util.ArrayList;

public class Node {
    ArrayList peers = new ArrayList();

    public Node() {

        try {
            RmiClient client = new RmiClient();
            RmiServer server = new RmiServer();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
