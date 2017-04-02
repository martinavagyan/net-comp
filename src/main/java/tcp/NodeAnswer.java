package tcp;

import java.io.Serializable;

/** Class used for representation of a packet that contains the answer from a worker node. */
public class NodeAnswer extends AbstractNodePacket implements Serializable, Comparable<NodeAnswer> {
    private long delay;


    public NodeAnswer(NodeRequest nr, long delay, NodeConnector origin) {
        super(nr.getJobID(), origin, nr.getOrigin());
        this.delay = nr.getDelay() + delay; // delay from request travel and current node
    }

    public long getDelay() { return delay; }

    @Override
    public int compareTo(NodeAnswer that) {
        if (this.getDelay() > that.getDelay()) return 1;
        else if (this.getDelay() == that.getDelay()) return 0;
        return -1;
    }
}
