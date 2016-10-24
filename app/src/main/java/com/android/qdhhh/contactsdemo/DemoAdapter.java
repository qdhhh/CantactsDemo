package com.android.qdhhh.contactsdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by qdhhh on 2016/10/10.
 */

public class DemoAdapter extends BaseAdapter {

    private List<ProvinceBean> list;

    private Context context;

    public DemoAdapter(List list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item, null);
            vh = new ViewHolder();
            vh.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            vh.letter_tv_id = (TextView) convertView.findViewById(R.id.letter_tv_id);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.tv_id.setText(list.get(position).getP());


        //获得当前position是属于哪个分组
        int sectionForPosition = list.get(position).getFirstLetter().charAt(0);
        //获得该分组第一项的position
        int positionForSection = getPositionForSection(sectionForPosition);

        if (position == positionForSection) {
            vh.letter_tv_id.setVisibility(View.VISIBLE);
            vh.letter_tv_id.setText(list.get(position).getFirstLetter());
        } else {
            vh.letter_tv_id.setVisibility(View.GONE);
        }
        return convertView;
    }


    //传入一个分组值[A-Z],获得该分组的第一项的position
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPinyin().charAt(0) == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    //传入一个position，获得该position所在的分组
    public int getSectionForPosition(int position) {
        return list.get(position).getPinyin().charAt(0);
    }

    /**
     * viewholder实体类
     */
    private class ViewHolder {
        TextView tv_id;
        TextView letter_tv_id;
    }


}
