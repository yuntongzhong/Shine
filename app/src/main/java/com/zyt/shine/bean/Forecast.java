
package com.zyt.shine.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Forecast {

    @SerializedName("fengxiang")
    @Expose
    private String fengxiang;
    @SerializedName("fengli")
    @Expose
    private String fengli;
    @SerializedName("high")
    @Expose
    private String high;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("low")
    @Expose
    private String low;
    @SerializedName("date")
    @Expose
    private String date;

    /**
     * 
     * @return
     *     The fengxiang
     */
    public String getFengxiang() {
        return fengxiang;
    }

    /**
     * 
     * @param fengxiang
     *     The fengxiang
     */
    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    /**
     * 
     * @return
     *     The fengli
     */
    public String getFengli() {
        return fengli;
    }

    /**
     * 
     * @param fengli
     *     The fengli
     */
    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    /**
     * 
     * @return
     *     The high
     */
    public String getHigh() {
        return high;
    }

    /**
     * 
     * @param high
     *     The high
     */
    public void setHigh(String high) {
        this.high = high;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The low
     */
    public String getLow() {
        return low;
    }

    /**
     * 
     * @param low
     *     The low
     */
    public void setLow(String low) {
        this.low = low;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

}
