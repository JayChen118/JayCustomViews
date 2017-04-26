package cn.jaychen.jaycustomviews.bean;

/**
 * Created by JayChen on 2017/4/16.
 */

public class Page {

    private String title;
    private Class clazz;

    public Page(String title, Class clazz) {
        this.title = title;
        this.clazz = clazz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
