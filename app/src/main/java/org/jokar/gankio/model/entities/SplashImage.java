package org.jokar.gankio.model.entities;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * Created by JokAr on 16/9/10.
 */
public class SplashImage {
    @SerializedName("text")
    private String text;
    @SerializedName("img")
    private String img;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "SplashImage{" +
                "text='" + text + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public String getJsonString(){
//        {
//            "text": "Johannes Andersson",
//                "img": "https://pic1.zhimg.com/v2-2c9774a1c31be8033125427da581e66c.jpg"
//        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append( "\"text\":\"");
        buffer.append(text);
        buffer.append("\", \"img\":\"");
        buffer.append(img);
        buffer.append("\"}");
        return buffer.toString();
    }
}
