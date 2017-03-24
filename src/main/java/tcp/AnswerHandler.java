package tcp;


public class AnswerHandler implements Runnable{
    private NodeAnswer na;


    public AnswerHandler(NodeConnector nc, NodeAnswer na) {
        this.na = na;
        this.na.pushTraceStack(nc); // add current NodeConnector to the traceStack
    }

    @Override
    public void run() {
        NodeConnector nc = na.popBackTraceStack(); // get next node from the back trace
        System.out.println("Going to send NodeAnswer to " + nc.getIp() + " on port: " + nc.getPort());
        SocketSender.send(nc, na);
    }
}
