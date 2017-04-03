
import tcp.ProxyNode;
import web.REST;

import static tcp.ProxyNodeInitiator.initProxyNode;


public class Node {

    public static void main(String[] args) {
        REST api = new REST();
        try {
            ProxyNode proxy = initProxyNode("proxy.txt");
            api.setProxyNode(proxy);
            proxy.addNewTask(1000000, 8);
            proxy.addNewTask(1500000, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
