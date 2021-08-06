package app.daytoday.softpro.daytodayindiadtd;

/**
 * Created by hp on 09-08-2019.
 */

public class newsdata
{
    private String Headline;
    private  String Description;
    private String Imgurl;
    private String Time;
    private String Date;
    private String Category;
    private String Key;
    public newsdata()
    {

    }

    public String getHeadline() {
        return Headline;
    }

    public void setHeadline(String headline) {
        Headline = headline;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImgurl() {
        return Imgurl;
    }

    public void setImgurl(String imgurl) {
        Imgurl = imgurl;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public newsdata(String headline, String description, String imgurl, String time, String date, String category, String key) {
        Headline = headline;
        Description = description;
        Imgurl = imgurl;
        Time = time;
        Date = date;
        Category = category;
        Key = key;
    }
}
