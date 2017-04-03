package tcp;


/** Class used for handling the sending of a NodeRequest. */
public class RequestHandler implements Runnable {
    private NodeConnector nc;
    private NodeRequest nr;


    public RequestHandler(NodeConnector ncCurrent, NodeConnector ncNext, NodeRequest nr) {
        this.nc = ncNext;
        this.nr = nr;
        nr.addDelay(ncNext.getDelay()); // add delay to next node connector for this node request
        nr.pushTraceStack(ncCurrent);   // add current node to the trace stack of the request
    }

    @Override
    public void run() {
        System.out.println("Sending NodeRequest to " + nc.getIp() + " on port: " + nc.getPort());
        SocketSender.send(nc, nr);
    }
}
