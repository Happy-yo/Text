package com.lenovo.smarttraffic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */
public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("title",MODE_PRIVATE);
        editor = getSharedPreferences("title",MODE_PRIVATE).edit();

        editor.putInt("推荐",1);
        editor.putInt("热点",1);
        editor.putInt("科技",1);
        editor.putInt("汽车资讯",1);
        editor.putInt("健康",0);
        editor.putInt("财经",0);
        editor.putInt("体育",0);
        editor.putInt("实时路况",0);
        editor.putInt("文化",0);
        editor.putInt("二手车",0);
        editor.putInt("违章资讯",0);
        editor.putInt("娱乐",0);
        editor.putInt("体育",0);
        editor.putInt("视频",0);
        editor.putInt("旅游",0);
        editor.putInt("军事",0);
        editor.putInt("游戏",0);
        editor.putInt("电影",0);
        editor.apply();


        InitApp.getHandler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        },1000);
    }

}
