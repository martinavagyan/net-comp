package tcp;


import rmi.RmiLogger;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public abstract class TCPAbstractNode implements Runnable {
    protected ArrayList<NodeConnector> connectionList;
    protected ServerSocket ssocket;
    protected RmiLogger rmiLogger;

    public TCPAbstractNode(int port,String rmiIp, int rmiPort ){
        rmiLogger = new RmiLogger(rmiIp ,rmiPort);
        try {
            String ip = null;
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp() || iface.isVirtual() || iface.isPointToPoint())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    final String theIp = addr.getHostAddress();
                    if(Inet4Address.class == addr.getClass()) ip= theIp;
                }
            }

            this.ssocket = new ServerSocket(port, 10, InetAddress.getByName(ip));
            rmiLogger.testConnectionLog(getNodeConnector().toString());
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
