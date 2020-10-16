package com.lenovo.smarttraffic.demo7.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.Title;
import com.lenovo.smarttraffic.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;

public class SetTitleActivity extends AppCompatActivity {
    private String names = "sdfsdfsdfd";
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private ArrayList<Title> list1 = new ArrayList<>();
    private ArrayList<Title> list2 = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String name[] = new String[]{
      "推荐","热点","科技","汽车资讯","健康","财经","教育","旅游","军事","实时路况","文化","二手车","违章资讯","娱乐","体育","视频","游戏","电影"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("title",MODE_PRIVATE);
        editor = getSharedPreferences("title",MODE_PRIVATE).edit();
        for (int i = 0; i < name.length; i++) {
            if(sharedPreferences.getInt(name[i],-1) == 0){
                list1.add(new Title(name[i]));
            }
            else if(sharedPreferences.getInt(name[i],-1) == 1){
                list2.add(new Title(name[i]));
            }
        }

        setContentView(R.layout.settitle);
        recyclerView1 = findViewById(R.id.re1);
        recyclerView2 = findViewById(R.id.re2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView1.setAdapter(new TitleAdapter3(list2,recyclerView1,recyclerView2,list2,list1));
        recyclerView2.setAdapter(new TitleAdapter4(list1));
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this,4);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this,4);
        recyclerView1.setLayoutManager(gridLayoutManager1);
        recyclerView2.setLayoutManager(gridLayoutManager2);
    }
}

//适配器1
class TitleAdapter1 extends RecyclerView.Adapter<TitleAdapter1.ViewHolder>{
    private ArrayList<Title> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;
    public TitleAdapter1(ArrayList<Title> list,RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        this.list = list;
        itemTouchHelper = new ItemTouchHelper(new OnTouchRey(this,list));
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model3,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getTitel());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }

}

//适配器2
class TitleAdapter2 extends RecyclerView.Adapter<TitleAdapter2.ViewHolder>{
    private ArrayList<Title> list = new ArrayList<>();
    public TitleAdapter2(ArrayList<Title> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model2,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getTitel());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}

//适配器3
class TitleAdapter3 extends RecyclerView.Adapter<TitleAdapter3.ViewHolder>{
    private ArrayList<Title> list = new ArrayList<>();
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private ArrayList<Title> list1 = new ArrayList<>();
    private ArrayList<Title> list2 = new ArrayList<>();

    public TitleAdapter3(ArrayList<Title> list, RecyclerView recyclerView1, RecyclerView recyclerView2, ArrayList<Title> list1, ArrayList<Title> list2) {
        this.list = list;
        this.recyclerView1 = recyclerView1;
        this.recyclerView2 = recyclerView2;
        this.list1 = list1;
        this.list2 = list2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model1,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getTitel());
        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                recyclerView1.setAdapter(new TitleAdapter1(list1,recyclerView1));
                recyclerView2.setAdapter(new TitleAdapter2(list2));
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }

}

//适配器4
class TitleAdapter4 extends RecyclerView.Adapter<TitleAdapter4.ViewHolder>{
    private ArrayList<Title> list = new ArrayList<>();
    public TitleAdapter4(ArrayList<Title> list){
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model1,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getTitel());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }

}

class OnTouchRey extends ItemTouchHelper.Callback{
    private TitleAdapter1 titleAdapter1;
    private ArrayList<Title> list;

    public OnTouchRey(TitleAdapter1 titleAdapter1, ArrayList<Title> list) {
        this.titleAdapter1 = titleAdapter1;
        this.list = list;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag;    //拖动标记
        int swipeFlags;    //滑动标记
        //如果是表格布局，则可以上下左右的拖动，但是不能滑动
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipeFlags = 0;
        }
        //如果是线性布局，那么只能上下拖动，只能左右滑动
        else {
            dragFlag = ItemTouchHelper.UP |
                    ItemTouchHelper.DOWN;
            swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }

        //通过makeMovementFlags生成最终结果
        return makeMovementFlags(dragFlag, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        System.out.println("拖动");
        //被拖动的item位置
        int fromPosition = viewHolder.getLayoutPosition();
        //他的目标位置
        int targetPosition = target.getLayoutPosition();
        //为了降低耦合，使用接口让Adapter去实现交换功能
        Collections.swap(list,fromPosition,targetPosition);
        titleAdapter1.notifyDataSetChanged();
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }
}