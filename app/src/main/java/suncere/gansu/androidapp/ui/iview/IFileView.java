package suncere.gansu.androidapp.ui.iview;

/**
 * @author lys
 * @time 2018/9/20 16:32
 * @desc:
 */

public interface IFileView extends IBaseView {

    void getFileListSuccess(Object respon);

    void showUploadFileLoading();
    void uploadFileSuccess(Object msg);
    void uploadFileFail(String msg);
    void finishiUploadFile();

    void showDeleteFileLoading();
    void deleteFileSuccess(Object respon);
    void deleteFileFail(String msg);
    void finishiDeleteFile();

    void showDownFileLoading();
    void downFileSuccess(Object respon);
    void onProgress(int currentLength);
    void downFileFail(String msg);
    void finishDownFile();

}
