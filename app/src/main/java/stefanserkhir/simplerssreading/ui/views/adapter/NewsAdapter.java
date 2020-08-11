package stefanserkhir.simplerssreading.ui.views.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stefanserkhir.simplerssreading.R;
import stefanserkhir.simplerssreading.ui.presenters.interfaces.NewsListPresenter;
import stefanserkhir.simplerssreading.ui.views.helpers.ClickListener;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
    private NewsListPresenter mPresenter;
    private final LayoutInflater mInflater;

    public NewsAdapter(NewsListPresenter presenter, LayoutInflater inflater) {
        mPresenter = presenter;
        mInflater = inflater;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsHolder(mInflater.inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, int position) {
        mPresenter.onBindRepositoryItemViewAtPosition(newsHolder, position);
        newsHolder.itemView.setOnClickListener(new ClickListener(mPresenter, position));
    }

    @Override
    public int getItemCount() {
        return mPresenter.getRepositoryItemsCount();
    }
}
