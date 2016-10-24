package com.android.qdhhh.contactsdemo;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private View view;
    private ListView lv_id;
    private LetterIndexSideBar index_id;
    private TextView remind_tv_id;
    private List<ProvinceBean> list;
    private DemoAdapter demoAdapter;

    private View headView;


    public MainFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initViews();
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getData();
        formatData();
        setListView();
        super.onActivityCreated(savedInstanceState);
    }


    /**
     * 数据排序
     */
    private void formatData() {

        for (int i = 0; i < list.size(); i++) {

            String convert = SwitchToPinyinUtils.getInstance().getPinyin(list.get(i).getP()).toUpperCase();
            list.get(i).setPinyin(convert);

            if (convert.substring(0, 1).matches("[A-Z]")) {
                list.get(i).setFirstLetter(convert.substring(0, 1));
            } else {
                list.get(i).setFirstLetter("#");
            }

        }

        Collections.sort(list, new Comparator<ProvinceBean>() {
            @Override
            public int compare(ProvinceBean lhs, ProvinceBean rhs) {
                if (lhs.getFirstLetter().contains("#")) {
                    return 1;
                } else if (rhs.getFirstLetter().contains("#")) {
                    return -1;
                } else {
                    return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
                }
            }
        });
    }


    /**
     * 设置ListView
     */
    private void setListView() {

        demoAdapter = new DemoAdapter(list, getContext());

        lv_id.setAdapter(demoAdapter);

        setLetterView();

    }


    /**
     * 设置字母索引侧边栏
     */
    private void setLetterView() {
        index_id.setTextViewDialog(remind_tv_id);
        index_id.setUpdateListView(new LetterIndexSideBar.UpdateListView() {
            @Override
            public void updateListView(String currentChar) {
                int positionForSection = demoAdapter.getPositionForSection(currentChar.charAt(0));
                lv_id.setSelection(positionForSection);
            }
        });
        lv_id.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (list.size() == 0) {
                } else {
                    int sectionForPosition = demoAdapter.getSectionForPosition(firstVisibleItem);
                    index_id.updateLetterIndexView(sectionForPosition);
                }
            }
        });
    }


    /**
     * 获取数据
     */
    private void getData() {

        list = new LinkedList<>();

        String str = openAssetsText();

        ProvinceBean p = null;

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONObject(str).getJSONArray("citylist");

            for (int i = 0; i < jsonArray.length(); i++) {

                p = new ProvinceBean();

                JSONObject obj = jsonArray.getJSONObject(i);

                String provinceName = obj.getString("p");

                p.setP(provinceName);

                list.add(p);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 从资源文件中获取地址的数据
     *
     * @return 地址的Json数据
     */

    public String openAssetsText() {
        Resources res = this.getResources();
        InputStream inputStream = null;
        String str = null;
        try {
            inputStream = res.getAssets().open("city.json");
            byte[] buffer = new byte[inputStream.available()];
            while (inputStream.read(buffer) != -1) {
            }
            str = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }


    /**
     * 初始化控件
     */
    private void initViews() {
        lv_id = (ListView) view.findViewById(R.id.lv_id);
        index_id = (LetterIndexSideBar) view.findViewById(R.id.index_id);
        remind_tv_id = (TextView) view.findViewById(R.id.remind_tv_id);

        headView = View.inflate(getContext(), R.layout.headview, null);

        lv_id.addHeaderView(headView);

    }
}
