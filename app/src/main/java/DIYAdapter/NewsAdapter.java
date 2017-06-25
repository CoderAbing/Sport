package DIYAdapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.viencent.sport.NewsActivity;
import com.example.viencent.sport.R;


import java.util.List;

import Bean.News;

/**
 * 新闻列表适配器
 * Created by Vincent on 2016/9/6.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private List<News> news;
    private LayoutInflater inflater;


    public NewsAdapter(List<News> news, LayoutInflater inflater) {
        this.news = news;
        this.inflater = inflater;
    }

    public void changData(List<News> news) {
        this.news = news;
        this.notifyDataSetChanged();

    }

    public void clearData() {
        this.news = null;
    }

    public void addData(List<News> newss) {
        this.news = newss;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.news_list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (holder.view != null) {
            holder.title.setText(news.get(position).getTitle());
            holder.summary.setText(news.get(position).getDescription());
            Glide.with(inflater.getContext())
                    .load(news.get(position).getPicUrl())
                    .into(holder.image);

            holder.view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(inflater.getContext(), NewsActivity.class);
                    intent.putExtra("newURL", news.get(position).getUrl());
                    inflater.getContext().startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return news == null ? 0 : news.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        private TextView summary;

        private ImageView image;

        private View view;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            title = (TextView) itemView.findViewById(R.id.news_item_title);
            summary = (TextView) itemView.findViewById(R.id.news_item_summary);
            image = (ImageView) itemView.findViewById(R.id.news_item_image);
        }
    }

}
