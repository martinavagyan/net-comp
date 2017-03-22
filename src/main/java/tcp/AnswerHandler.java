package tcp;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AnswerHandler implements Runnable{
    private NodeAnswer na;

    public AnswerHandler(NodeConnector nc, NodeAnswer n) {
        this.na = n;
        this.na.pushTraceStack(nc); // add current NodeConnector to the traceStack
    }

    @Override
    public void run() {
        try {
            NodeConnector nc = na.popBackTraceStack(); // get next node from the back trace
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
