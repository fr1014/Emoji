package com.permission;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间：2020/2/9
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class Permission {
    //持有弱引用HandlerActivity,GC回收时会被回收掉.解决内存泄漏的问题
    private WeakReference<Activity> mWeakActivity;

    private int requestCode;

    private PermissionListener listener;

    private String[] permissions;

    private static Permission instance = new Permission();

    private static List<Integer> codes = new ArrayList<>();

    private Permission() {
    }

    /**
     * 关联上下文
     *
     * @param activity
     * @return
     */
    @NonNull
    public static Permission with(@NonNull Activity activity) {
        instance.setActivity(activity);
        return instance;
    }

    /**
     * 关联上下文
     *
     * @param fragment
     * @return
     */
    @NonNull
    public static Permission with(@NonNull android.app.Fragment fragment) {
        instance.setActivity(fragment.getActivity());
        return instance;
    }

    /**
     * 关联上下文
     *
     * @param fragment
     * @return
     */
    @NonNull
    public static Permission with(@NonNull Fragment fragment) {
        instance.setActivity(fragment.getActivity());
        return instance;
    }

    /**
     * 设置权限请求码
     *
     * @param requestCode
     * @return
     */
    @NonNull
    public Permission requestCode(@NonNull int requestCode) {
        codes.add(requestCode);
        instance.setRequestCode(requestCode);
        return instance;
    }

    /**
     * 设置请求回调
     *
     * @param listener
     * @return
     */
    @NonNull
    public Permission callBack(@NonNull PermissionListener listener) {
        instance.setListener(listener);
        return instance;
    }

    /**
     * 请求项目
     *
     * @param permissions
     * @return
     */
    @NonNull
    public Permission permission(@NonNull String... permissions) {
        instance.setPermissions(permissions);
        return instance;
    }

    /**
     * 开始请求
     */
    @NonNull
    public void send() {
        if (instance == null || instance.getWeakActivity().get() == null || instance.getListener() == null
                || instance.getPermissions() == null) {
            return;
        }

        // 判断是否授权
        if (PermissionUtils.getInstance().checkPermission(instance.getWeakActivity().get(), instance.getPermissions())) {
            // 已经授权，执行授权回调
            instance.getListener().onPermit(instance.getRequestCode(), instance.getPermissions());
        } else {
            PermissionUtils.getInstance().requestPermission(instance.getWeakActivity().get(), instance.getRequestCode(), instance.getPermissions());
        }

    }

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (instance == null) {
            return;
        }
        for (int j = 0; j < codes.size(); j++) {
            if (requestCode == codes.get(j)) {
                // 遍历请求时的所有权限
                for (int i = 0; i < grantResults.length; i++) {
                    // 授权
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        instance.getListener().onPermit(codes.get(j), permissions);
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        instance.getListener().onCancel(codes.get(j), permissions);
                    }

                }
                codes.remove(codes.get(j));
            }
        }
    }


    //==================================以下为get、set方法================================================

    public WeakReference<Activity> getWeakActivity() {
        return mWeakActivity;
    }

    public void setWeakActivity(WeakReference<Activity> mWeakActivity) {
        this.mWeakActivity = mWeakActivity;
    }

//    public Activity getActivity() {
//        return activity;
//    }

    public void setActivity(Activity activity) {
//        this.activity = activity;
        mWeakActivity = new WeakReference<Activity>(activity);
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public PermissionListener getListener() {
        return listener;
    }

    public void setListener(PermissionListener listener) {
        this.listener = listener;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }
}