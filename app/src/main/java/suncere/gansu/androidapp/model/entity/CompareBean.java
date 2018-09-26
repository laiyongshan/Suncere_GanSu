package suncere.gansu.androidapp.model.entity;

/**
 * @author lys
 * @time 2018/8/27 18:27
 * @desc:
 */

public class CompareBean extends BaseBean {

    /**
     * AreaName : null
     * ItemName : SO2
     * CurrentData : 16
     * LastData : 16
     * DataChangePercent : 0%
     */

    private String AreaName;
    private String ItemName;
    private String CurrentData;
    private String LastData;
    private String DataChangePercent;



    public void setAreaName(String AreaName) {
        this.AreaName = AreaName;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public void setCurrentData(String CurrentData) {
        this.CurrentData = CurrentData;
    }

    public void setLastData(String LastData) {
        this.LastData = LastData;
    }

    public void setDataChangePercent(String DataChangePercent) {
        this.DataChangePercent = DataChangePercent;
    }

    public String getAreaName() {
        return AreaName;
    }

    public String getItemName() {
        return ItemName;
    }

    public String getCurrentData() {
        return CurrentData;
    }

    public String getLastData() {
        return LastData;
    }

    public String getDataChangePercent() {
        return DataChangePercent;
    }
}
