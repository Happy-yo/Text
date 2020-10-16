package com.lenovo.smarttraffic.demo7.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.lenovo.smarttraffic.R;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    private TextView textView[] = new TextView[4];
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        textView[0] = findViewById(R.id.text);
        textView[1] = findViewById(R.id.text1);
        textView[2] = findViewById(R.id.text2);
        textView[3] = findViewById(R.id.text3);
        ArrayList<String> list = getIntent().getStringArrayListExtra("this");
        textView[0].setText(list.get(0));
        textView[1].setText(list.get(1));
        textView[2].setText(list.get(2));
        textView[3].setText(list.get(3));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
