package tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;


public class WorkerNode extends TCPAbstractNode {
    private TaskManager tm;


    public WorkerNode(int port, String webHost,String rmiIp, int rmiPort) {
        super(port,rmiIp,rmiPort);
        tm = new TaskManager(getNodeConnector(), webHost);
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
                rmiLogger.receivedNodeRequestLog(nr.getJobID() + "");

                NodeAnswer na = new NodeAnswer(nr, tm.getDelay(), getNodeConnector());
                AnswerHandler ah = new AnswerHandler(na);
                (new Thread(ah)).start(); // send back the node answer to request (i.e. the delay)

                connectionList.stream().filter(nc -> !nr.getTraceStack().contains(nc)).forEach(nc -> {
                    RequestHandler rh = new RequestHandler(getNodeConnector(), nc, nr);
                    (new Thread(rh)).start();
                });
            } else if (obj instanceof NodeJob) {
                NodeJob nj = (NodeJob) obj;
                rmiLogger.receivedNodeJobLog(nj.getJobID() + "");
                if (nj.validate(getNodeConnector())) { // job is for us
                    tm.addNodeJob(nj); // post to webservice when finished
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
