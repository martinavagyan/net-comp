package tcp;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable{
    private NodeConnector nc;
    private NodeRequest nr;


    public RequestHandler(NodeConnector nc, NodeRequest nr) {
        this.nc = nc;
        this.nr = nr;
    }

    @Override
    public void run() {
        try {
            Socket s = new Socket(nc.getIp(), nc.getPort());
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            nr.pushNodeConnector(nc); // add next node to the trace stack of the request

            out.writeObject(nr);
            out.flush();
            out.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
