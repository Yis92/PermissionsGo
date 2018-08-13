package com.yis.study.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.yis.study.model.MyPermissionBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yis on 2018/8/10.
 */
public class PermissionUtils {

    private List<MyPermissionBean> myPermissionsList = new ArrayList<>();

    public List<MyPermissionBean> getMyPermissionsList() {
        return myPermissionsList;
    }

    public void setMyPermissionsList(List<MyPermissionBean> myPermissionsList) {
        this.myPermissionsList = myPermissionsList;
    }

    public void initPermissions(Context mContext) {
        MyPermissionBean myPermissionBean_storage = new MyPermissionBean();
        myPermissionBean_storage.setPermissionName(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        myPermissionBean_storage.setPermissionRequestInfo("存储");

        myPermissionsList.add(myPermissionBean_storage);
        MyPermissionBean myPermissionBean_phone = new MyPermissionBean();
        myPermissionBean_phone.setPermissionName(Manifest.permission.READ_PHONE_STATE);
        myPermissionBean_phone.setPermissionRequestInfo("电话");

        myPermissionsList.add(myPermissionBean_phone);
        MyPermissionBean myPermissionBean_accounts = new MyPermissionBean();
        myPermissionBean_accounts.setPermissionName(Manifest.permission.GET_ACCOUNTS);
        myPermissionBean_accounts.setPermissionRequestInfo("通讯录");

        myPermissionsList.add(myPermissionBean_accounts);
        MyPermissionBean myPermissionBean_location = new MyPermissionBean();
        myPermissionBean_location.setPermissionName(Manifest.permission.ACCESS_FINE_LOCATION);
        myPermissionBean_location.setPermissionRequestInfo("GPS定位");
        myPermissionsList.add(myPermissionBean_location);

        MyPermissionBean myPermissionBean_camera = new MyPermissionBean();
        myPermissionBean_camera.setPermissionName(Manifest.permission.CAMERA);
        myPermissionBean_camera.setPermissionRequestInfo("相机");
        myPermissionsList.add(myPermissionBean_camera);

        MyPermissionBean myPermissionBean_Sound = new MyPermissionBean();
        myPermissionBean_Sound.setPermissionName(Manifest.permission.RECORD_AUDIO);
        myPermissionBean_Sound.setPermissionRequestInfo("录音");
        myPermissionsList.add(myPermissionBean_Sound);

        checkPermissions(mContext);
    }

    /**
     * 检查权限
     *
     * @param mContext
     * @return
     */
    public static boolean checkPermission(Context mContext) {
        //是否需要动态请求权限
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(mContext)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                mContext.startActivity(intent);
                return false;
            }
        }
        return true;
    }

    public boolean checkPermissions(Context mContext) {
        //是否需要动态请求权限
        boolean isNeedPermission = false;
        if (Build.VERSION.SDK_INT >= 23) {
            for (int i = 0; i < myPermissionsList.size(); i++) {
                int permission = ContextCompat.checkSelfPermission(mContext, myPermissionsList.get(i).getPermissionName());
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    myPermissionsList.get(i).setHas(false);
                    isNeedPermission = true;
                } else {
                    myPermissionsList.get(i).setHas(true);
                }
            }
        }
        return isNeedPermission;
    }

    public void shouldRequest(Activity mContext) {
        try {
            for (int i = 0; i < myPermissionsList.size(); i++) {
                if (myPermissionsList != null && !myPermissionsList.get(i).isHas()) {
                    Log.d("liu", "---需要获取权限--->" + myPermissionsList.get(i).getPermissionName());
                    if (ActivityCompat.shouldShowRequestPermissionRationale(mContext, myPermissionsList.get(i).getPermissionName())) {
                        explainDialog(mContext, myPermissionsList, i);
                        return;
                    } else {
                        //请求权限
                        ActivityCompat.requestPermissions(mContext, new String[]{myPermissionsList.get(i).getPermissionName()}, i + 1);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void explainDialog(final Activity mContext, final List<MyPermissionBean> permissionsList, final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("应用需要获取您的" + permissionsList.get(i).getPermissionRequestInfo() + "权限,是否授权？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //请求权限
                        ActivityCompat.requestPermissions(mContext, new String[]{permissionsList.get(i).getPermissionName()}, i + 1);
                    }
                }).setNegativeButton("取消", null)
                .create().show();
    }

}
