package com.example.utilitycalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.List;

public class CateAdapter extends BaseAdapter {
    private final Context context;
    private final List<CateItem> items;

    public CateAdapter(Context context, List<CateItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cate_item, parent, false);
            holder = new ViewHolder();
            holder.iconImage = convertView.findViewById(R.id.iconImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CateItem item = items.get(position);
        holder.iconImage.setImageResource(item.getIconResId());

        return convertView;
    }

    private static class ViewHolder {
        ImageView iconImage;
    }
}