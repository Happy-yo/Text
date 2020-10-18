package com.lenovo.smarttraffic.demo7.activity;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.util.Okhttp_Get;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TreeMap;

public class SubwayInforActivity extends AppCompatActivity {
    private boolean isRiskMove;
    private int mRiskLastX;
    private int mRiskLastY;
    private ArrayList<String> list1 = new ArrayList<>();
    private ArrayList<String> list2 = new ArrayList<>();
    private TextView textView_title;
    private TextView[] textView = new TextView[8];
    private ImageView imageView;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                Object o[] = (Object[])msg.obj;
                String s[] = (String[])o[0];
                JSONArray array[] = (JSONArray[]) o[1];
                textView_title.setText(s[0]+"线路详情");
                Glide.with(SubwayInforActivity.this).load("http://10.0.2.2:8088/transportservice"+s[1]).placeholder(R.drawable.indeterminate_drawable).error(R.drawable.ic_error_page).into(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(TextView textViews:textView){
                            textViews.setVisibility(View.INVISIBLE);
                        }
                        recyclerView1.setVisibility(View.INVISIBLE);
                        recyclerView2.setVisibility(View.INVISIBLE);
                        imageView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                int x = (int) motionEvent.getRawX();
                                int y = (int) motionEvent.getRawY();
                                switch (motionEvent.getAction()) {
                                    case MotionEvent.ACTION_DOWN:

                                        break;
                                    case MotionEvent.ACTION_MOVE:
                                        isRiskMove = true;
                                        //计算距离上次移动了多远
                                        int deltaX = x - mRiskLastX;
                                        int deltaY = y - mRiskLastY;
                                        int translationX = (int) (imageView.getTranslationX() + deltaX);
                                        int translationY = (int) (imageView.getTranslationY() + deltaY);
                                        //使mFloatRiskBtn根据手指滑动平移
                                        imageView.setTranslationX(translationX);
                                        imageView.setTranslationY(translationY);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        //平移回到该view水平方向的初始点
                                        imageView.setTranslationX(0);
                                        //判断什么情况下需要回到原点
                                        if(imageView.getY()<0 || imageView.getY()>(imageView.getMeasuredHeight()-imageView.getMeasuredHeight())) {
                                            imageView.setTranslationY(0);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                                //记录上次手指离开时的位置
                                mRiskLastX = x;
                                mRiskLastY = y;

                                return true;
                            }
                        });
                    }
                });



                JSONArray array1 = array[0];
                JSONArray array2 = array[1];

                for (int i = 0; i < 13; i++) {
                    try {
                        list1.add(array1.getString(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 13; i < array1.length(); i++) {
                    try {
                        list2.add(array1.getString(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                textView[6].setText(array1.length()+"站,"+array1.length()*2+"公里");
                textView[7].setText("最高票价"+array1.length()*0.2+"元");


                SubwayAdapter adapter1 = new SubwayAdapter(list1);
                SubwayAdapter adapter2 = new SubwayAdapter(list2);
                recyclerView1.setAdapter(adapter1);
                recyclerView2.setAdapter(adapter2);
                recyclerView1.setLayoutManager(new LinearLayoutManager(SubwayInforActivity.this));
                recyclerView2.setLayoutManager(new LinearLayoutManager(SubwayInforActivity.this));

                try {
                    JSONObject jsonObject1 = array2.getJSONObject(0);
                    JSONObject jsonObject2 = array2.getJSONObject(1);

                    textView[0].setText(jsonObject1.getString("site"));
                    textView[1].setText("首班："+jsonObject1.getString("starttime"));
                    textView[2].setText("末班："+jsonObject1.getString("endtime"));
                    textView[3].setText(jsonObject2.getString("site"));
                    textView[4].setText("首班："+jsonObject2.getString("starttime"));
                    textView[5].setText("末班："+jsonObject2.getString("endtime"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_infor);
        imageView = findViewById(R.id.image);
        recyclerView1 = findViewById(R.id.re1);
        recyclerView2 = findViewById(R.id.re2);
        textView_title = findViewById(R.id.tooltext);
        textView[0] = findViewById(R.id.text1);
        textView[1] = findViewById(R.id.text2);
        textView[2] = findViewById(R.id.text3);
        textView[3] = findViewById(R.id.text4);
        textView[4] = findViewById(R.id.text5);
        textView[5] = findViewById(R.id.text6);
        textView[6] = findViewById(R.id.text7);
        textView[7] = findViewById(R.id.text8);

        int id = getIntent().getIntExtra("this",0);
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
                    array[1] = jsonObject1.getJSONArray("time");
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
class SubwayAdapter extends RecyclerView.Adapter<SubwayAdapter.ViewHolder>{
    private ArrayList<String> list = new ArrayList<>();

    public SubwayAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subway_model,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name);
        }
    }
}
