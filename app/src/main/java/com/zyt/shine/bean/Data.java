
package com.zyt.shine.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("wendu")
    @Expose
    private String wendu;
    @SerializedName("ganmao")
    @Expose
    private String ganmao;
    @SerializedName("forecast")
    @Expose
    private List<Forecast> forecast = new ArrayList<Forecast>();
    @SerializedName("yesterday")
    @Expose
    private Yesterday yesterday;
    @SerializedName("aqi")
    @Expose
    private String aqi;
    @SerializedName("city")
    @Expose
    private String city;

    /**
     * 
     * @return
     *     The wendu
     */
    public String getWendu() {
        return wendu;
    }

    /**
     * 
     * @param wendu
     *     The wendu
     */
    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    /**
     * 
     * @return
     *     The ganmao
     */
    public String getGanmao() {
        return ganmao;
    }

    /**
     * 
     * @param ganmao
     *     The ganmao
     */
    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    /**
     * 
     * @return
     *     The forecast
     */
    public List<Forecast> getForecast() {
        return forecast;
    }

    /**
     * 
     * @param forecast
     *     The forecast
     */
    public void setForecast(List<Forecast> forecast) {
        this.forecast = forecast;
    }

    /**
     * 
     * @return
     *     The yesterday
     */
    public Yesterday getYesterday() {
        return yesterday;
    }

    /**
     * 
     * @param yesterday
     *     The yesterday
     */
    public void setYesterday(Yesterday yesterday) {
        this.yesterday = yesterday;
    }

    /**
     * 
     * @return
     *     The aqi
     */
    public String getAqi() {
        return aqi;
    }

    /**
     * 
     * @param aqi
     *     The aqi
     */
    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    /**
     * 
     * @return
     *     The city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(String city) {
        this.city = city;
    }

}
