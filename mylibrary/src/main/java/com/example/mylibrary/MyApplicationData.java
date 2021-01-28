package com.example.mylibrary;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyApplicationData {
 private static List<MyAppInfo>appsList;
    public static List<MyAppInfo> getMyAppInfoList(Context context){
        PackageManager pManager = context.getPackageManager();

        appsList = new ArrayList<MyAppInfo>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> allApps = pManager.queryIntentActivities(i, 0);

        for (ResolveInfo ri : allApps) {
            MyAppInfo app = new MyAppInfo();

            app.setLabel(ri.loadLabel(pManager));
            app.setPackageName(ri.activityInfo.packageName);
            app.setIcon(ri.activityInfo.loadIcon(pManager));

            PackageInfo packageInfo=null;
            try{
                packageInfo=pManager.getPackageInfo(app.packageName.toString(),0);


            } catch (PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }
            app.setVersionName(packageInfo.versionName.toString());
            app.setVersionCode(packageInfo.versionCode);
            app.setMainActivityName(ri.activityInfo.name);
            Log.d("TAG", "setUpApps: "+app.mainActivityName);

            Log.i("Installed App Info: ",  "App Name: "+app.label+"  App Main Activity"+app.mainActivityName+ "  Apps Package Name: "+app.packageName.toString()+"  Version Code: "+packageInfo.versionCode+"   Version Name: "+packageInfo.versionName);


            Collections.sort(appsList, new Comparator<MyAppInfo>() {
                @Override
                public int compare(MyAppInfo lhs, MyAppInfo rhs) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    return lhs.label.toString().compareTo(rhs.label.toString());
                }
            });
            appsList.add(app);
        }
        return appsList;

    }


}
