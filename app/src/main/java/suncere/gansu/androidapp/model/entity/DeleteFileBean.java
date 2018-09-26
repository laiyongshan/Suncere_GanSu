package suncere.gansu.androidapp.model.entity;

/**
 * @author lys
 * @time 2018/9/20 15:51
 * @desc:
 */

public class DeleteFileBean extends BaseBean {


    /**
     * Status : true
     * ErrorCode : 200
     * ErrorMessage : 操作成功
     * Message : 操作成功
     * Url :
     */

    private boolean Status;
    private int ErrorCode;
    private String ErrorMessage;
    private String Message;
    private String Url;

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
