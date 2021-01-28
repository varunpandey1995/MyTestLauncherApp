package com.example.mylibrary;

import android.content.Intent;
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
 public static List<AppInfo>appsList;
    public static List<AppInfo> getMyAppsData(Context context){
        PackageManager pManager = context.getPackageManager();

        appsList = new ArrayList<AppInfo>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> allApps = pManager.queryIntentActivities(i, 0);

        for (ResolveInfo ri : allApps) {
            AppInfo app = new AppInfo();
            app.label = ri.loadLabel(pManager);
            app.packageName = ri.activityInfo.packageName;

            Log.i(" Log package ",app.packageName.toString());
            app.icon = ri.activityInfo.loadIcon(pManager);
            Collections.sort(appsList, new Comparator<AppInfo>() {
                @Override
                public int compare(AppInfo lhs, AppInfo rhs) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    return lhs.label.toString().compareTo(rhs.label.toString());
                }
            });
            appsList.add(app);
        }
        return appsList;

    }


}
