
package com.zyt.shine.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Yesterday {

    @SerializedName("fl")
    @Expose
    private String fl;
    @SerializedName("fx")
    @Expose
    private String fx;
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
     *     The fl
     */
    public String getFl() {
        return fl;
    }

    /**
     * 
     * @param fl
     *     The fl
     */
    public void setFl(String fl) {
        this.fl = fl;
    }

    /**
     * 
     * @return
     *     The fx
     */
    public String getFx() {
        return fx;
    }

    /**
     * 
     * @param fx
     *     The fx
     */
    public void setFx(String fx) {
        this.fx = fx;
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
