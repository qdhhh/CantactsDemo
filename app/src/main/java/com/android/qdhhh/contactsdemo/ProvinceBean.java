package com.android.qdhhh.contactsdemo;

/**
 * 省份的实体类
 * <p>
 * Created by qdhhh on 2016/10/10.
 */

public class ProvinceBean {

    private String p;
    private String pinyin;
    private String FirstLetter;

    public String getFirstLetter() {
        return FirstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        FirstLetter = firstLetter;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
