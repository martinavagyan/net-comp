package tcp;
import rmi.RmiLogger;
import util.Util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

public abstract class TCPAbstractNode implements Runnable {
    protected ArrayList<NodeConnector> connectionList;
    protected ServerSocket ssocket;
    protected RmiLogger rmiLogger;

    public TCPAbstractNode(int port,String rmiIp, int rmiPort ){
        rmiLogger = new RmiLogger(rmiIp ,rmiPort);
        String ip = new Util().getProperIp();

        try {
            this.ssocket = new ServerSocket(port, 10, InetAddress.getByName(ip));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rmiLogger.testConnectionLog(getNodeConnector().toString());
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
