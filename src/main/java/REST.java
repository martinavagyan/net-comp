/**
 * Created by jurgen on 17-3-17.
 */
import mq.Receive;
import mq.Send;
import spark.Route;
import org.json.simple.JSONObject;
import static spark.Spark.*;

public class REST {

    public REST() {
        get("/status", (req, res) ->  {
            JSONObject obj = new JSONObject();
            obj.put("status", "Feeling great");
            obj.put("temperature", "27");
            return obj.toJSONString();
        });
    }

    public static void main(String[] args) {
        new REST();
    }
}

