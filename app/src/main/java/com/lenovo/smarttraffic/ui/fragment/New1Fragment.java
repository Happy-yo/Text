package com.lenovo.smarttraffic.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.New;
import com.lenovo.smarttraffic.demo7.activity.DetailsActivity;
import com.lenovo.smarttraffic.util.Okhttp_Get;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class New1Fragment extends Fragment {
    private int nows = 0;
    private ArrayList<New> list = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setAdapter(new New1Adapter(list,getContext()));
                recyclerView.setLayoutManager(linearLayoutManager);
            }
            if(msg.what == 1){
                if(nows == 1){
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frx,new New1Fragment());
                    transaction.commit();
                    nows = 0;
                }
            }
        }
    };
    public New1Fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news,container,false);
        recyclerView = view.findViewById(R.id.re);
        TreeMap<String,Object> map = new TreeMap<>();
        map.put("UserName","user1");
        Okhttp_Get.http(map, "http://10.0.2.2:8088/transportservice/action/GetNewsInfo.do", new Okhttp_Get.Get_Dat() {
            @Override
            public void get(JSONObject json) {
                try {
                    JSONArray array = json.getJSONArray("ROWS_DETAIL");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        list.add(new New(jsonObject.getString("title"),jsonObject.getString("createtime"),jsonObject.getString("content")));
                    }
                    Message message = new Message();
                    message.what = 0;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {
                nows = 1;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fra,new ThirdFragment());
                transaction.commit();
            }
        });
        swipeRefreshLayout = view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onRefresh() {
                //逻辑
                TreeMap<String,Object> map = new TreeMap<>();
                map.put("UserName","user1");
                Okhttp_Get.http(map, "http://10.0.2.2:8088/transportservice/action/GetNewsInfo.do", new Okhttp_Get.Get_Dat() {
                    @Override
                    public void get(JSONObject json) {
                        Message message1 = new Message();
                        message1.what = 1;
                        handler.sendMessage(message1);
                        try {
                            JSONArray array = json.getJSONArray("ROWS_DETAIL");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                list.add(new New(jsonObject.getString("title"),jsonObject.getString("createtime"),jsonObject.getString("content")));
                            }
                            Message message = new Message();
                            message.what = 0;
                            handler.sendMessage(message);
                            swipeRefreshLayout.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void fail() {
                        nows = 1;
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fra,new ThirdFragment());
                        transaction.commit();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        });
        return view;
    }

}
//适配器1
class New1Adapter extends RecyclerView.Adapter<New1Adapter.ViewHolder>{
    private ArrayList<New> list = new ArrayList<>();
    private Context context;

    public New1Adapter(ArrayList<New> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_model,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView_title.setText(list.get(position).getTile());
        holder.textView_time.setText(list.get(position).getTime());
        holder.textView_context.setText(list.get(position).getContext());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                ArrayList<String> list1 = new ArrayList<>();
                list1.add(list.get(position).getTile());
                list1.add("推荐");
                list1.add(list.get(position).getTime());
                list1.add(list.get(position).getContext());
                intent.putExtra("this",list1);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_title;
        TextView textView_time;
        TextView textView_context;
        public ViewHolder(View itemView) {
            super(itemView);
            textView_title = itemView.findViewById(R.id.text1);
            textView_time = itemView.findViewById(R.id.text2);
            textView_context = itemView.findViewById(R.id.text3);
        }
    }
}
