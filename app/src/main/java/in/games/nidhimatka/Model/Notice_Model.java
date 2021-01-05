package in.games.nidhimatka.Model;

public class Notice_Model {

    String title;
    String detail;
    public Notice_Model(String title,String detail) {

        this.title = title;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

}
