package tcp;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import jdk.nashorn.internal.codegen.CompilerConstants;
import mq.CallBack;
import mq.MessageQueue;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class TaskManager implements Runnable, CallBack {
    private MessageQueue mq;
    private NodeConnector nc; // own node connector, used for identification when logging
    private String host = "http://localhost:4567/";

    public TaskManager (NodeConnector nc) {
        this.nc = nc;
        mq = new MessageQueue(); // hard-coded capacity
    }

    public synchronized boolean addNodeJob(NodeJob nj) {
        return this.mq.offer(nj);
    }

    @Override
    public void run() {
        mq.listen(this);
    }

    public void runTask(NodeJob nj) {
        System.out.println("Running task with ID: " + nj.getJobID());
        nj.getTask().execute();
        // update webservice that job is done
        System.out.println("Finished job with ID: " + nj.getJobID());

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

    //TODO
    public long getDelay() {
        return mq.size()*100;
    }
}
