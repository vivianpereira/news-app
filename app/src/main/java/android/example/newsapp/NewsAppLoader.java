package android.example.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class NewsAppLoader extends AsyncTaskLoader<List<News>> {

    private String mTopic;

    public NewsAppLoader(Context context, String topic) {
        super(context);
        mTopic = topic;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        List<News> newsDataList = null;
        try {
            newsDataList = new NewsApi().fetchNewsApp(mTopic);
        } catch (Exception e) {
            Log.e("NewsAppLoader", "Some error happened loading the news,", e);
        }
        return newsDataList;
    }


}