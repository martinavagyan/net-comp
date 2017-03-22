package web;

import org.json.simple.JSONObject;
import static spark.Spark.*;

public class REST {

    public REST() {
        get("/api/status", (req, res) ->  {
            JSONObject obj = new JSONObject();
            obj.put("status", "Feeling great");
            obj.put("temperature", "27");
            return obj.toJSONString();
        });
    }
}

