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
    public void receivedNodeRequestLog(String nodeConnector, String jobID){
        sendLog(nodeConnector + " - received NodeRequest with jobID: "+jobID);
    }

    /**
     * Received node request message
     * */
    public void receivedNodeJobLog(String nodeConnector,String jobID){
        sendLog(nodeConnector + "- received NodeJob with jobID: "+jobID);
    }


    /**
     * Node Request message
     * */
    public void sendNodeRequestLog(String nodeConnector, String jobID, String destination){
        sendLog(nodeConnector + " - send node request with jobID: " + jobID + " to " + destination);
    }

    /**
     * Node Job message
     * */
    public void sendNodeJobLog(String nodeConnector, String jobID, String destination){
        sendLog(nodeConnector + " - send node job with jobID: " + jobID + " to " + destination);
    }

    /**
     * Node Answer message
     * */
    public void receivedNodeAnswerLog(String nodeConnector, NodeAnswer na){
        sendLog(nodeConnector + " - received node answer from "
                + na.getOrigin() + " for JobID: " + na.getJobID());
    }

    /**
     * Node Task message
     * */
    public void receivedNodeTaskLog(String nodeConnector, String jobID){
        sendLog(nodeConnector + " - received node task with jobID: "+ jobID);
    }

    /**
     * Test the connection by sending a message
     * */
    public void testConnectionLog(String ip){
        sendLog("Node: " + ip + " connected. ");
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
