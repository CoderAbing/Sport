package fragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.viencent.sport.R;

import java.util.ArrayList;
import java.util.List;

import Bean.News;
import DIYAdapter.NewsAdapter;
import presenter.NewsPresenter;
import baseview.NewsInterface;

/**
 * 这是新闻界面
 * Created by Viencent on 2016/7/19.
 */
public class Fragment_two extends Fragment implements NewsInterface, SwipeRefreshLayout.OnRefreshListener {
    private NewsPresenter newsPresenter;
    private ArrayList<News> list;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsAdapter adapter;
    private LayoutInflater inflater;
    private LinearLayout progressBar;
    private ProgressBar loadMore;
    private ImageView error;
    private LinearLayoutManager linearLayoutManager;
    private static int page = 1;
    private static final int FIRST = 0;//初次加载
    private static final int UPDATE = 1;//更新
    private static final int MORE = 2;//加载更多


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_news);
        progressBar = (LinearLayout) view.findViewById(R.id.get_news_bar);
        progressBar.setVisibility(View.VISIBLE);
        error = (ImageView) view.findViewById(R.id.get_error);
        loadMore = (ProgressBar) view.findViewById(R.id.progressBar_more);
        linearLayoutManager = new LinearLayoutManager(getContext());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        this.inflater = inflater;
        newsPresenter = new NewsPresenter(this, getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setOnRefreshListener(this);//给SwipeRefreshLayout添加下拉事件
        newsPresenter.getNews(FIRST, 1);
        addListener();
        return view;
    }

    /**
     * 添加点击事件
     */
    private void addListener() {

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    loadMore.setVisibility(View.VISIBLE);
                    newsPresenter.getNews(MORE, page);
                }
            }
        });
    }

    /**
     * 接口回调方法获得数据
     *
     * @param list
     */

    @Override
    public void succeedGetNews(List<News> list, int type) {
        ArrayList<News> lists = new ArrayList<>();
        for (News news : list) {
            lists.add(news);
        }
        switch (type) {
            case FIRST:
                this.list = lists;
                progressBar.setVisibility(View.GONE);

                adapter = new NewsAdapter(list, inflater);
                recyclerView.setAdapter(adapter);
                break;
            case UPDATE:
                adapter.clearData();
                adapter.changData(list);
                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                break;
            case MORE:
                for (News news : list) {
                    this.list.add(news);
                }
                loadMore.setVisibility(View.GONE);
                adapter.addData(this.list);
                Toast.makeText(getContext(), "成功加载", Toast.LENGTH_SHORT).show();
                page++;
                return;


        }
    }

    @Override
    public void errorGetNews() {
        progressBar.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
    }

    /**
     * 实现下拉刷新接口 刷新数据
     */
    @Override
    public void onRefresh() {
        error.setVisibility(View.INVISIBLE);
        newsPresenter.getNews(UPDATE, 1);
        swipeRefreshLayout.setRefreshing(false);
    }
}
