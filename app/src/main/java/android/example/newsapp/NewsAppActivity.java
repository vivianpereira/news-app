package android.example.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NewsAppActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private TextView messageTextView;
    private ProgressBar loadingBar;
    private NewsAdapter newsAdapter;
    private ListView newsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_app_activity);

        messageTextView = findViewById(R.id.message_text_view);
        loadingBar = findViewById(R.id.loading_bar);
        newsListView = findViewById(R.id.news_list_view);


        setUpList();
        checkConnection();
    }

    private void checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, this);
        } else {
            loadingBar.setVisibility(View.GONE);
            messageTextView.setText(R.string.no_internet_connection);
        }
    }

    private void setUpList() {
        newsListView.setEmptyView(messageTextView);
        newsAdapter = new NewsAdapter(this, new ArrayList<>());
        newsListView.setAdapter(newsAdapter);
        newsListView.setOnItemClickListener((adapterView, view, position, l) -> {
            News current = newsAdapter.getItem(position);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(current.getWebURL())));
        });
    }


    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsAppLoader(this, "COVID");
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        newsAdapter.clear();
        loadingBar.setVisibility(View.GONE);
        if (news != null && !news.isEmpty()) {
            newsAdapter.addAll(news);
        } else if (news == null) {
            messageTextView.setText(R.string.error_server);
        } else {
            messageTextView.setText(R.string.no_news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();
    }

}