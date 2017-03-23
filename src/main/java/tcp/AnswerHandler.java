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
        SocketSender.send(nc, na);
    }
}
