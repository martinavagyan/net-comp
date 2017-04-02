package tcp;

import java.io.Serializable;

/** Class used for representation of a packet that contains a task to be handled by the access node. */
public class NodeTask extends AbstractNodePacket implements Serializable {
    private int size;

    public NodeTask(long jobID, int size) {
        super(jobID, null, null);
        this.size = size;
    }

    public int getSize() { return size; }

}
