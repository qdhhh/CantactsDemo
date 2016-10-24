package com.android.qdhhh.contactsdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private FragmentManager fragmentManager;
    private MainFragment mainFragment;
    private Search_Fragment searchfragment;
    private FragmentTransaction fragmentTransaction;
    private ThisOnClickLitener onClickLitener;

    private boolean isSearch;

    private EditText et_id;
    private ImageView iv_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onClickLitener = new ThisOnClickLitener();
        et_id = (EditText) findViewById(R.id.et_id);
        et_id.setOnTouchListener(new ThisTouchListener());
        iv_id = (ImageView) findViewById(R.id.iv_id);
        iv_id.setOnClickListener(onClickLitener);
        button = (Button) findViewById(R.id.bt_id);
        button.setOnClickListener(onClickLitener);
        mainFragment = new MainFragment();
        searchfragment = new Search_Fragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_main_id, searchfragment).commit();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(searchfragment).add(R.id.fl_main_id, mainFragment).commit();
    }


    private final class ThisOnClickLitener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.iv_id) {
                iv_id.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                hideKeyboard(et_id);
                isSearch = false;

                fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.hide(searchfragment).show(mainFragment).commit();

            }
        }
    }

    public void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private final class ThisTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (!isSearch) {
                iv_id.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                isSearch = true;
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(mainFragment).show(searchfragment).commit();
            }
            return false;
        }

    }
}