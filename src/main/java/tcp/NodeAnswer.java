package tcp;

import java.io.Serializable;

public class NodeAnswer implements Serializable, Comparable<NodeAnswer> {
    private long jobID;
    private long delay;
    private NodeConnector origin;
    private NodeConnector destination;


    public NodeAnswer(NodeRequest nr, long delay, NodeConnector origin) {
        this.delay = nr.getDelay() + delay; // delay from request travel and current node
        this.jobID = nr.getJobID();
        this.origin = origin;
        this.destination = nr.getOrigin();
    }

    public long getDelay() { return delay; }

    public long getJobID() { return jobID; }

    public NodeConnector getOrigin() { return origin; }

    public NodeConnector getDestination() { return destination; }

    @Override
    public int compareTo(NodeAnswer that) {
        if (this.getDelay() > that.getDelay()) return 1;
        else if (this.getDelay() == that.getDelay()) return 0;
        return -1;
    }
}
