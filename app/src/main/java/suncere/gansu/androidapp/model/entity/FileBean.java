package suncere.gansu.androidapp.model.entity;

/**
 * @author lys
 * @time 2018/9/19 17:06
 * @desc:
 */

public class FileBean extends BaseBean {

    /**
     * ID : 1
     * FileName : 2018_02_24_15_34_04.jpg
     * FilePath : null
     * NewFileName : AppFile201892017407142.jpg
     * CreateTime : 2018-09-20T17:40:07
     * State : 1
     */

    private String ID;
    private String FileName;
    private String FilePath;
    private String NewFileName;
    private String CreateTime;
    private String State;


    public void setID(String ID) {
        this.ID = ID;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public void setFilePath(String FilePath) {
        this.FilePath = FilePath;
    }

    public void setNewFileName(String NewFileName) {
        this.NewFileName = NewFileName;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getID() {
        return ID;
    }

    public String getFileName() {
        return FileName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public String getNewFileName() {
        return NewFileName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getState() {
        return State;
    }
}
