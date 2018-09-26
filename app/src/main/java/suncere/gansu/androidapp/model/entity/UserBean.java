package suncere.gansu.androidapp.model.entity;

/**
 * Created by Hjo on 2017/5/27.
 */

public class UserBean extends BaseBean {

//             "Result": "sample string 1",
//             "Token": "sample string 2",
//             "Level": "sample string 3",
//             "AreaCode": "sample string 4"

    private String Result;
    private String Token;
    private String Level;
    private String AreaCode;
    private String ParentID;

    @Override
    public String toString() {
        return "UserBean{" +
                "Result='" + Result + '\'' +
                ", Token='" + Token + '\'' +
                ", Level='" + Level + '\'' +
                ", AreaCode='" + AreaCode + '\'' +
                ", ParentID='" + ParentID + '\'' +
                '}';
    }

    public String getParentID() {
        return ParentID;
    }

    public void setParentID(String parentID) {
        ParentID = parentID;
    }



    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }
}
