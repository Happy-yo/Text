package com.lenovo.smarttraffic.demo7.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.util.Okhttp_Get;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TreeMap;

public class SubwayActivity extends AppCompatActivity {
    TextView textView[] = new TextView[8];
    EditText editText1;
    EditText editText2;
    Button button;
    public static String s1;
    public static String s2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway);
        button = findViewById(R.id.button);
        editText1 = findViewById(R.id.edit1);
        editText2 = findViewById(R.id.edit2);
        textView[0] = findViewById(R.id.text1);
        textView[1] = findViewById(R.id.text2);
        textView[2] = findViewById(R.id.text3);
        textView[3] = findViewById(R.id.text4);
        textView[4] = findViewById(R.id.text5);
        textView[5] = findViewById(R.id.text6);
        textView[6] = findViewById(R.id.text7);
        textView[7] = findViewById(R.id.text8);
        for (int i = 0; i < textView.length; i++) {
            int finalI = i;
            textView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SubwayActivity.this,SubwayInforActivity.class);
                    intent.putExtra("this",finalI);
                    startActivity(intent);
                }
            });
        }
        editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog1 myDialog1 = new MyDialog1(SubwayActivity.this);
                myDialog1.setContentView(R.layout.subwaydialog1);
                WindowManager.LayoutParams layoutParams = myDialog1.getWindow().getAttributes();
                layoutParams.width = 500;
                layoutParams.height = 200;
                myDialog1.getWindow().setAttributes(layoutParams);
                myDialog1.show();
            }
        });

        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog1 myDialog1 = new MyDialog1(SubwayActivity.this);
                myDialog1.setContentView(R.layout.subwaydialog1);
                WindowManager.LayoutParams layoutParams = myDialog1.getWindow().getAttributes();
                layoutParams.width = 500;
                layoutParams.height = 200;
                myDialog1.getWindow().setAttributes(layoutParams);
                myDialog1.show();
            }
        });


        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                  if(!editText1.getText().toString().equals("")&&!editText2.getText().toString().equals("")){
                      button.setBackgroundColor(0xFF0BD4EF);
                  }
            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editText1.getText().toString().equals("")&&!editText2.getText().toString().equals("")){
                    button.setBackgroundColor(0xFF0BD4EF);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText1.getText().toString().equals(editText2.getText().toString())){
                    Toast.makeText(SubwayActivity.this, "起点与终点站不可相同", Toast.LENGTH_SHORT).show();
                }
                else {
                    s1 = editText1.getText().toString();
                    s2 = editText2.getText().toString();
                    startActivity(new Intent(SubwayActivity.this,IderActivity.class));
                }
            }
        });





    }
}
class MyDialog1 extends Dialog{
    TextView textView[] = new TextView[8];
    Context context;

    public MyDialog1(@NonNull Context context) {
        super(context);
        this.context =context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView[0] = findViewById(R.id.text1);
        textView[1] = findViewById(R.id.text2);
        textView[2] = findViewById(R.id.text3);
        textView[3] = findViewById(R.id.text4);
        textView[4] = findViewById(R.id.text5);
        textView[5] = findViewById(R.id.text6);
        textView[6] = findViewById(R.id.text7);
        textView[7] = findViewById(R.id.text8);

        for (int i = 0; i < textView.length; i++) {
            int finalI = i;
            textView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyDiglog2 myDiglog2 = new MyDiglog2(context,finalI);
                    myDiglog2.setContentView(R.layout.subwaydialog2);
                    WindowManager.LayoutParams layoutParams = myDiglog2.getWindow().getAttributes();
                    layoutParams.width = 1000;
                    layoutParams.height = 500;
                    myDiglog2.getWindow().setAttributes(layoutParams);
                    myDiglog2.show();
                }
            });
        }

    }

}
class MyDiglog2 extends Dialog {
    private Context context;
    private ArrayList<String> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView textView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                Object o[] = (Object[])msg.obj;
                String s[] = (String[])o[0];
                JSONArray array[] = (JSONArray[]) o[1];
                textView.setText(s[0]+"号线路");

                JSONArray array1 = array[0];
                JSONArray array2 = array[1];

                ArrayList<Integer> other = new ArrayList<>();
                for (int i = 0; i < array1.length(); i++) {
                    try {
                        String name = array1.getString(i);
                        for (int j = 0; j < array2.length(); j++) {
                            if(name.equals(array2.getString(j))){
                                other.add(i);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



                for (int i = 0; i < array1.length(); i++) {
                    try {
                        list.add(array1.getString(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                SubwayListAdapter adapter = new SubwayListAdapter(list,other);
                recyclerView.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(context);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);

            }
        }
    };
    private int id;
    public MyDiglog2(@NonNull Context context,int id) {
        super(context);
        this.context = context;
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = findViewById(R.id.text);
        recyclerView = findViewById(R.id.re);
        TreeMap<String,Object> map = new TreeMap<>();
        map.put("Line",id);
        map.put("UserName","user1");
        Okhttp_Get.http(map, "http://10.0.2.2:8088/transportservice/action/GetMetroInfo.do", new Okhttp_Get.Get_Dat() {
            @Override
            public void get(JSONObject json) {
                JSONArray array[] = new JSONArray[2];
                String s[] = new String[2];
                Object o[] = new Object[]{s,array};
                try {
                    JSONArray arrays = json.getJSONArray("ROWS_DETAIL");

                    JSONObject jsonObject1 = arrays.getJSONObject(0);
                    s[0] = jsonObject1.getString("name");
                    s[1] = jsonObject1.getString("map");
                    array[0] = jsonObject1.getJSONArray("sites");
                    array[1] = jsonObject1.getJSONArray("transfersites");
                    Message message = new Message();
                    message.what = 0;
                    message.obj = o;
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void fail() {

            }
        });

    }
}
class SubwayListAdapter extends RecyclerView.Adapter<SubwayListAdapter.ViewHolder>{
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<Integer> other = new ArrayList<>();

    public SubwayListAdapter(ArrayList<String> list,ArrayList<Integer> other) {
        this.list = list;
        this.other = other;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subway_model1,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
        for (int i = 0; i < other.size(); i++) {
            if(other.get(i) == position){
                holder.view.setBackgroundResource(R.drawable.subway4);
                holder.textView_im.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View view;
        TextView textView_im;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name);
            view = itemView.findViewById(R.id.ons);
            textView_im = itemView.findViewById(R.id.title);
        }
    }
}
