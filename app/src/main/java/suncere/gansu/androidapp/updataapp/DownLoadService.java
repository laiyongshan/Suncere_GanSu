package suncere.gansu.androidapp.updataapp;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import suncere.gansu.androidapp.R;
import suncere.gansu.androidapp.ui.BigImageDialog;
import suncere.gansu.androidapp.utils.FileUtils;
import suncere.gansu.androidapp.utils.MediaFile;


/**
 * Created by zs on 2016/7/8.
 */
public class DownLoadService extends Service {

    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "download";// M_DEFAULT_DIR

    /**
     * 目标文件存储的文件名
     */
    private String apkName = "suncere.apk";

    private Context mContext;
    private int preProgress = 0;
    private int NOTIFY_ID = 1000;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private Retrofit.Builder retrofit;
    private String DownloadType;
    private String downloadUrl;
    private String fileName;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;
        downloadUrl = intent.getStringExtra("downloadUrl");
        DownloadType = intent.getStringExtra("DownloadType");
        fileName=intent.getStringExtra("fileName");
        if (DownloadType.equals("apk")) {
//      http://110.185.210.239:85/UpdateApk/shuangliu_2.0.apk
//        downloadUrl="http://110.185.210.239:85/UpdateApk/shuangliu_2.0.apk";
            String apkurl = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
            String BaseUrl = downloadUrl.replace(apkurl, "");
            loadFile(BaseUrl, apkurl, apkName);
        } else {
            String fileurl = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
            loadFile("http://61.178.102.124:7200/AppServer/WebUpLoadFile/", fileurl, fileName);
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 下载文件
     */
    private void loadFile(String baseUrl, String apkurl, final String destFileName) {
        initNotification();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder();
        }

        retrofit.baseUrl(baseUrl)//   http://112.124.9.133:8080/parking-app-admin-1.0/android/manager/adminVersion/
                .client(initOkHttpClient())
                .build()
                .create(IFileLoad.class)
                .loadFile(apkurl)//downloadUrl
                .enqueue(new FileCallback(destFileDir, destFileName) {
                    @Override
                    public void onSuccess(File file) {
                        Log.e("hjo", "请求成功");
                        // 安装软件
                        cancelNotification();
                        if (DownloadType.equals("apk")) {
                            installApk(file);
                        } else {
                            if (MediaFile.isImgFileType(FileUtils.DIR_Name + destFileName)) {
                                BigImageDialog bigImageDialog = new BigImageDialog(getApplicationContext(), R.style.dialog, destFileName);
                                bigImageDialog.show();
                            } else {
                                FileUtils.openFile(mContext, destFileName);
                            }
                        }

                    }

                    @Override
                    public void onLoading(long progress, long total) {
                        Log.e("hjo", progress + "----" + total);
                        updateNotification(progress * 100 / total);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("hjo", "请求失败");
                        cancelNotification();
                    }
                });
    }

    public interface IFileLoad {
        //        http://106.37.208.233:8093/UpdateAPK/ZongZhan_1.2.4-release.apk
//        @Streaming
        @GET("{path}")
//UpdateApk/shuangliu_2.0.apk
        Call<ResponseBody> loadFile(@Path("path") String url); // @Url String url
    }

    /**
     * 安装软件
     *
     * @param file
     */
    private void installApk(File file) {
//        Uri uri = Uri.fromFile(file);
//        Intent install = new Intent(Intent.ACTION_VIEW);
//        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        install.setDataAndType(uri, "application/vnd.android.package-archive");
//        // 执行意图进行安装
//        mContext.startActivity(install);

//        File apkfile = new File(mSavePath, newAPKName);//mHashMap.get("name"));
//        if (!apkfile.exists())
//        {
//            return;
//        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileProvider", file);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
    }


    /**
     * 初始化OkHttpClient
     *
     * @return
     */
    private OkHttpClient initOkHttpClient() {
//        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(100000, TimeUnit.SECONDS);
//        builder .addInterceptor(httpLoggingInterceptor);
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse
                        .newBuilder()
                        .body(new FileResponseBody(originalResponse))
                        .build();
            }
        });
        return builder.build();
    }

    /**
     * 初始化Notification通知
     */
    public void initNotification() {
        builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("0%")
                .setContentTitle("APP更新")
                .setProgress(100, 0, false);
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

    /**
     * 更新通知
     */
    public void updateNotification(long progress) {
        int currProgress = (int) progress;
        if (preProgress < currProgress) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, (int) progress, false);
            notificationManager.notify(NOTIFY_ID, builder.build());
        }
        preProgress = (int) progress;
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        notificationManager.cancel(NOTIFY_ID);
    }
}
