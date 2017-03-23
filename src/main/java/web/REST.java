package web;

import org.json.simple.JSONObject;
import static spark.Spark.*;

public class REST {

    public REST() {

        post("api/sort_list", (req, res) -> {
            JSONObject obj = new JSONObject();
            String list_size = req.queryParams("size");
            obj.put("job_id", 1);
            obj.put("job_task", "sort_list");
            obj.put("list_size", list_size);
            return obj.toJSONString();
        });

        get("api/status/check/:job_id", (req, res) -> {
            JSONObject obj = new JSONObject();
            int job_id = Integer.parseInt(req.params(":job_id"));
            obj.put("job_id", job_id);
            obj.put("status", "unknown");
            return obj.toJSONString();
        });

        post("api/status/update/:job_id", (req, res) -> {
            JSONObject obj = new JSONObject();
            int job_id = Integer.parseInt(req.params(":job_id"));
            String status = req.queryParams("status");
            obj.put("job_id", job_id);
            obj.put("status", status);
            return obj.toJSONString();
        });
    }
}

