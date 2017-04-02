package tcp;


import java.io.Serializable;

/** Class that represents the model of packets to be sent via sockets. */
public abstract class AbstractNodePacket implements Serializable{
    private long jobID;
    private NodeConnector origin;
    private NodeConnector destination;


    public AbstractNodePacket(long jobID, NodeConnector origin, NodeConnector destination) {
        this.jobID = jobID;
        this.origin = origin;
        this.destination = destination;
    }

    public NodeConnector getOrigin() { return this.origin;}

    public NodeConnector getDestination() { return destination; }

    public long getJobID() { return this.jobID; }
}
