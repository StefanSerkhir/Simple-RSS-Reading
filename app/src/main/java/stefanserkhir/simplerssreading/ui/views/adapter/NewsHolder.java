package stefanserkhir.simplerssreading.ui.views.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stefanserkhir.simplerssreading.data.local.model.NewsItem;
import stefanserkhir.simplerssreading.R;
import stefanserkhir.simplerssreading.ui.views.SingleNewsActivity;

class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Activity mActivity;
    private TextView mNewsTitle;
    private TextView mNewsDate;
    private NewsItem mNewsItem;

    public NewsHolder(@NonNull View itemView, Activity activity) {
        super(itemView);

        itemView.setOnClickListener(this);

        mNewsTitle = itemView.findViewById(R.id.news_title);
        mNewsDate = itemView.findViewById(R.id.news_date);

        mActivity = activity;
    }

    public void bindNews(NewsItem newsItem) {
        mNewsTitle.setText(newsItem.getTitle());
        mNewsDate.setText(newsItem.getDate());
        mNewsItem = newsItem;
    }

    @Override
    public void onClick(View v) {
        mActivity.startActivity(SingleNewsActivity.newIntent(mActivity, mNewsItem));
    }
}
