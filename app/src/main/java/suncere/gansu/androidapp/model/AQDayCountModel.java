package suncere.gansu.androidapp.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import suncere.gansu.androidapp.model.entity.BaseBean;

/**
 * @author lys
 * @time 2018/8/30 14:49
 * @desc:
 */

public class AQDayCountModel extends BaseBean {


    /**
     * CityName : 兰州市
     * TimeRangeValue : 2018,8
     * TimeRange : 2
     * G1 : 2
     * G2 : 19
     * G3 : 8
     * G4 : 0
     * G5 : 0
     * G6 : 0
     * G12Per : 72.4
     * G1Per : 6.9
     * G2Per : 65.5
     * G3Per : 27.6
     * G4Per : 0.0
     * G5Per : 0.0
     * G6Per : 0.0
     */

    private String CityName;
    private String TimeRangeValue;
    private int TimeRange;
    private int G1;
    private int G2;
    private int G3;
    private int G4;
    private int G5;
    private int G6;
    private double G12Per;
    private double G1Per;
    private double G2Per;
    private double G3Per;
    private double G4Per;
    private double G5Per;
    private double G6Per;

    public static List<AQDayCountModel> arrayAQDayCountModelFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<AQDayCountModel>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public void setTimeRangeValue(String TimeRangeValue) {
        this.TimeRangeValue = TimeRangeValue;
    }

    public void setTimeRange(int TimeRange) {
        this.TimeRange = TimeRange;
    }

    public void setG1(int G1) {
        this.G1 = G1;
    }

    public void setG2(int G2) {
        this.G2 = G2;
    }

    public void setG3(int G3) {
        this.G3 = G3;
    }

    public void setG4(int G4) {
        this.G4 = G4;
    }

    public void setG5(int G5) {
        this.G5 = G5;
    }

    public void setG6(int G6) {
        this.G6 = G6;
    }

    public void setG12Per(double G12Per) {
        this.G12Per = G12Per;
    }

    public void setG1Per(double G1Per) {
        this.G1Per = G1Per;
    }

    public void setG2Per(double G2Per) {
        this.G2Per = G2Per;
    }

    public void setG3Per(double G3Per) {
        this.G3Per = G3Per;
    }

    public void setG4Per(double G4Per) {
        this.G4Per = G4Per;
    }

    public void setG5Per(double G5Per) {
        this.G5Per = G5Per;
    }

    public void setG6Per(double G6Per) {
        this.G6Per = G6Per;
    }

    public String getCityName() {
        return CityName;
    }

    public String getTimeRangeValue() {
        return TimeRangeValue;
    }

    public int getTimeRange() {
        return TimeRange;
    }

    public int getG1() {
        return G1;
    }

    public int getG2() {
        return G2;
    }

    public int getG3() {
        return G3;
    }

    public int getG4() {
        return G4;
    }

    public int getG5() {
        return G5;
    }

    public int getG6() {
        return G6;
    }

    public double getG12Per() {
        return G12Per;
    }

    public double getG1Per() {
        return G1Per;
    }

    public double getG2Per() {
        return G2Per;
    }

    public double getG3Per() {
        return G3Per;
    }

    public double getG4Per() {
        return G4Per;
    }

    public double getG5Per() {
        return G5Per;
    }

    public double getG6Per() {
        return G6Per;
    }
}
