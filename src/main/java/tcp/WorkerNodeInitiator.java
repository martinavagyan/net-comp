package tcp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WorkerNodeInitiator {

    public static void main(String[] args) throws IOException {
        WorkerNode wn = initWorkerNode(args[0]);
        (new Thread(wn)).start();
    }

    public static WorkerNode initWorkerNode(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));

        int port = Integer.parseInt(in.readLine());
        int numConnections = Integer.parseInt(in.readLine());
        String webHost = in.readLine();
        WorkerNode workerNode = new WorkerNode(port, webHost);

        for (int i=0; i < numConnections; ++i) {
            String line = in.readLine();
            String[] connector = line.split(" ");
            workerNode.addNodeConnector(new NodeConnector(connector[0], Integer.parseInt(connector[1]), Integer.parseInt(connector[2])));
        }
        return workerNode;
    }
}
