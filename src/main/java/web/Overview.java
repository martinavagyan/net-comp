package web;

import org.json.simple.JSONObject;
import static spark.Spark.*;

/**
 * Created by jurgen on 22-3-17.
 */
public class Overview {
    public Overview() {
        get("/", (req, res) -> {
            String html = "<h1> ITS WORKING </h1>";
            return html;
        });
    }
}
