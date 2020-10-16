package com.lenovo.smarttraffic.demo7.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.bean.Title;
import com.lenovo.smarttraffic.ui.activity.BaseActivity;
import com.lenovo.smarttraffic.ui.activity.Item1Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class SetTitleActivity extends AppCompatActivity {
    protected static TextView textView;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private ArrayList<Title> list1 = new ArrayList<>();
    private ArrayList<Title> list2 = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String sort = "";
    private String sort_list[] = null;
    private String name[] = new String[]{
      "推荐","热点","科技","汽车资讯","健康","财经","教育","旅游","军事","实时路况","文化","二手车","违章资讯","娱乐","体育","视频","游戏","电影"
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SetTitleActivity.this, Item1Activity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("title",MODE_PRIVATE);
        editor = getSharedPreferences("title",MODE_PRIVATE).edit();

        if(!sharedPreferences.getString("sort","").equals("")){
            sort = sharedPreferences.getString("sort","");
        }
        if(!sort.equals("")){
            sort_list = sort.split(",");
        }


        if(sort_list == null){
            for (int i = 0; i < name.length; i++) {
                if(sharedPreferences.getInt(name[i],-1) == 0){
                    list1.add(new Title(name[i]));
                }
                else if(sharedPreferences.getInt(name[i],-1) == 1){
                    list2.add(new Title(name[i]));
                }
            }
        }
        else {
            for (int i = 0; i < name.length; i++) {
                if(sharedPreferences.getInt(name[i],-1) == 0){
                    list1.add(new Title(name[i]));
                }
            }
            for (int i = 0; i < sort_list.length; i++) {
                int j = Integer.parseInt(sort_list[i]);
                list2.add(new Title(name[j]));
            }

        }

        setContentView(R.layout.settitle);
        textView = findViewById(R.id.over);
        recyclerView1 = findViewById(R.id.re1);
        recyclerView2 = findViewById(R.id.re2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView1.setAdapter(new TitleAdapter3(editor,list2,recyclerView1,recyclerView2,list2,list1));
        recyclerView2.setAdapter(new TitleAdapter4(list1));
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this,4);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this,4);
        recyclerView1.setLayoutManager(gridLayoutManager1);
        recyclerView2.setLayoutManager(gridLayoutManager2);
    }
}

//适配器1
class TitleAdapter1 extends RecyclerView.Adapter<TitleAdapter1.ViewHolder>{
    private SharedPreferences.Editor editor;
    private ArrayList<Title> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;
    private RecyclerView recyclerView2;
    private ArrayList<Title> list2;
    public TitleAdapter1(SharedPreferences.Editor editor,ArrayList<Title> list,RecyclerView recyclerView,RecyclerView recyclerView2,ArrayList<Title> list2){
        this.recyclerView = recyclerView;
        this.list = list;
        this.editor = editor;
        this.recyclerView2 = recyclerView2;
        this.list2 = list2;
        itemTouchHelper = new ItemTouchHelper(new OnTouchRey(editor,this,list));
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
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt(list.get(position).getTitel(),0);
                list2.add(list.get(position));
                recyclerView2.getAdapter().notifyDataSetChanged();
                //移除动画
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(0,list.size());
            }
        });
        itemTouchHelper.startDrag(holder);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            imageView = itemView.findViewById(R.id.image);
        }
    }

}

//适配器2
class TitleAdapter2 extends RecyclerView.Adapter<TitleAdapter2.ViewHolder>{
    private ArrayList<Title> list = new ArrayList<>();
    private SharedPreferences.Editor editor;
    private RecyclerView recyclerView1;
    private ArrayList<Title> list1;
    public TitleAdapter2(SharedPreferences.Editor editor,ArrayList<Title> list,RecyclerView recyclerView1,ArrayList<Title> list1){
        this.list = list;
        this.editor = editor;
        this.list1 = list1;
        this.recyclerView1 = recyclerView1;
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
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putInt(list.get(position).getTitel(),1);
                list1.add(list.get(position));
                recyclerView1.getAdapter().notifyDataSetChanged();
                //移除动画
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(0,list.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            imageView = itemView.findViewById(R.id.image);
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
    private SharedPreferences.Editor editor;
    private String name[] = new String[]{
            "推荐","热点","科技","汽车资讯","健康","财经","教育","旅游","军事","实时路况","文化","二手车","违章资讯","娱乐","体育","视频","游戏","电影"
    };

    public TitleAdapter3(SharedPreferences.Editor editor,ArrayList<Title> list, RecyclerView recyclerView1, RecyclerView recyclerView2, ArrayList<Title> list1, ArrayList<Title> list2) {
        this.list = list;
        this.recyclerView1 = recyclerView1;
        this.recyclerView2 = recyclerView2;
        this.list1 = list1;
        this.list2 = list2;
        this.editor = editor;

        SetTitleActivity.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTitleActivity.textView.setVisibility(View.INVISIBLE);
                String sort = "";
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < name.length; j++) {
                        if(list.get(i).getTitel().equals(name[j])){
                            sort += j+",";
                        }
                    }
                }
                editor.putString("sort",sort);
                editor.commit();
                recyclerView1.setAdapter(new TitleAdapter3(editor,list1,recyclerView1,recyclerView2,list1,list2));
                recyclerView2.setAdapter(new TitleAdapter4(list2));
            }
        });
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
                recyclerView1.setAdapter(new TitleAdapter1(editor,list1,recyclerView1,recyclerView2,list2));
                recyclerView2.setAdapter(new TitleAdapter2(editor,list2,recyclerView1,list1));
                SetTitleActivity.textView.setVisibility(View.VISIBLE);
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
    private SharedPreferences.Editor editor;
    private String name[] = new String[]{
            "推荐","热点","科技","汽车资讯","健康","财经","教育","旅游","军事","实时路况","文化","二手车","违章资讯","娱乐","体育","视频","游戏","电影"
    };

    public OnTouchRey(SharedPreferences.Editor editor,TitleAdapter1 titleAdapter1, ArrayList<Title> list) {
        this.titleAdapter1 = titleAdapter1;
        this.list = list;
        this.editor = editor;
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
        //被拖动的item位置
        int fromPosition = viewHolder.getLayoutPosition();
        //他的目标位置
        int targetPosition = target.getLayoutPosition();
        //为了降低耦合，使用接口让Adapter去实现交换功能
        Collections.swap(list,fromPosition,targetPosition);
        String sort = "";
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < name.length; j++) {
                if(list.get(i).getTitel().equals(name[j])){
                    sort += j+",";
                }
            }
        }
        editor.putString("sort",sort);
        editor.commit();
        titleAdapter1.notifyDataSetChanged();
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }
}