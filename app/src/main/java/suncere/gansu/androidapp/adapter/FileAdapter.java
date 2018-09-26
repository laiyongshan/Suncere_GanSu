package suncere.gansu.androidapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.model.entity.FileBean;
import suncere.gansu.androidapp.presenter.FileManagerPresenter;
import suncere.gansu.androidapp.ui.BigImageDialog;
import suncere.gansu.androidapp.utils.DownloadUtils;
import suncere.gansu.androidapp.utils.FileUtils;
import suncere.gansu.androidapp.utils.MediaFile;

/**
 * @author lys
 * @time 2018/9/19 17:05
 * @desc:
 */

public class FileAdapter extends BaseQuickAdapter<FileBean, BaseViewHolder> {

    boolean isOption;
    FileManagerPresenter fileManagerPresenter;
    DownloadUtils downloadUtils;

    Context context;

    public FileAdapter(Context context, List<FileBean> data, boolean isOption, FileManagerPresenter fileManagerPresenter) {
        super(R.layout.item_file_rv, data);
        this.isOption = isOption;
        this.fileManagerPresenter = fileManagerPresenter;
        this.context = context;
        downloadUtils=new DownloadUtils(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, final FileBean item) {
        ((TextView) helper.getView(R.id.serial_number_tv)).setText((helper.getPosition() + 1) + "");
        ((TextView) helper.getView(R.id.file_name_tv)).setText(item.getFileName() + "");
        ((TextView) helper.getView(R.id.file_uptime_tv)).setText(item.getCreateTime().replace("T", "\n") + "");

        ((ImageView) helper.getView(R.id.delete_file_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("提示")
                        .setMessage("确定要删除\"" + item.getFileName() + "\"?")
                        .setNeutralButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                dialog.dismiss();
                                fileManagerPresenter.deleteFile(item.getID());
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        if (FileUtils.fileIsExit(item.getFileName())) {//文件已存在  查看
            ((ImageView) helper.getView(R.id.down_file_iv)).setImageResource(R.mipmap.icon_check);
        } else {//文件未存在 下载
            ((ImageView) helper.getView(R.id.down_file_iv)).setImageResource(R.drawable.download_icon_bg);
        }

        ((ImageView) helper.getView(R.id.down_file_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("lys",item.getFileName()+"是否存在："+FileUtils.fileIsExit(item.getFileName()));
                if (FileUtils.fileIsExit(item.getFileName())) {//文件已存在  查看
                    if (MediaFile.isImgFileType(FileUtils.DIR_Name + item.getFileName())) {
                        BigImageDialog bigImageDialog = new BigImageDialog(context, R.style.dialog, item.getFilePath());
                        bigImageDialog.show();
                    } else {
                        FileUtils.openFile(context, item.getFileName());
                    }
                } else {//文件未存在 下载

//                    Intent intent=   new Intent(mContext, DownLoadService.class);
//                    intent.putExtra("downloadUrl", ApiNetManager.BASE_URL + item.getFilePath());
//                    intent.putExtra("DownloadType","file");
//                    intent.putExtra("fileName",item.getFileName());
//                    mContext.startService(intent);


//                    FileUtils.downloadFile2(context,item.getFileName(), "http://61.178.102.124:7200/AppServer/" + item.getFilePath());

                    downloadUtils.downloadFile("http://61.178.102.124:7200/AppServer/" + item.getFilePath(),item.getFileName());

                    new AsyncTask<Void, Integer, Integer>() {
                        @Override
                        protected Integer doInBackground(Void... voids) {
                            return FileUtils.downloadFile(item.getFileName(), "http://61.178.102.124:7200/AppServer/" + item.getFilePath());
                        }

                        @Override
                        protected void onPostExecute(Integer result) {
                            super.onPostExecute(result);
                            if (result == 1) {
                                if (MediaFile.isImgFileType(FileUtils.DIR_Name + item.getFileName())) {
                                    BigImageDialog bigImageDialog = new BigImageDialog(context, R.style.dialog, item.getFilePath());
                                    bigImageDialog.show();
                                } else {
                                    FileUtils.openFile(context, item.getFileName());
                                }
                            }else{//下载出错

                            }

                        }
                    }.execute();


//                    if ( == 0) {//下载成功
//                        if (MediaFile.isImgFileType(FileUtils.DIR_Name + item.getFileName())) {
//                            BigImageDialog bigImageDialog = new BigImageDialog(context, R.style.dialog, item.getFilePath());
//                            bigImageDialog.show();
//                        } else {
//                            FileUtils.openFile(context, item.getFileName());
//                        }
//                    }else{//下载出错
//
//                    }
                }
            }
        });

        if (isOption)
            ((CheckBox) helper.getView(R.id.file_selected_cb)).setVisibility(View.VISIBLE);
        else
            ((CheckBox) helper.getView(R.id.file_selected_cb)).setVisibility(View.GONE);
    }
}
