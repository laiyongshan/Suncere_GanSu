package suncere.gansu.androidapp.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Hjo on 2017/5/10.
 */
public class ApiNetManager {

//    public static final String  BASE_URL="http://111.75.227.199:8081/AppServer/api/";//"http://111.75.227.199:8081/AppServerTest/api/";
    //10.10.10.67 61.178.102.124:7200/AppServer/
    public static final String BASE_URL="http://61.178.102.124:7200/AppServer/api/";
    private static ApiNetManager apiNetManager;

    public static ApiNetManager getInstence(){
        if (apiNetManager==null){
            synchronized (ApiNetManager.class){
                if (apiNetManager==null)
                    apiNetManager=new ApiNetManager();
            }
        }
        return apiNetManager;
    }

    public static List<ApiNetManager> arrayApiNetManagerFromData(String str) {
        Type listType = new TypeToken<ArrayList<ApiNetManager>>() {
        }.getType();
        return new Gson().fromJson(str, listType);
    }

    /**
     * 获取 RetrofitSrevice
     * @return
     */
    //   public  <T> T  getRetrofitSrevice(Class<T> cla){
   public  RetrofitSrevice   getRetrofitSrevice(){
       //添加日志信息
       HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
       httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
       OkHttpClient okHttpClient=new OkHttpClient.Builder()
               .addInterceptor(httpLoggingInterceptor)
               .build();

      Retrofit retrofit=new Retrofit.Builder()
              .client(okHttpClient)
              .baseUrl(BASE_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
              .build();
       return retrofit.create(RetrofitSrevice.class);
   }
}
