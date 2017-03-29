package web;

import static spark.Spark.*;

public class Overview {
    public Overview() {
        get("/", (req, res) -> {
            String html = "<h1> ITS WORKING </h1>";
            return html;
        });
    }
}
