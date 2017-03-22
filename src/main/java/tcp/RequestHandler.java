package tcp;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private NodeConnector nc;
    private NodeRequest nr;


    public RequestHandler(NodeConnector ncCurrent, NodeConnector ncNext, NodeRequest nr) {
        this.nc = ncNext;
        this.nr = nr;
        nr.addDelay(ncNext.getDelay()); // add delay to next node connector
        nr.pushTraceStack(ncCurrent); // add current node to the trace stack of the request
    }

    @Override
    public void run() {
        try {
            Socket s = new Socket(nc.getIp(), nc.getPort());
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject(nr);
            out.flush();
            out.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
