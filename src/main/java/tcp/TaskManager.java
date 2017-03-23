package tcp;


import java.util.concurrent.ArrayBlockingQueue;

public class TaskManager implements Runnable{
    private ArrayBlockingQueue<NodeJob> nodeJobQueue;
    private NodeConnector nc; // own node connector, used for identification when logging

    public TaskManager (NodeConnector nc) {
        this.nc = nc;
        nodeJobQueue = new ArrayBlockingQueue<>(10); // hard-coded capacity
    }

    public synchronized boolean addNodeJob(NodeJob nj) {
        return this.nodeJobQueue.offer(nj);
    }

    private NodeJob retrieveJob() {
        return nodeJobQueue.poll();
    }

    @Override
    public void run() {
        while (true) {
            NodeJob nj = retrieveJob();
            if (nj != null) {
                nj.getTask().execute();
                // update webservice that job is done
                System.out.println("Finished job with ID: " + nj.getJobID()+ "\n----------------" + "\n");
            }
        }
    }

    public long getDelay() {
        return nodeJobQueue.size()*100; // hard-coded for now
    }
}
