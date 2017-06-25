package baseview;

import java.util.List;

import Bean.News;

/**
 * 获取网络新闻界面接口
 * Created by Vincent on 2016/10/21.
 */
public interface NewsInterface {

    //获取网络数据成功
    public void succeedGetNews(List<News> list,int type);


    //获取网络数据失败
    public void errorGetNews();
}
