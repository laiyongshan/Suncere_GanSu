package suncere.gansu.androidapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IsConnectionNet {
	Context mContext;

	public IsConnectionNet(Context context) {
		this.mContext=context;
//		 isNetWorkAble();
	}

	public boolean isNetWorkAble(){
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager=(ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info==null) {//网络不可用
			return false;
		}else {
			return true;
		}
	}

	public  void setRefreshTime(String RefreshTime){
		SharedPreferences sharedPreferences = mContext.getSharedPreferences("isYesterDayXML", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("isYesterDay",RefreshTime);//默认是当前的更新时间
		editor.apply();
	}

	public  String getLastRefreshTime(){
		SharedPreferences sharedPreferences= mContext.getSharedPreferences("isYesterDayXML", Activity.MODE_PRIVATE);
		// 使用getString方法获得value，注意第2个参数是value的默认值
		String refalseTime =sharedPreferences.getString("isYesterDay","1990-01-01");
//		Log.e("hjo", refalseTime);
		return	refalseTime;
	}

	/***
	 * 比较两个时间的大小
	 * @param time
	 * @return
	 */
	public Boolean compareTime(String time){
		int days = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");
		try {
			Date  lastReflase=df.parse( getLastRefreshTime() );
			Date nowTime=df.parse( time );
			days = (int) Math.abs((nowTime.getTime() - lastReflase.getTime())/ (24 * 60 * 60 * 1000)) + 1;
//			Log.e("hjo", getIsFristTime() +"     "+ time+ "   时间对比："+days);
			if (days>1) {
				return true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	//测试是否连接上服务器
	public void  pingHost(){
//        String resault="不成功";
		String str="61.178.102.124";//61.178.102.124
		try {
			Process p = Runtime.getRuntime().exec("ping -c 1 -w 100 " +str);
			int status = p.waitFor();
			if (status == 0) {  //能连接服务器
//                resault="success";
//            	Toast.makeText(mContext, "连接上服务器！", Toast.LENGTH_LONG).show();
//            	return true;
			}
			else
			{
				Toast.makeText(mContext, "无法连接服务器！", Toast.LENGTH_LONG).show();
//                resault="faild";  
//            	return false;
			}
		} catch (IOException e) {
		} catch (InterruptedException e) {
		}

//        return false;  
	}


}
