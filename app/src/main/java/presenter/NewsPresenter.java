package presenter;

import android.content.Context;
import android.os.Handler;

import java.util.List;

import Bean.News;
import fragment.Fragment_two;
import mode.NewsModel;
import basemodel.NewsModelInterface;
import baseview.NewsInterface;

/**
 * 新闻中间类
 * Created by Vincent on 2016/10/21.
 */
public class NewsPresenter {

    private NewsInterface newsInterface;
    private Context context;
    private Handler handler;
    private NewsModel newsModel;

    public NewsPresenter(Fragment_two view, Context context) {
        this.newsInterface = view;
        this.context = context;
        this.newsModel = new NewsModel();
        this.handler = new Handler();
    }

    public void getNews(int type ,int page) {
        newsModel.getNews(context, new NewsModelInterface() {
            //获得数据
            @Override
            public void succeedGetNews(final List<News> list,final int type) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        newsInterface.succeedGetNews(list,type);
                    }
                });
            }
            //获得数据错误
            @Override
            public void errorGetNews() {
                newsInterface.errorGetNews();
            }
        },type,page);
    }

}
