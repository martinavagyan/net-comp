package tcp;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AccessNodeInitiator {

    public static AccessNode initAccessNode(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));

        int port = Integer.parseInt(in.readLine());
        int numConnections = Integer.parseInt(in.readLine());
        int numWorkerNodes = Integer.parseInt(in.readLine());

        String rmiline = in.readLine();
        String[] rmiSpec = rmiline.split(" ");
        AccessNode accessNode = new AccessNode(port, numWorkerNodes,rmiSpec[0], Integer.parseInt(rmiSpec[1]));

        for (int i=0; i < numConnections; ++i) {
            String line = in.readLine();
            String[] connector = line.split(" ");
            accessNode.addNodeConnector(new NodeConnector(connector[0], Integer.parseInt(connector[1]), Integer.parseInt(connector[2])));
        }
        return accessNode;
    }
}
