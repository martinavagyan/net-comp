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
        NodeConnector nc = nj.popBackTraceStack();
        SocketSender.send(nc, nj);
    }
}
