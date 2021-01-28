package com.example.mytestlauncheapp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppsDrawerAdapter extends RecyclerView.Adapter<AppsDrawerAdapter.ViewHolder> {
    private static Context context;
    private static List<AppInfo> appsList;
    static List<AppInfo> filterdNames;
    String text;
    public AppsDrawerAdapter(Context c,List<AppInfo>appsList) {
        context = c;
        appsList=appsList;
        filterdNames=appsList;
      //  setUpApps();
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //This is what adds the code we've written in here to our target view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_view_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String appLabel = filterdNames.get(position).label.toString();
        final String appPackage = filterdNames.get(position).packageName.toString();
        Drawable appIcon = filterdNames.get(position).icon;

        TextView textView = holder.textView;
        textView.setText(appLabel);
        textView.setTextColor(R.color.black);
        ImageView imageView = holder.img;
        imageView.setImageDrawable(appIcon);
        holder.ll_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackage);

                if (intent != null) {
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return filterdNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView img;
        public LinearLayout ll_click;

        public ViewHolder(View itemView) {
            super(itemView);

            //Finds the views from our row.xml
            textView =  itemView.findViewById(R.id.tv_app_name);
            img = itemView.findViewById(R.id.app_icon);
            ll_click=itemView.findViewById(R.id.ll_click);



        }

    }


    public  void updateList(List<AppInfo> list){
        filterdNames = list;
        notifyDataSetChanged();
    }


//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    filterdNames = appsList;
//                } else {
//                    List<AppInfo> filteredList = new ArrayList<>();
//                    for (AppInfo row : appsList) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.packageName.toString().contains(charString) || row.packageName.toString().contains(charSequence)) {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    filterdNames = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filterdNames;
//                return filterResults;
//            }
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                filterdNames = (ArrayList<AppInfo>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

}

