package stefanserkhir.simplerssreading.ui.views.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stefanserkhir.simplerssreading.R;
import stefanserkhir.simplerssreading.ui.presenters.interfaces.NewsListPresenter;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
    private NewsListPresenter mNewsListPresenter;
    private final LayoutInflater mInflater;

    public NewsAdapter(NewsListPresenter newsListPresenter, LayoutInflater inflater) {
        mNewsListPresenter = newsListPresenter;
        mInflater = inflater;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsHolder(mInflater.inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, int position) {
        mNewsListPresenter.onBindRepositoryItemViewAtPosition(newsHolder, position);
    }

    @Override
    public int getItemCount() {
        Log.d("MyFilter", "getItemCount -> mNewsListPresenter == null ? " + (mNewsListPresenter == null));
        return mNewsListPresenter.getRepositoryItemsCount();
    }
}
