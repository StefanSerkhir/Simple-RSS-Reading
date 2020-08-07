package stefanserkhir.simplerssreading.ui.views.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.RealmResults;
import stefanserkhir.simplerssreading.data.db.model.NewsItem;
import stefanserkhir.simplerssreading.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
    private RealmResults<NewsItem> mNewsList;
    private final Activity mActivity;

    public NewsAdapter(RealmResults<NewsItem> newsList, Activity activity) {
        mNewsList = newsList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsHolder(mActivity.getLayoutInflater().inflate(R.layout.news_item, parent, false), mActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, int position) {
        newsHolder.bindNews(mNewsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }
}
