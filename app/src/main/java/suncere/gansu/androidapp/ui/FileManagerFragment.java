package suncere.gansu.androidapp.ui;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.adapter.FileAdapter;
import suncere.gansu.androidapp.customview.LoginProgressDialog;
import suncere.gansu.androidapp.customview.SuccessProgressDialog;
import suncere.gansu.androidapp.model.entity.DeleteFileBean;
import suncere.gansu.androidapp.model.entity.FileBean;
import suncere.gansu.androidapp.model.entity.UploadFileBean;
import suncere.gansu.androidapp.presenter.FileManagerPresenter;
import suncere.gansu.androidapp.ui.iview.IFileView;
import suncere.gansu.androidapp.utils.ColorUtils;
import suncere.gansu.androidapp.utils.FileUtils;
import suncere.gansu.androidapp.utils.MediaFile;

/**
 * @author lys
 * @time 2018/9/19 10:14
 * @desc:
 */

public class FileManagerFragment extends MvpFragment<FileManagerPresenter> implements IFileView, SwipeRefreshLayout.OnRefreshListener {

    FileManagerPresenter mFileManagerPresenter;

    @BindView(R.id.title_tv)
    TextView title_tv;

    @BindView(R.id.option_tv)
    TextView option_tv;

    @BindView(R.id.file_rv)
    RecyclerView file_rv;

    @BindView(R.id.list_emptyText)
    LinearLayout list_emptyText;

    @BindView(R.id.creat_new_upload_rtv)
    RoundTextView creat_new_upload_rtv;

    @BindView(R.id.delete_choose_rtv)
    RoundTextView delete_choose_rtv;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;

    LoginProgressDialog mLoginProgressDialog;
    SuccessProgressDialog mSuccessProgressDialog;

    public static final int CHOOSE_FILE_REQUEST_CODE = 22;

    FileAdapter mFileAdapter;
    List<FileBean> fileBeanList = new ArrayList<>();
    boolean isOption;

//    private DownLoadCompleteReceiver receiver;

    private View view;

    @Override
    protected FileManagerPresenter createPresenter() {
        mFileManagerPresenter = new FileManagerPresenter(this);
        return mFileManagerPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_file_manager, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

//        IntentFilter filter = new IntentFilter();
//        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
//        receiver = new DownLoadCompleteReceiver();
//        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mFileManagerPresenter.getFileList();
    }



