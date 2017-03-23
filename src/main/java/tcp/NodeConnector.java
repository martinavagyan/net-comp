package tcp;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeConnector that = (NodeConnector) o;
        return Objects.equals(ip, that.ip) &&
                Objects.equals(port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }
}