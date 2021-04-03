package in.matka.ns.Model;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 03,April,2021
 */
public class EarnCommisionModel {
    String id,referred_code,refer_by,refer_to,refer_amount,status,created_at,name;

    public EarnCommisionModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferred_code() {
        return referred_code;
    }

    public void setReferred_code(String referred_code) {
        this.referred_code = referred_code;
    }

    public String getRefer_by() {
        return refer_by;
    }

    public void setRefer_by(String refer_by) {
        this.refer_by = refer_by;
    }

    public String getRefer_to() {
        return refer_to;
    }

    public void setRefer_to(String refer_to) {
        this.refer_to = refer_to;
    }

    public String getRefer_amount() {
        return refer_amount;
    }

    public void setRefer_amount(String refer_amount) {
        this.refer_amount = refer_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
