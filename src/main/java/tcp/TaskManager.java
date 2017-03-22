package tcp;


import java.util.concurrent.ArrayBlockingQueue;

public class TaskManager implements Runnable{
    private ArrayBlockingQueue<NodeJob> nodeJobQueue;
    private NodeConnector nc; // own node connector

    public TaskManager (NodeConnector nc) {
        this.nc = nc;
        nodeJobQueue = new ArrayBlockingQueue<NodeJob>(10); // hard-coded capacity
    }

    public boolean available() { // later change to check whether maximum concurrent processes is reached
        return nodeJobQueue.remainingCapacity() > 0;
    }

    public synchronized boolean addNodeJob(NodeJob nj) {
        return this.nodeJobQueue.offer(nj);
    }

    private NodeJob retrieveJob() {

    }

    @Override
    public void run() {
        while (true) {

        }
    }
}
