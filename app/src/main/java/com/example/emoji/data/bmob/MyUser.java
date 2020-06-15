package com.example.emoji.data.bmob;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 创建时间:2020/6/7
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class MyUser extends BmobUser {
    private Boolean sex; //性别
    private Integer age; //新增age字段
    private String headPicUrl; //头像url

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getHeadPicUrl() {
        return headPicUrl;
    }

    public void setHeadPicUrl(String headPicUrl) {
        this.headPicUrl = headPicUrl;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "sex=" + sex +
                ", age=" + age +
                ", headPicUrl=" + headPicUrl +
                '}';
    }
}
