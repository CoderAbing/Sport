package mode;


import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import Bean.News;
import basemodel.NewsModelInterface;

/**
 * 在此处抓取新闻
 * Created by Vincent on 2016/10/21.
 */
public class NewsModel {

    private final String APPKEY = "74b16c33fd164842b5a477fdac72b13f";//阿凡达数据APPKEY
    private int rows = 50;//一次返回新闻的条数
    private String URL;
    private java.net.URL url;
    private List<News> list;
    private News news;
    private static Context context;

    //获取网络新闻
    public void getNews(final Context context, final NewsModelInterface newsModel, final int type, int page) {
        URL = " http://api.avatardata.cn/GuoNeiNews/Query?key=" + APPKEY + "&page=" + page + "&rows=" + rows;
        RequestQueue mQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode rootNode = null;
                        try {
                            rootNode = mapper.readValue(s, JsonNode.class);
                            JsonNode json = rootNode.findPath("result");
                            News[] news = mapper.readValue(json + "", News[].class);
                            list = Arrays.asList(news);
                            newsModel.succeedGetNews(list, type);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        newsModel.errorGetNews();
                    }
                }
        );
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2 * 1000, 0, 1.0f));//设置网络超时和最多重复请求次数
        mQueue.add(stringRequest);
    }


}
