package tcp;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AnswerHandler implements Runnable {
    private NodeAnswer na;

    public AnswerHandler(NodeAnswer na) {
        this.na = na;
    }

    @Override
    public void run() { // obviously needs refactoring
        try {
            NodeConnector nc = na.popBackTraceStack(); // get next node from the trac
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
