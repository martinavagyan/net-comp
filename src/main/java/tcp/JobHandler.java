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
        try {
            NodeConnector nc = nj.popBackTraceStack();
            Socket s = new Socket(nc.getIp(), nc.getPort());
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject(nj);
            out.flush();
            out.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
