package tcp;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TaskManager implements Runnable{
    private ArrayBlockingQueue<NodeJob> nodeJobQueue;
    private NodeConnector nc; // own node connector, used for identification when logging
    private String host = "http://localhost:4567/";

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
                System.out.print("Finished job with ID: " + nj.getJobID());

                HttpClient httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost(host + "api/status/update/" + nj.getJobID());

                List<NameValuePair> params = new ArrayList<NameValuePair>(1);
                params.add(new BasicNameValuePair("status", "Finished"));
                try {
                    httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        String result = entity.getContent().toString();
                        System.out.println(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public long getDelay() {
        return nodeJobQueue.size()*100; // hard-coded for now
    }
}
