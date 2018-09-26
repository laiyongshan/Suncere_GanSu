package suncere.gansu.androidapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import suncere.gansu.androidapp.model.entity.UserBean;

/**
 * Created by Hjo on 2017/5/27.
 */

public class Tools {

    Context mContext;
    public Tools(Context context){
        mContext=context;
    }

    public void setSaveUserNameAndPsw(String userName,String psw){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("LoginXML", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName",userName);
        editor.putString("psw",psw);
        editor.apply();
    }
    public String [] getUserNameAndPsw(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("LoginXML", Activity.MODE_PRIVATE);
        String [] result=new String [2];
        String AreaCode=sharedPreferences.getString("userName","admin");
        String Level=sharedPreferences.getString("psw","123");
        result[0]=AreaCode;
        result[1]=Level;
        return result;
    }



    //
    public  void setSaveYesTerdal(String Yesterdaltime){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("SaveYesTerdalXML", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Yesterdaltime",Yesterdaltime);
        editor.apply();
    }

    public  String getSaveYesTerdal(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("SaveYesTerdalXML", Activity.MODE_PRIVATE);
        String userName=sharedPreferences.getString("Yesterdaltime", "---- -- -- ");
        return userName;
    }


    public void setCompareSelectedCity(String compareSelectedCity){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("CompareSelectedCityXML", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("compareSelectedCity",compareSelectedCity);
        editor.apply();
    }

    public  String getCompareSelectedCity(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("CompareSelectedCityXML", Activity.MODE_PRIVATE);
        String compareSelectedCity=sharedPreferences.getString("compareSelectedCity", "");
        return compareSelectedCity;
    }


//    public  void setPsw(String psw){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences("LoginPswXML", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("psw",psw);
//        editor.apply();
//    }
//
//    public  String getPsw(){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences("LoginPswXML", Activity.MODE_PRIVATE);
//        String psw=sharedPreferences.getString("psw", "123");
//        return psw;
//    }
//
//
//    public void isLogin(boolean islogin){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences("isLoginXML", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("islogin", islogin);
//        editor.apply();
//    }
//    public boolean  getisLogin(){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences("isLoginXML", Activity.MODE_PRIVATE);
//        boolean  islogin=sharedPreferences.getBoolean("islogin", false);
//        return islogin;
//    }
//
//
//    public void isSavePsw (boolean islogin){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences("isisSavePswXML", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("isSavePsw", islogin);
//        editor.apply();
//    }
//    public boolean  getisSavePsw(){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences("isisSavePswXML", Activity.MODE_PRIVATE);
//        boolean  islogin=sharedPreferences.getBoolean("isSavePsw", false);
//        return islogin;
//    }

    public void setUserBean(UserBean userBean){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("SaveUserBeanXML", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("AreaCode",userBean.getAreaCode());
        editor.putString("ParentID",userBean.getParentID());
        editor.putString("Level",userBean.getLevel().equals("3")? userBean.getLevel(): "2");
        editor.putString("Result",userBean.getResult());
        editor.putString("Token",userBean.getToken());
        editor.apply();
    }

    public String [] getAreaCodeAndLevel(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("SaveUserBeanXML", Activity.MODE_PRIVATE);
        String [] result=new String [4];
        String AreaCode=sharedPreferences.getString("AreaCode","");
        String Level=sharedPreferences.getString("Level","");
        String ParentID=sharedPreferences.getString("ParentID","");
        String Result=sharedPreferences.getString("Result","99999");// 为1时代表登录成功
        result[0]=AreaCode;
        result[1]=Level;
        result[2]=ParentID;
        result[3]=Result;
        return result;

    }


        public void setPageIndex (int index ){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("homepageXML", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("index", index);
        editor.apply();
    }
    public int  getPageIndex(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("homepageXML", Activity.MODE_PRIVATE);
        int   pageindex=sharedPreferences.getInt("index", 0);
        return pageindex;
    }

        public void setishadLogin(boolean islogin){
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("SaveUserBeanXML", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Result", "99999");
            editor.apply();
    }
    public boolean  getishadLogin(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("SaveUserBeanXML", Activity.MODE_PRIVATE);
        if (sharedPreferences.getString("Result","9999").equals("1"))
            return true;
        else
            return false;

    }

}