    public void initView() {

        mLoginProgressDialog=new LoginProgressDialog(getActivity());
        mLoginProgressDialog.setProgressText("正在上传");
        mSuccessProgressDialog=new SuccessProgressDialog(getActivity());


        file_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFileAdapter = new FileAdapter(getActivity(),fileBeanList, isOption,mFileManagerPresenter);
        mFileAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (FileUtils.fileIsExit(fileBeanList.get(position).getFileName())) {//文件已存在  查看
                    if (MediaFile.isImgFileType(FileUtils.DIR_Name + fileBeanList.get(position).getFileName())) {
                        BigImageDialog bigImageDialog = new BigImageDialog(getActivity(), R.style.dialog, fileBeanList.get(position).getFilePath());
                        bigImageDialog.show();
                    } else {
                        FileUtils.openFile(getActivity(), fileBeanList.get(position).getFileName());
                    }
                } else {//文件未存在 下载
                }

            }
        });

        refresh_layout.setColorSchemeColors(ColorUtils.Colors);
        refresh_layout.setOnRefreshListener(this);
    }

    @OnClick({R.id.option_tv, R.id.creat_new_upload_rtv, R.id.delete_choose_rtv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.option_tv:
                isOption = !isOption;
                if (isOption) {
                    creat_new_upload_rtv.setVisibility(View.GONE);
                    delete_choose_rtv.setVisibility(View.VISIBLE);
                    option_tv.setText("完成");
                } else {
                    creat_new_upload_rtv.setVisibility(View.VISIBLE);
                    delete_choose_rtv.setVisibility(View.GONE);
                    option_tv.setText("管理");
                }

                mFileAdapter = new FileAdapter(getActivity(), fileBeanList, isOption,mFileManagerPresenter);
                file_rv.setAdapter(mFileAdapter);
                break;

            case R.id.creat_new_upload_rtv://新建上传
                selectFile();
                break;

            case R.id.delete_choose_rtv://批量删除

                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_FILE_REQUEST_CODE){//22选择文件的请求码
            if (data != null) {
                if (resultCode == -1) {
                    if (data.getData() == null) {
                        return;
                    }
                    if (!FileUtils.hasSdcard()) {
                        Toast.makeText(getActivity(), "SD卡不可用,请检查", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Uri uri = data.getData();
                    String path = FileUtils.getPath(getActivity(), uri);

                    File file = new File(path);
                    mFileManagerPresenter.UploadFile(file);
                    Log.i("ok", "选择的文件路径为：" + file.getAbsolutePath() + file.getTotalSpace());
//                    upFile(file);
//                    showCustomToast("文件上传成功");
                }
            }
        }
    }


    /**
     * 通过手机选择文件
     */
    public void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "选择文件上传"), FileManagerFragment.CHOOSE_FILE_REQUEST_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "请安装一个文件管理器", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRefresh() {
        mFileManagerPresenter.getFileList();
    }


    @Override
    public void getDataFail(String msg) {
        Toast.makeText(getActivity(), msg.toString() + "", Toast.LENGTH_SHORT).show();
        refresh_layout.setRefreshing(false);
    }

    @Override
    public void showRefresh() {
        refresh_layout.setRefreshing(true);
    }

    @Override
    public void finishRefresh() {
        refresh_layout.setRefreshing(false);
    }

    @Override
    public void getFileListSuccess(Object respon) {
        if (respon != null) {
            fileBeanList = (List<FileBean>) respon;
            if (fileBeanList != null && !fileBeanList.isEmpty()) {
                mFileAdapter.setNewData(fileBeanList);
                mFileAdapter.notifyDataSetChanged();
                file_rv.setAdapter(mFileAdapter);
                list_emptyText.setVisibility(View.INVISIBLE);
            } else {
                file_rv.setVisibility(View.INVISIBLE);
                list_emptyText.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void showUploadFileLoading() {
        mLoginProgressDialog.showDialog();
    }

    @Override
    public void uploadFileSuccess(Object respone) {
        if(((UploadFileBean)respone).getStatus()) {
//            mFileManagerPresenter.getFileList();
            mSuccessProgressDialog.showDialog();
        }else{
            Toast.makeText(getActivity(),  "上传失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void uploadFileFail(String msg) {
        mLoginProgressDialog.dismiss();

    }

    @Override
    public void finishiUploadFile() {
        mLoginProgressDialog.dismiss();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mSuccessProgressDialog.isShowing())
                    mSuccessProgressDialog.dismiss();
            }
        },1000);

    }

    @Override
    public void showDeleteFileLoading() {

    }

    @Override
    public void deleteFileSuccess(Object respon) {
        if(respon!=null){
            DeleteFileBean deleteFileBean= (DeleteFileBean) respon;
            if(deleteFileBean.getStatus())
                mFileManagerPresenter.getFileList();
            else
                Toast.makeText(getActivity(),""+deleteFileBean.getErrorMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void deleteFileFail(String msg) {
        Toast.makeText(getActivity(),""+msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishiDeleteFile() {

    }

    @Override
    public void showDownFileLoading() {

    }

    @Override
    public void downFileSuccess(Object respon) {

    }

    @Override
    public void onProgress(int currentLength) {

    }

    @Override
    public void downFileFail(String msg) {

    }

    @Override
    public void finishDownFile() {

    }


    private class DownLoadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Toast.makeText(getActivity(), "下载任务已经完成！", Toast.LENGTH_SHORT).show();
            }else if(intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)){
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        getActivity().unregisterReceiver(receiver);
    }
}
