package com.lenovo.smarttraffic.demo7.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.lenovo.smarttraffic.R;

public class IderActivity extends AppCompatActivity {
    private TextView textView[] = new TextView[6];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idear);
        textView[0] = findViewById(R.id.text1);
        textView[1] = findViewById(R.id.text2);
        textView[2] = findViewById(R.id.text3);
        textView[3] = findViewById(R.id.text4);
        textView[4] = findViewById(R.id.text5);
        textView[5] = findViewById(R.id.text6);

        textView[0].setText(SubwayActivity.s1);
        textView[2].setText(SubwayActivity.s1);
        textView[4].setText(SubwayActivity.s1);
        textView[1].setText(SubwayActivity.s2);
        textView[3].setText(SubwayActivity.s2);
        textView[5].setText(SubwayActivity.s2);

    }
}
