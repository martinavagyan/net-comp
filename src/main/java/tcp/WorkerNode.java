package tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;


public class WorkerNode extends TCPAbstractNode {
    private TaskManager tm;

    public WorkerNode(int port) {
        super(port);
        tm = new TaskManager(getNodeConnector());
        (new Thread(tm)).start();
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) try {
            System.out.println("Patiently waiting...\n" + "IP: " + getIP() + " on port: "+ getPort());

            Socket client = ssocket.accept();
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            Object obj = in.readObject();

            if (obj instanceof NodeRequest) {
                NodeRequest nr = (NodeRequest) obj;
                System.out.println("Received NodeRequest" + nr.getJobID());

                NodeAnswer na = new NodeAnswer(nr, tm.getDelay());
                AnswerHandler ah = new AnswerHandler(getNodeConnector(), na);
                (new Thread(ah)).start(); // send back the node answer to request (i.e. the delay)

                connectionList.stream().filter(nc -> !nr.getTraceStack().contains(nc)).forEach(nc -> {
                    RequestHandler rh = new RequestHandler(getNodeConnector(), nc, nr);
                    (new Thread(rh)).start();
                });
            } else if (obj instanceof NodeAnswer) { // propagate the answer further
                NodeAnswer na = (NodeAnswer) obj;

                System.out.println("Received NodeAnswer" + na.getJobID());
                AnswerHandler ah = new AnswerHandler(getNodeConnector(), na);
                (new Thread(ah)).start(); // send the answer further
            } else if (obj instanceof NodeJob) {
                NodeJob nj = (NodeJob) obj;
                System.out.println("Received NodeJob" + nj.getJobID());
                if (nj.validate(getNodeConnector())) { // job is for us
                    tm.addNodeJob(nj); // post to webservice when finished
                } else { // send it further
                    JobHandler jh = new JobHandler(nj);
                    (new Thread(jh)).start();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
