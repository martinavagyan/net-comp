package tcp;


public class NodeConnector {
    private String ip;
    private Integer port;
    private long delay;

    public NodeConnector(String ip, int port, long delay) {
        this.ip = ip;
        this.port = port;
        this.delay = delay;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public long getDelay() {
        return delay;
    }
}