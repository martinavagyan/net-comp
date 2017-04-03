package tcp;


/** Class used for handling the sending of a NodeJob. */
public class JobHandler implements Runnable {
    private NodeJob nj;


    public JobHandler(NodeJob nj) {
        this.nj = nj;
    }

    @Override
    public void run() {
        NodeConnector nc = nj.getDestination();
        System.out.println("Sending NodeJob to " + nc.getIp() + " on port: " + nc.getPort());
        SocketSender.send(nc, nj);
    }
}
