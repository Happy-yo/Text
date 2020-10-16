package com.lenovo.smarttraffic.ui.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.demo7.activity.SetTitleActivity;
import com.lenovo.smarttraffic.ui.adapter.BasePagerAdapter;
import com.lenovo.smarttraffic.ui.fragment.New1Fragment;
import com.lenovo.smarttraffic.ui.fragment.New2Fragment;
import com.lenovo.smarttraffic.ui.fragment.New3Fragment;
import com.lenovo.smarttraffic.ui.fragment.New4Fragment;
import com.lenovo.smarttraffic.ui.fragment.New5Fragment;
import com.lenovo.smarttraffic.ui.fragment.SecondFragment;
import com.lenovo.smarttraffic.ui.fragment.ThirdFragment;
import com.lenovo.smarttraffic.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */

public class Item1Activity extends BaseActivity {
    private ImageView imageView;
    private SharedPreferences sharedPreferences;
    private String name[] = new String[]{
            "推荐","热点","科技","汽车资讯","健康","财经","教育","旅游","军事","实时路况","文化","二手车","违章资讯","娱乐","体育","视频","游戏","电影"
    };
    private ArrayList<String> list = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    @BindView(R.id.tab_layout_list)
    TabLayout tabLayoutList;
    @BindView(R.id.header_layout)
    LinearLayout headerLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = findViewById(R.id.add);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Item1Activity.this, SetTitleActivity.class);
                startActivity(intent);
                finish();
            }
        });
        sharedPreferences = getSharedPreferences("title",MODE_PRIVATE);
        String sort = sharedPreferences.getString("sort","");
        String sort_list[] = sort.split(",");
        for (int i = 0; i < sort_list.length; i++) {
            int j = Integer.parseInt(sort_list[i]);
            list.add(name[j]);
            if(j == 0){
                fragmentList.add(new New1Fragment());
            }
            else if(j == 1){
                fragmentList.add(new New2Fragment());
            }
            else if(j == 2){
                fragmentList.add(new New3Fragment());
            }
            else if(j == 3){
                fragmentList.add(new New5Fragment());
            }
            else if(j == 9){
                fragmentList.add(new New4Fragment());
            }
            else {
                fragmentList.add(new SecondFragment());
            }
        }
        InitView();
        InitData();
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_list_tab;
    }

    private void InitView() {
        initToolBar(findViewById(R.id.toolbar), true, "");
        tabLayoutList.setupWithViewPager(viewPager);
        tabLayoutList.setTabMode(TabLayout.MODE_SCROLLABLE);
        headerLayout.setBackgroundColor(CommonUtil.getInstance().getColor());
    }

    private void InitData() {

        BasePagerAdapter basePagerAdapter = new BasePagerAdapter(getSupportFragmentManager(),fragmentList,list);
        viewPager.setAdapter(basePagerAdapter);
        viewPager.setOffscreenPageLimit(2);
    }


    @Override
    protected void onResume() {
        super.onResume();
        headerLayout.setBackgroundColor(CommonUtil.getInstance().getColor());
    }

}
