package in.matka.ns.Model;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 23,June,2020
 */
public class PointsModel {
    String digit,point;

    public PointsModel() {
    }

    public PointsModel(String digit, String point) {
        this.digit = digit;
        this.point = point;
    }

    public String getDigit() {
        return digit;
    }

    public void setDigit(String digit) {
        this.digit = digit;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
