package suncere.gansu.androidapp.presenter;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import suncere.gansu.androidapp.api.ApiNetCallBack;
import suncere.gansu.androidapp.model.entity.DeleteFileBean;
import suncere.gansu.androidapp.model.entity.FileBean;
import suncere.gansu.androidapp.model.entity.UploadFileBean;
import suncere.gansu.androidapp.ui.MyApplication;
import suncere.gansu.androidapp.ui.iview.IFileView;
import suncere.gansu.androidapp.utils.NetWorkUtil;

/**
 * @author lys
 * @time 2018/9/18 10:34
 * @desc:
 */

public class FileManagerPresenter extends BasePresenter<IFileView> {

    public FileManagerPresenter(IFileView iView) {
        attrchIView(iView);
    }

    String mKey = "FileManager";

    //获取已上传的文件列表
    public void getFileList() {
        mIView.showRefresh();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())) {
            addSubscription(mRetrofitService.getFileInfo("", "", ""), new ApiNetCallBack<List<FileBean>>() {
                @Override
                public void onSuccess(List<FileBean> response) {
                    mIView.getFileListSuccess(response);
                }

                @Override
                public void onError(String msg) {
                    mIView.getDataFail(msg);
                }

                @Override
                public void onFinish() {
                    mIView.finishRefresh();
                }
            });
        } else {
            mIView.getDataFail("无网络连接！");
            mIView.finishRefresh();
        }
    }


    //上传文件
    public void UploadFile(File... files) {

        mIView.showUploadFileLoading();

        //组装partMap对象
        Map<String, RequestBody> partMap = new HashMap<>();
        for (File file : files) {
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
            partMap.put("file\"; filename=\"" + file.getName() + "\"", fileBody);
        }

        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())) {
            addSubscription(mRetrofitService.uploadFile(partMap), new ApiNetCallBack<UploadFileBean>() {
                @Override
                public void onSuccess(UploadFileBean response) {
                    mIView.uploadFileSuccess(response);
                    getFileList();
                }

                @Override
                public void onError(String msg) {
                    mIView.uploadFileFail(msg);
                }

                @Override
                public void onFinish() {
                    mIView.finishiUploadFile();
                }
            });
        } else {
            mIView.getDataFail("无网络连接！");
            mIView.finishiUploadFile();
        }
    }


    //获取下载文件的Url
    public void downFile(String fileUrl) {
        mIView.showRefresh();


        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())) {
            mRetrofitService.downFile(fileUrl).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .map(new Func1<ResponseBody, InputStream>() {

                        @Override
                        public InputStream call(ResponseBody responseBody) {
                            return responseBody.byteStream();
                        }
                    })
                    .observeOn(Schedulers.computation()) // 用于计算任务
                    .doOnNext(new Action1<InputStream>() {
                        @Override
                        public void call(InputStream inputStream) {
//                            writeFile(inputStream, filePath);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<InputStream>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(InputStream inputStream) {

                        }
                    });
        } else {
            mIView.getDataFail("无网络连接！");
            mIView.finishDownFile();
        }
    }


    //删除文件
    public void deleteFile(String IDs) {
        mIView.showDeleteFileLoading();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())) {
            addSubscription(mRetrofitService.deleteById(IDs), new ApiNetCallBack<DeleteFileBean>() {
                @Override
                public void onSuccess(DeleteFileBean response) {
                    mIView.deleteFileSuccess(response);
                    getFileList();
                }

                @Override
                public void onError(String msg) {
                    mIView.deleteFileFail(msg);
                }

                @Override
                public void onFinish() {
                    mIView.finishiDeleteFile();
                }
            });
        } else {
            mIView.getDataFail("无网络连接！");
            mIView.finishiDeleteFile();
        }
    }

}
