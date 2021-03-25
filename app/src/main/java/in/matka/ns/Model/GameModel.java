package in.matka.ns.Model;

public class GameModel {
    String id ;
    String name ;
    int img ;
    String type ;
    boolean is_disable;

    public GameModel(String id, String name, int img, String type, boolean is_disable) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.type = type;
        this.is_disable = is_disable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isIs_disable() {
        return is_disable;
    }

    public void setIs_disable(boolean is_disable) {
        this.is_disable = is_disable;
    }
}
