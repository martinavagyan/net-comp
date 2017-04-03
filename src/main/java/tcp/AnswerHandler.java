package tcp;

/** Class used for handling the sending of a NodeAnswer. */
public class AnswerHandler implements Runnable{
    private NodeAnswer na;


    public AnswerHandler(NodeAnswer na) { this.na = na; }

    @Override
    public void run() {
        NodeConnector nc = na.getDestination();
        System.out.println("Sending NodeAnswer to " + nc.getIp() + " on port: " + nc.getPort());
        SocketSender.send(nc, na);
    }
}
