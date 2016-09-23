package org.jokar.gankio.model.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JokAr on 2016/9/23.
 */

public class AddGank {


    /**
     * error : true
     * msg : 干货类型不对, 可选参数为: Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App 区分大小写哟!
     */
    @SerializedName("error")
    private boolean error;
    @SerializedName("msg")
    private String msg;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
