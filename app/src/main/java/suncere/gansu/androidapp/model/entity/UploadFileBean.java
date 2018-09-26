package suncere.gansu.androidapp.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lys
 * @time 2018/9/20 14:31
 * @desc:
 */

public class UploadFileBean extends BaseBean {


    /**
     * Status : true
     * ErrorCode : 200
     * ErrorMessage : 上传成功
     * Message : 上传成功
     * Url :
     */

    private boolean Status;
    private int ErrorCode;
    private String ErrorMessage;
    private String Message;
    private String Url;

    public static UploadFileBean objectFromData(String str) {

        return new Gson().fromJson(str, UploadFileBean.class);
    }

    public static List<UploadFileBean> arrayUploadFileBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<UploadFileBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    public void setErrorCode(int ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public boolean getStatus() {
        return Status;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public String getMessage() {
        return Message;
    }

    public String getUrl() {
        return Url;
    }
}
