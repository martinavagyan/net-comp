package tcp;

import java.io.Serializable;

public class NodeTask extends AbstractNodePacket implements Serializable {
    private int size;

    public NodeTask(long jobID, int size) {
        super(jobID, null, null);
        this.size = size;
    }

    public int getSize() { return size; }

}
