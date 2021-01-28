package com.example.mytestlauncheapp;

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

public class AppsDrawerFragment extends Fragment {
    RecyclerView recyclerView;
    AppsDrawerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private EditText editSearchText;
    public List<AppInfo>appsList;
    public AppsDrawerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apps_drawer, container, false);
        setUpApps();
        return view;
    }
    public  void setUpApps(){
        PackageManager pManager = getActivity().getPackageManager();

        appsList = new ArrayList<AppInfo>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> allApps = pManager.queryIntentActivities(i, 0);

        for (ResolveInfo ri : allApps) {
            AppInfo app = new AppInfo();

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


            Collections.sort(appsList, new Comparator<AppInfo>() {
                @Override
                public int compare(AppInfo lhs, AppInfo rhs) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    return lhs.label.toString().compareTo(rhs.label.toString());
                }
            });
            appsList.add(app);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.appDrawer_recylerView);
        editSearchText=view.findViewById(R.id.edt_search);
        adapter = new AppsDrawerAdapter(getContext(),appsList);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        editSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                trigger(s.toString());
            }
        });


    }
    public void trigger(String str){
        List<AppInfo> temp = new ArrayList();
        for(AppInfo d: appsList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.packageName.toString().toLowerCase().contains(str.toLowerCase().toString())){
                temp.add(d);
            }
        }

        adapter.updateList(temp);
    }

}
