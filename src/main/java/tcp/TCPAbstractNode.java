package tcp;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

public abstract class TCPAbstractNode implements Runnable {
    protected ArrayList<NodeConnector> connectionList;
    protected ServerSocket ssocket;

    public TCPAbstractNode(int port) {
        try {
            this.ssocket = new ServerSocket(port, 10, InetAddress.getLocalHost());
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionList = new ArrayList<>();
    }

    public String getIP() { return ssocket.getInetAddress().getHostAddress();  }

    public Integer getPort() {
        return ssocket.getLocalPort();
    }

    public NodeConnector getNodeConnector() {
        return new NodeConnector(getIP(), getPort(), 0);
    }

    public void addNodeConnector(NodeConnector nc) { connectionList.add(nc); }

}
