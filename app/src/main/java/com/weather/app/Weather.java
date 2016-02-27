package com.weather.app;

import android.graphics.Bitmap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yaroslav on 27-Feb-16.
 */
public class Weather {
    String iconId;
    long timeUpd;
    Double temp;
    Double maxTemp;
    Double minTemp;
    String descr;
    String detail;
    Double speed;
    Double degree;
    Double pressure;
    int humidity;
    int clouds;
    long sunUp;
    long sunDn;
    String city;
    String country;
    Double seaLvl;
    Double grndLvl;
    Double longitude;
    Double latitude;


    Weather() {

    }

    public void parseWeather(JSONObject json) {

        try {
            JSONObject mainJson = json.getJSONObject("main");
            JSONObject coordJson = json.getJSONObject("coord");
            JSONObject windJson = json.getJSONObject("wind");
            JSONObject cloudsJson = json.getJSONObject("clouds");
            JSONObject sysJson = json.getJSONObject("sys");
            JSONArray weatherJsonArr = json.getJSONArray("weather");

            latitude = coordJson.getDouble("lat");
            longitude = coordJson.getDouble("lon");

            descr = weatherJsonArr.getJSONObject(0).getString("main");
            detail = weatherJsonArr.getJSONObject(0).getString("description");
            iconId = weatherJsonArr.getJSONObject(0).getString("icon");

            temp = mainJson.getDouble("temp");
            maxTemp = mainJson.getDouble("temp_max");
            minTemp = mainJson.getDouble("temp_min");
            pressure = mainJson.getDouble("pressure");
            humidity = mainJson.getInt("humidity");

            speed = windJson.getDouble("speed");

            clouds = cloudsJson.getInt("all");

            country = sysJson.getString("country");
            sunUp = sysJson.getLong("sunrise");
            sunDn = sysJson.getLong("sunset");

            timeUpd = json.getLong("dt");
            city = json.getString("name");

            try {
                degree = windJson.getDouble("deg");
                seaLvl = mainJson.getDouble("sea_level");
                grndLvl = mainJson.getDouble("grnd_level");
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public long getTimeUpd() {
        return timeUpd;
    }

    public void setTimeUpd(long timeUpd) {
        this.timeUpd = timeUpd;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDegree() {
        return degree;
    }

    public void setDegree(Double degree) {
        this.degree = degree;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public long getSunUp() {
        return sunUp;
    }

    public void setSunUp(long sunUp) {
        this.sunUp = sunUp;
    }

    public long getSunDn() {
        return sunDn;
    }

    public void setSunDn(long sunDn) {
        this.sunDn = sunDn;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getSeaLvl() {
        return seaLvl;
    }

    public void setSeaLvl(Double seaLvl) {
        this.seaLvl = seaLvl;
    }

    public Double getGrndLvl() {
        return grndLvl;
    }

    public void setGrndLvl(Double grndLvl) {
        this.grndLvl = grndLvl;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}