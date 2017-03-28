package tcp;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class JobHandler implements Runnable {
    private NodeJob nj;

    public JobHandler(NodeJob nj) {
        this.nj = nj;
    }

    @Override
    public void run() {
        NodeConnector nc = nj.getDestination();
        System.out.println("Going to send NodeJob to " + nc.getIp() + " on port: " + nc.getPort());
        SocketSender.send(nc, nj);
    }
}
