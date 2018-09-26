package suncere.gansu.androidapp.model.entity;

/**
 * Created by Hjo on 2017/5/10.
 */
public class AllCityBean extends BaseBean {
    /**
     * AreaCode : 360100
     * AreaName : 南昌市
     * ParentID : 360000
     * Level : 2
     * OrderID : 1
     */
    private String AreaCode;
    private String AreaName;
    private String ParentID;
    private String Level;
    private String OrderID;

    @Override
    public String toString() {
        return "AllCityBean{" +
                "AreaCode='" + AreaCode + '\'' +
                ", AreaName='" + AreaName + '\'' +
                ", ParentID='" + ParentID + '\'' +
                ", Level='" + Level + '\'' +
                ", OrderID='" + OrderID + '\'' +
                '}';
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String AreaCode) {
        this.AreaCode = AreaCode;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String AreaName) {
        this.AreaName = AreaName;
    }

    public String getParentID() {
        return ParentID;
    }

    public void setParentID(String ParentID) {
        this.ParentID = ParentID;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String Level) {
        this.Level = Level;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String OrderID) {
        this.OrderID = OrderID;
    }
}
