package com.example.emoji.utils.okhttp3;

/**
 * 创建时间:2020/7/25
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class OkHttpException extends Exception{
    private int eCode; //错误码
    private String eMsg; //错误信息

    public OkHttpException(int eCode, String eMsg) {
        this.eCode = eCode;
        this.eMsg = eMsg;
    }

    public int getECode() {
        return eCode;
    }

    public String getEMsg() {
        return eMsg;
    }
}
