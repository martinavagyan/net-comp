package tcp;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AccessNodeInitiator {

    public static AccessNode initiAccessNode(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));

        int port = Integer.parseInt(in.readLine());
        int numConnections = Integer.parseInt(in.readLine());
        AccessNode accessNode = new AccessNode(port, 1); // hard coded size of worker nodes

        for (int i=0; i < numConnections; ++i) {
            String line = in.readLine();
            String[] connector = line.split(" ");
            accessNode.addNodeConnector(new NodeConnector(connector[0], Integer.parseInt(connector[1]), Integer.parseInt(connector[2])));
        }
        return accessNode;
    }
}
