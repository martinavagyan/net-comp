package tcp;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AcceptHandler implements Runnable{
    private NodeAccept na;

    public AcceptHandler(NodeAccept na) {
        this.na = na;
    }

    @Override
    public void run() {
        try {
            NodeConnector nc = na.popNodeConnector(); // get next node from the trace
            Socket s = new Socket(nc.getIp(), nc.getPort());
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());

            out.writeObject(na);
            out.flush();
            out.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
