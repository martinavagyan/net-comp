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

        String[] portLine = in.readLine().split(" ");
        int port = Integer.parseInt(portLine[0]);

        String[] numConnectLine = in.readLine().split(" ");
        int numConnections = Integer.parseInt(numConnectLine[0]);

        String[] webHostLine = in.readLine().split(" ");
        String[] rmiLine = in.readLine().split(" ");
        WorkerNode workerNode = new WorkerNode(port, webHostLine[0], rmiLine[0], Integer.parseInt(rmiLine[1]));

        for (int i=0; i < numConnections; ++i) {
            String line = in.readLine();
            String[] connector = line.split(" ");
            workerNode.addNodeConnector(new NodeConnector(connector[0], Integer.parseInt(connector[1]), Integer.parseInt(connector[2])));
        }
        return workerNode;
    }
}
