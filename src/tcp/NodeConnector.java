package tcp;


public class NodeConnector {
    private String ip;
    private Integer port;

    public NodeConnector(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}