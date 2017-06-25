package basemodel;

import java.util.List;

import Bean.News;

/**
 * 获取网络新闻底层接口
 * Created by Vincent on 2016/10/21.
 */
public interface NewsModelInterface {
    //成功返回网络数据
    public void succeedGetNews(List<News> list, int type);

    //返回网络数据失败

    public void errorGetNews();
}
