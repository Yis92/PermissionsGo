package com.yis.study;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yis.study.utils.PermissionUtils;

public class MainActivity extends AppCompatActivity {

    PermissionUtils permissionUtils = new PermissionUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        permissionUtils.initPermissions(this);

        if (!permissionUtils.checkPermissions(this)) {
            Log.i("liu", "---不需要获取权限---");

            //TODO...权限申请完成

        } else {
            Log.i("liu", "---需要获取权限---");


            permissionUtils.shouldRequest(this);
        }
    }


    @Override
    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Log.i("liu", "----获取权限requestCode--->" + requestCode);
                switch (requestCode) {
                    case 1:
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            permissionUtils.getMyPermissionsList().get(0).setHas(true);
                        }
                        break;
                    case 2:
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            permissionUtils.getMyPermissionsList().get(1).setHas(true);
                        }
                        break;
                    case 3:
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            permissionUtils.getMyPermissionsList().get(2).setHas(true);
                        }
                        break;
                    case 4:
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            permissionUtils.getMyPermissionsList().get(3).setHas(true);
                        }
                        break;
                    case 5:
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            permissionUtils.getMyPermissionsList().get(4).setHas(true);
                        }
                        break;
                    case 6:
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            permissionUtils.getMyPermissionsList().get(5).setHas(true);
                        }
                        break;
                    default:
                        break;
                }
                isContinue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i("liu", "----获取权限版本小于23requestCode--->" + requestCode);
        }
    }

    private void isContinue() {
        boolean isCanContinue = true;
        if (permissionUtils.getMyPermissionsList() != null && permissionUtils.getMyPermissionsList().size() > 0) {
            for (int i = 0; i < permissionUtils.getMyPermissionsList().size(); i++) {
                if (!permissionUtils.getMyPermissionsList().get(i).isHas()) {
                    isCanContinue = false;
                }
            }
            if (isCanContinue) {
                //TODO

            } else {
                Log.i("chenggs", "----未获取到所以需要的权限---");
                permissionUtils.shouldRequest(this);
            }
        }
    }
}
