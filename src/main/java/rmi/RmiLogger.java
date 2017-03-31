package rmi;

import tcp.NodeAnswer;

import java.time.LocalDateTime;

public class RmiLogger {

    private RmiClient rmiClient;

    public RmiLogger(String rmiIp, int rmiPort){
        rmiClient = new RmiClient(rmiIp,rmiPort);
    }

    /**
     * Received node request message
     * */
    public void receivedNodeRequestLog(String jobID){
        sendLog("Received NodeRequest with jobID: "+jobID);
    }

    /**
     * Received node request message
     * */
    public void receivedNodeJobLog(String jobID){
        sendLog("Received NodeJob with jobID: "+jobID);
    }


    /**
     * Node Request message
     * */
    public void sendNodeRequestLog(String nodeConnector){
        sendLog(nodeConnector + " - send node request ");
    }

    /**
     * Node Job message
     * */
    public void sendNodeJobLog(String nodeConnector){
        sendLog(nodeConnector + " - send node job ");
    }

    /**
     * Node Answer message
     * */
    public void receivedNodeAnswerLog(String nodeConnector, NodeAnswer na){
        sendLog(nodeConnector + " - received node answer from "
                + na.getOrigin() + " for JobID" + na.getJobID());
    }

    /**
     * Node Task message
     * */
    public void receivedNodeTaskLog(String nodeConnector){
        sendLog(nodeConnector + " - received node task");
    }

    /**
     * Test the connection by sending a message
     * */
    public void testConnectionLog(String ip){
        sendLog("Node with IP: " + ip + " successfully connected!");
    }

    /**
     * Send the message to server via RMI connection
     * */
    private void sendLog(String message){
        try {
            rmiClient.logMessage(message+ " : " + LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
