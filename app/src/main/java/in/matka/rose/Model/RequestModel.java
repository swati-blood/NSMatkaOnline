package in.matka.rose.Model;

public class RequestModel {
   String request_id;
   String request_points;
   String time;
   String user_id;
   String type;
   String request_status;

    public RequestModel() {
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getRequest_points() {
        return request_points;
    }

    public void setRequest_points(String request_points) {
        this.request_points = request_points;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }
}
