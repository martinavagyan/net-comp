package tcp;


import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ProxyNode {
    private ArrayList<NodeConnector> connectionList;

    public ProxyNode() {
        connectionList = new ArrayList<>();
    }

    public void addNewTask(int size, long jobID) {
        int randomNode = ThreadLocalRandom.current().nextInt(0, connectionList.size());
        NodeConnector nc = connectionList.get(randomNode);
        NodeTask nt = new NodeTask(jobID, size);
        SocketSender.send(nc,nt);
    }

    public void addNodeConnector(NodeConnector nc) { connectionList.add(nc); }

}
