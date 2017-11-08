package com.bwie.recyclerview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bwie.recyclerview.adapter.RecyclerViewAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
//使用RecyclerView需添加Android官方支持的依赖：compile 'com.android.support:recyclerview-v7:25.3.1'
public class MainActivity extends Activity {
    private RecyclerView recyclerView;
    private List<String> list = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        list.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3437523560,2570312276&fm=27&gp=0.jpg");
        list.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1048304009,1533562216&fm=27&gp=0.jpg");
        list.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3744146906,172628736&fm=27&gp=0.jpg");
        list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=900946121,4058384925&fm=27&gp=0.jpg");
        list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2746401840,2463337952&fm=27&gp=0.jpg");
        list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4009572336,3320118404&fm=27&gp=0.jpg");
        list.add("http://img1.imgtn.bdimg.com/it/u=2710025438,2030209695&fm=27&gp=0.jpg");
        list.add("http://img0.imgtn.bdimg.com/it/u=2749800578,1172713594&fm=27&gp=0.jpg");
        list.add("http://img1.imgtn.bdimg.com/it/u=1364748988,3595630243&fm=27&gp=0.jpg");
        list.add("http://img2.imgtn.bdimg.com/it/u=1313667106,1509125836&fm=27&gp=0.jpg");

        /**
         * 创建一个线性布局管理器
         * 想使用 瀑布流 布局 就把下面一行 设置布局管理器 代码注释，但是不能滑动显示页面
         * 想使用 滑动显示页面 就把下面一行 设置布局管理器 代码解释，但是不能 显示 瀑布流 布局
         */
        linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);             //设置布局管理器

        // 默认是Vertical，可以不写
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //1. 设置布局管理器：recyclerView.setLayoutManager(布局方式)
        // （1）设置类似于listview的布局管理器：new LinearLayoutManager()
        //recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        // （2）设置九宫格布局管理器:  new GridLayoutManager()
        //recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        //  （3）设置 瀑布流 布局管理器：new StaggeredGridLayoutManager()
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));

        //2. 设置适配器
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, list);
        recyclerView.setAdapter(adapter);

        //3. 为条目添加分割线,需在buil.gradle文件中添加依赖：compile 'com.yqritc:recyclerview-flexibledivider:1.4.0'
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.GREEN).build()
        );

        //4. 调用接口类，显示点击事件触发的数据
        adapter.setListener(new RecyclerViewAdapter.Listener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this, "当前条目为："+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "当前条目为："+position, Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 5. 滑动监听事件  setOnScrollListener
         * LinearLayoutManager提供了如下几个方法来帮助开发者获取屏幕上的顶部item和底部item：
             findFirstVisibleItemPosition()
             findFirstCompletelyVisibleItemPosition()
             findLastVisibleItemPosition()
             findLastCompletelyVisibleItemPosition()
         */
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isShowTop = false;
            boolean isShowBottom = false;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == list.size()-1) {
                    if (!isShowTop) {
                        Toast.makeText(MainActivity.this, "滑动到底部", Toast.LENGTH_SHORT).show();
                    }
                    isShowTop = true;
                }else {
                    isShowTop = false;
                }
                if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    if (!isShowBottom) {
                        Toast.makeText(MainActivity.this, "滑动到顶部", Toast.LENGTH_SHORT).show();
                    }
                    isShowBottom = true;
                } else {
                    isShowBottom = false;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }
}
