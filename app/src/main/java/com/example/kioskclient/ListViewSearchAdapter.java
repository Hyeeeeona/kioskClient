package com.example.kioskclient;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ListViewSearchAdapter extends BaseAdapter implements Filterable {
    private ArrayList<ListViewSearchItem> ListViewSearchItemList = new ArrayList<ListViewSearchItem>();
    private ArrayList<ListViewSearchItem> filteredItemList = ListViewSearchItemList;

    Filter listFilter;

    public ListViewSearchAdapter() {
    }

    @Override
    public int getCount() {
        return filteredItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getItemShopId(int position){
        return filteredItemList.get(position).getRestId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ViewHoldr 패턴
        final int pos = position;
        final Context mContext = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_rest, parent, false);
        }

        ListViewSearchItem item = filteredItemList.get(position);
        ImageView restImage = (ImageView) convertView.findViewById(R.id.restImage);
        TextView restName = (TextView) convertView.findViewById(R.id.restName);
        //TextView restScore = (TextView) convertView.findViewById(R.id.restScore);
        TextView restDistance = (TextView) convertView.findViewById(R.id.restDistance);
        TextView restMain = (TextView) convertView.findViewById(R.id.restMain);

        //restImage.setImageDrawable(item.getRestIcon());
        Glide.with(mContext)
                .load(R.drawable.delete_text)
                .centerCrop()
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(mContext))
                .override(75,75)
                .into(restImage);
        restName.setText(item.getRestName());
        // restScore.setText(String.valueOf(item.getRestScore()));
        restDistance.setText(String.valueOf(item.getRestDistance()));
        restMain.setText(item.getRestMain());

        return convertView;
    }

    public void addItem(String name, Number score, String distance, Drawable icon, String main, int id) {
        ListViewSearchItem item = new ListViewSearchItem();

        item.setRestName(name);
        // item.setRestScore(score);
        item.setRestDistance(distance);
        item.setRestMain(main);
        item.setRestIcon(icon);
        item.setRestId(id);

        ListViewSearchItemList.add(item);
    }

    @Override
    public Filter getFilter() {
        if(listFilter == null){
            listFilter = new ListFilter();
        }
        return listFilter;
    }

    private class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() == 0){
                results.values = ListViewSearchItemList;
                results.count = ListViewSearchItemList.size();
            } else {
                ArrayList<ListViewSearchItem> itemList = new ArrayList<ListViewSearchItem>();

                for(ListViewSearchItem item : ListViewSearchItemList){
                    if(item.getRestMain().toUpperCase().contains(constraint.toString().toUpperCase())
                            || item.getRestName().toUpperCase().contains(constraint.toString().toUpperCase())){
                        itemList.add(item);
                    }
                }

                results.values = itemList;
                results.count = itemList.size();
            }

            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItemList = (ArrayList<ListViewSearchItem>) results.values;

            Comparator<ListViewSearchItem> noDesc = new Comparator<ListViewSearchItem>() {
                @Override
                public int compare(ListViewSearchItem o1, ListViewSearchItem o2) {
                    int ret = 0;

                    if(Float.parseFloat(o1.getRestDistance()) < Float.parseFloat(o2.getRestDistance())){
                        ret = 1;
                    } else if(Float.parseFloat(o1.getRestDistance()) > Float.parseFloat(o2.getRestDistance())){
                        ret = 0;
                    } else {
                        ret = -1;
                    }
                    return ret;
                }
            };

            Collections.sort(filteredItemList, noDesc);

            if(results.count > 0){
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }


}