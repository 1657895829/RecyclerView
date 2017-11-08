package com.bwie.recyclerview.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bwie.recyclerview.R;
import java.util.List;
import java.util.Random;
import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * RecyclerView的适配器
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ImageViewHolder> {
    private int itemWidth;
    private Context context;
    private List<String> list;

    public RecyclerViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;

        //获取屏幕的宽
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        itemWidth = width / 3;       //设置条目的宽度
    }

    //创建ViewHolder
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建一个View视图
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, null);
        //实例化ImageViewHolder类
        ImageViewHolder holder = new ImageViewHolder(view);
        return holder;
    }

    // 绑定view 显示数据
    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        ViewGroup.LayoutParams params = holder.itemImageview.getLayoutParams();
        //设置条目的高度为随机数
        int itemHeight = 300;
        itemHeight = new Random().nextInt(500);
        if (itemHeight < 300) {
            itemHeight = 300;
        }

        params.width = itemWidth;
        params.height = itemHeight;

        holder.itemImageview.setLayoutParams(params);
        //使用Glide加载图片需导包
        Glide.with(context).load(list.get(position)).into(holder.itemImageview);
        //使用ImageLoader加载图片需导包
        //ImageLoader.getInstance().displayImage(list.get(position),holder.itemImageview, ImageLoaderUtil.getDefaultOption());

        holder.itemTextview.setText("条目：" + position);
        //条目单击事件
        holder.itemImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {  //回调接口，显示数据
                    listener.onClick(v, position);
                }
            }
        });
        //条目长按事件
        holder.itemImageview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {  //回调接口，显示数据
                    listener.onLongClick(v, position);
                }
                return false;
            }
        });
    }

    // 获取条目的个数
    @Override
    public int getItemCount() {
        return list.size();
    }

    //自定义一个ViewHolder容器静态类，声明其构造方法
    static class ImageViewHolder extends RecyclerView.ViewHolder {
        //使用butterKnife必须使用 版本7 第三方依赖   compile 'com.jakewharton:butterknife:7.0.0'
        @Bind(R.id.item_imageview)
        ImageView itemImageview;
        @Bind(R.id.item_textview)
        TextView itemTextview;
        public ImageViewHolder(View itemView) {
            super(itemView);
            //下面这行代码相当于查找控件id
            ButterKnife.bind(this, itemView);
            //itemImageview = (ImageView) itemView.findViewById(R.id.item_imageview);
            //itemTextview = (TextView) itemView.findViewById(R.id.item_textview);
        }
    }

    //再声明接口变量，设置接口的setter方法
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    //先设置条目点击接口
    public interface Listener {
        public void onClick(View view, int position);  //单击事件

        public void onLongClick(View view, int position);  //长按事件
    }
}
