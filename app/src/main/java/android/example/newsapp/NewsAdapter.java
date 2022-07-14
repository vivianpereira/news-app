package android.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> arrayList) {
        super(context, R.layout.news_app_list, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listIem = convertView;
        if (listIem == null) {
            listIem = inflateItem(parent);
        }
        bindItem(listIem, getItem(position));
        return listIem;
    }

    private View inflateItem(@NonNull ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(
                R.layout.news_app_list, parent, false);
    }

    private void bindItem(View listIem, News news) {
        TextView titleView = listIem.findViewById(R.id.title);
        TextView dateView = listIem.findViewById(R.id.date);
        TextView authorView = listIem.findViewById(R.id.author);
        TextView sectionNameView = listIem.findViewById(R.id.sectionName);

        titleView.setText(news.getTitle());
        dateView.setText(String.format("%s %s", news.getDate(), news.getTime()));
        authorView.setText(news.getAuthor());
        sectionNameView.setText(news.getSectionName());
    }
}