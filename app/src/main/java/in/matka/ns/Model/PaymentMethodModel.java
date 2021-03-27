package in.matka.ns.Model;

public class PaymentMethodModel {
    String id,method,withdrwal,add;
    public PaymentMethodModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getWithdrwal() {
        return withdrwal;
    }

    public void setWithdrwal(String withdrwal) {
        this.withdrwal = withdrwal;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }
}
