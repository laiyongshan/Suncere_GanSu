package suncere.gansu.androidapp.utils;

import android.app.DownloadManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author lys
 * @time 2018/8/4 14:41
 * @desc:
 */

public class FileUtils {

    public static final String DIR_Name = Environment.DIRECTORY_DOWNLOADS + File.separator + "download/";

    /*
* 判断sdcard是否被挂载
*/
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    /**
     * 下载指定路径的文件，并写入到指定的位置
     *
     * @param fileName
     * @param urlStr
     * @return 返回0表示下载成功，返回1表示下载出错
     */
    public static int downloadFile(String fileName, String urlStr) {

        OutputStream output = null;
        //获取存储卡路径、构成保存文件的目标路径
        //SD卡具有读写权限、指定附件存储路径为SD卡上指定的文件夹

        try {
            //将字符串形式的path,转换成一个url
            URL url = new URL(urlStr);
            //得到url之后，将要开始连接网络，以为是连接网络的具体代码
            //首先，实例化一个HTTP连接对象conn
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //定义请求方式为GET，其中GET的大小写不要搞错了。
            conn.setRequestMethod("GET");
            //定义请求时间，在ANDROID中最好是不好超过10秒。否则将被系统回收。
            conn.setConnectTimeout(6 * 1000);
            //请求成功之后，服务器会返回一个响应码。如果是GET方式请求，服务器返回的响应码是200，post请求服务器返回的响应码是206（貌似）。
            if (conn.getResponseCode() == 200) {
                //返回码为真
                //从服务器传递过来数据，是一个输入的动作。定义一个输入流，获取从服务器返回的数据
                InputStream input = conn.getInputStream();
                File file = createFile(DIR_Name + fileName);
                if (!file.exists()) {
                    file.mkdirs();
                }

                output = new FileOutputStream(file);
                //读取大文件
                byte[] buffer = new byte[1024];
                //记录读取内容
                int n = input.read(buffer);
                //写入文件
                output.write(buffer, 0, n);
                n = input.read(buffer);
                output.flush();
                input.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null)
                    output.close();
                System.out.println("success");
                return 1;
            } catch (IOException e) {
                System.out.println("fail");
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static void downloadFile2(Context context, String fileName, String urlStr) {
        DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(urlStr);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //指定下载路径和下载文件名
//        request.setDestinationInExternalPublicDir("", urlStr.substring(urlStr.lastIndexOf("/") + 1));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setMimeType("application/vnd.android.package-archive");
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        request.setAllowedOverRoaming(false);
        request.setTitle(fileName);//设置Notification的title信息
        //设置文件存放目录
//        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "mydown");
        // 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        long refernece = dManager.enqueue(request);
    }


    /**
     * 在SD卡的指定目录上创建文件
     *
     * @param fileName
     */
    public static File createFile(String fileName) {
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 判断文件是否已经存在
     */
    public static boolean fileIsExit(String fileName) {
        String SDPATH = Environment.DIRECTORY_DOWNLOADS;
        //file:///storage/emulated/0/storage/emulated/0/laiyongshan20180705T165222-5.amr
        // /storage/emulated/0/storage/emulated/0/laiyongshan20180705T165222-7.amr
        try {
            File f = new File("/storage/emulated/0/storage/emulated/0/"+fileName);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    //打开指定文件夹
    public static void openFile(Context context, String fileName) {

        File file = new File(DIR_Name + fileName);
        //获取父目录
        File parentFlie = new File(file.getParent());

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "*/*");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        context.startActivity(intent);


    }

}
