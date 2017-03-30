package tcp;


public class AnswerHandler implements Runnable{
    private NodeAnswer na;


    public AnswerHandler(NodeAnswer na) { this.na = na; }

    @Override
    public void run() {
        NodeConnector nc = na.getDestination(); // get next node from the back trace
        System.out.println("Going to send NodeAnswer to " + nc.getIp() + " on port: " + nc.getPort());
        SocketSender.send(nc, na);
    }
}
