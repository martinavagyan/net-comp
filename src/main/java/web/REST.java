package web;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import tcp.AccessNode;

import static spark.Spark.*;

public class REST {

    private AccessNode accessNode;
    private int id_counter = 0;
    private ArrayList jobs;

    public REST() {
        jobs = new ArrayList();

        post("api/sort_list", (req, res) -> {
            JSONObject obj = new JSONObject();
            if (accessNode == null) {
                return setError(1, "There is no accessNode connected to this API");
            }

            String str_list_size = req.queryParams("size");
            int list_size = 0;
            try {
                list_size = Integer.parseInt(str_list_size);
            } catch (NumberFormatException nfe) {
                return setError(2, "Parameter 'size' should be an integer");
            }
            accessNode.addNewTask(list_size, id_counter);
            jobs.add(id_counter, "In progress");
            obj.put("job_id", id_counter);
            obj.put("job_task", "sort_list");
            obj.put("list_size", list_size);
            id_counter++;

            return obj.toJSONString();
        });

        get("api/status/check/:job_id", (req, res) -> {
            JSONObject obj = new JSONObject();
            if (accessNode == null) {
                return setError(1, "There is no accessNode connected to this API");
            }

            int job_id = 0;
            try {
                job_id = Integer.parseInt(req.params(":job_id"));
            } catch (NumberFormatException nfe) {
                return setError(2, "Parameter 'size' should be an integer");
            }

            if (job_id >= jobs.size()) {
                return setError(3, "There is no job recorded with index '" + job_id + "'");
            }

            obj.put("job_id", job_id);
            obj.put("status", jobs.get(job_id));

            return obj.toJSONString();
        });

        post("api/status/update/:job_id", (req, res) -> {
            JSONObject obj = new JSONObject();
            if (accessNode == null) {
                return setError(1, "There is no accessNode connected to this API");
            }

            int job_id = 0;
            try {
                job_id = Integer.parseInt(req.params(":job_id"));
            } catch (NumberFormatException nfe) {
                return setError(2, "Parameter 'size' should be an integer");
            }

            if (job_id >= jobs.size()) {
                return setError(3, "There is no job recorded with index '" + job_id + "'");
            }

            String status = req.queryParams("status");
            jobs.set(job_id, status);
            obj.put("job_id", job_id);
            obj.put("status", status);

            return obj.toJSONString();
        });
    }

    public JSONObject setError(int code, String message) {
        JSONObject obj = new JSONObject();
        obj.put("error", code);
        obj.put("message", message);
        return obj;
    }

    public void setAccessNode(AccessNode node)
    {
        this.accessNode = node;
    }
}

